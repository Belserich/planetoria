package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.Attacker;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityActor;

public class AttackerTurnHandler extends EntityActor {
	
	@Override
	protected Family actors() {
		return Family.all(
				Attacker.class,
				Turnable.HasTurn.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		Attacker ac = actor.getComponent(Attacker.class);
		ac.curr = ac.max;
	}
}
