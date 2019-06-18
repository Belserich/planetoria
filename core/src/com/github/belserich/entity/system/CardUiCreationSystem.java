package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityMaintainer;

public class CardUiCreationSystem extends EntityMaintainer {
	
	public CardUiCreationSystem() {
		
		super(Family.all(
				ZoneId.class,
				CardHandle.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		CardHandle hc = entity.getComponent(CardHandle.class);
		ZoneId zc = entity.getComponent(ZoneId.class);
		
		Name nc = entity.getComponent(Name.class);
		Effect ec = entity.getComponent(Effect.class);
		Lp lc = entity.getComponent(Lp.class);
		Ap ac = entity.getComponent(Ap.class);
		Sp sc = entity.getComponent(Sp.class);
		Covered cc = entity.getComponent(Covered.class);
		
		Modification mc = entity.getComponent(Modification.class);
		
		String name = nc != null ? nc.name : "???";
		String effect = ec != null ? ec.text : "";
		float lp = lc != null ? lc.pts : -1;
		float ap = ac != null ? ac.pts : -1;
		float sp = sc != null ? sc.pts : -1;
		boolean isCovered = cc != null;
		
		if (mc == null) {
			hc.handle = Services.getUiService().addCard(zc.id, name, lp, ap, sp, isCovered);
		} else {
			hc.handle = Services.getUiService().addCard(zc.id, name, effect, isCovered);
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		CardHandle hc = entity.getComponent(CardHandle.class);
		
		Services.getUiService().removeCard(hc.handle);
	}
}
