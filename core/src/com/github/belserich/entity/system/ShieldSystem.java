package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.AttackableComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.core.BaseEntitySystem;

public class ShieldSystem extends BaseEntitySystem {
	
	public ShieldSystem() {
		super(Family.all(
				ShieldComponent.class,
				AttackableComponent.Attacked.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		ShieldComponent sc = entity.getComponent(ShieldComponent.class);
		AttackableComponent.Attacked ac = entity.getComponent(AttackableComponent.Attacked.class);
		
		String specLog = "SP: " + sc.pts + "; Attack points: " + ac.pts;
		if (sc.pts <= ac.pts) {
			GameClient.log(this, "! Shield break. " + specLog);
			entity.remove(ShieldComponent.class);
		} else {
			GameClient.log(this, "! Useless Shield attack. " + specLog);
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
	
	}
}
