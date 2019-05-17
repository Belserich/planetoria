package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.AttackableComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.core.EventSystem;

public class ShieldSystem extends EventSystem {
	
	public ShieldSystem() {
		super(Family.all(
				ShieldComponent.class,
				AttackableComponent.Attacked.class
				).get(), 1,
				ShieldComponent.Broke.class);
	}
	
	@Override
	public void update(Entity entity) {
		
		ShieldComponent sc = entity.getComponent(ShieldComponent.class);
		AttackableComponent.Attacked ac = entity.getComponent(AttackableComponent.Attacked.class);
		
		String specLog = "SP: " + sc.pts + "; Attack points: " + ac.pts;
		if (sc.pts <= ac.pts) {
			GameClient.log(this, "! Shield break. " + specLog);
			entity.remove(ShieldComponent.class);
			entity.add(new ShieldComponent.Broke());
		} else {
			GameClient.log(this, "! Useless Shield attack. " + specLog);
		}
	}
}
