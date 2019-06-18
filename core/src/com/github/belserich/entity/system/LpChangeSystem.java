package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardHandle;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.core.EntitySystem;

public class LpChangeSystem extends EntitySystem {
	
	public LpChangeSystem() {
		super(Family.all(
				CardHandle.class,
				Lp.Changed.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
		
		CardHandle cc = entity.getComponent(CardHandle.class);
		Lp.Changed lc = entity.getComponent(Lp.Changed.class);
		
		Services.getUiService().updateCardLp(cc.handle, lc.now);
	}
}
