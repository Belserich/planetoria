package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Attackable;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.core.EntityActorSystem;

public class SpAttacker extends EntityActorSystem {
	
	public SpAttacker(int handleBits) {
		super(handleBits);
	}
	
	@Override
	public Family actors() {
		return Family.all(
				Sp.class,
				Attackable.Attacked.class
		).get();
	}
	
	@Override
	public void act(Entity actor) {
		
		Sp sc = actor.getComponent(Sp.class);
		Attackable.Attacked ac = actor.getComponent(Attackable.Attacked.class);
		actor.remove(Attackable.Attacked.class);
		
		String specLog = "SP: " + sc.pts + "; Attack points: " + ac.pts;
		if (sc.pts <= ac.pts) {
			GameClient.log(this, "! Shield break. " + specLog);
			actor.remove(Sp.class);
		} else {
			GameClient.log(this, "! Useless Shield attack. " + specLog);
		}
	}
}
