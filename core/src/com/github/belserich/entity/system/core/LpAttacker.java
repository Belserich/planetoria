package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityActor;

public class LpAttacker extends EntityActor {
	
	@Override
	public Family actors() {
		return Family.all(
				Lp.class,
				Attackable.Attacked.class
		).exclude(
				Sp.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		Lp lc = actor.getComponent(Lp.class);
		Attackable.Attacked ac = actor.getComponent(Attackable.Attacked.class);
		
		float last = lc.pts;
		lc.pts -= ac.pts;
		
		GameClient.log(this, "! Life attack. Old LP: " + last + "; New LP: " + lc.pts);
		
		if (lc.pts <= 0) {
			GameClient.log(this, "! Card death.");
			actor.remove(Lp.class);
			actor.remove(Covered.class);
			actor.add(new Dead());
		}
		
		actor.remove(Attackable.Attacked.class);
	}
}
