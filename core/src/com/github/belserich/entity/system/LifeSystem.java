package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.AttackableComponent;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.core.EventSystem;

public class LifeSystem extends EventSystem {
	
	public LifeSystem() {
		super(Family.all(
				LifeComponent.class,
				AttackableComponent.Attacked.class
				).exclude(
				ShieldComponent.class,
				ShieldComponent.Broke.class
				).get(), 0,
				LifeComponent.Changed.class);
	}
	
	@Override
	public void update(Entity entity) {
		
		LifeComponent lc = entity.getComponent(LifeComponent.class);
		AttackableComponent.Attacked ac = entity.getComponent(AttackableComponent.Attacked.class);
		
		float oldPts = lc.pts;
		lc.pts -= ac.pts;
		
		GameClient.log(this, "! Life attack. Old LP: " + oldPts + "; New LP: " + lc.pts);
		entity.add(new LifeComponent.Changed());
		
		if (lc.pts <= 0) {
			GameClient.log(this, "! Card death.");
			entity.remove(LifeComponent.class);
		}
	}
}
