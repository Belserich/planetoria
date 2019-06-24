package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Attackable;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.core.EntitySystem;

public class SpAttackSystem extends EntitySystem {
	
	public SpAttackSystem() {
		super(Family.all(
				Sp.class,
				Attackable.Attacked.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
		
		Sp sc = entity.getComponent(Sp.class);
		Attackable.Attacked ac = entity.getComponent(Attackable.Attacked.class);
		
		String specLog = "SP: " + sc.pts + "; Attack points: " + ac.pts;
		if (sc.pts <= ac.pts) {
			GameClient.log(this, "! Shield break. " + specLog);
			entity.remove(Sp.class);
		} else {
			GameClient.log(this, "! Useless Shield attack. " + specLog);
		}
		
		entity.remove(Attackable.Attacked.class);
	}
}
