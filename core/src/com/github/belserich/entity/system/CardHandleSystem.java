package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityMaintainer;

public class CardHandleSystem extends EntityMaintainer {
	
	public CardHandleSystem() {
		
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
		Lp lc = entity.getComponent(Lp.class);
		Ap ac = entity.getComponent(Ap.class);
		Sp sc = entity.getComponent(Sp.class);
		
		String name = nc != null ? nc.name : "???";
		float lp = lc != null ? lc.pts : -1;
		float ap = ac != null ? ac.pts : -1;
		float sp = sc != null ? sc.pts : -1;
		
		hc.handle = Services.getUiService().addCard(zc.id, name, lp, ap, sp, true);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		CardHandle hc = entity.getComponent(CardHandle.class);
		
		Services.getUiService().removeCard(hc.handle);
	}
}
