package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;

public class LpAttackSystem extends EntitySystem {
	
	public LpAttackSystem() {
		super(Family.all(
				Lp.class,
				Attackable.Attacked.class
		).exclude(
				Sp.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
		
		Lp lc = entity.getComponent(Lp.class);
		Attackable.Attacked ac = entity.getComponent(Attackable.Attacked.class);
		
		float last = lc.pts;
		lc.pts -= ac.pts;
		
		GameClient.log(this, "! Life attack. Old LP: " + last + "; New LP: " + lc.pts);
		
		if (lc.pts <= 0) {
			GameClient.log(this, "! Card death.");
			entity.remove(Lp.class);
			entity.remove(Covered.class);
			entity.add(new Dead());
		}
		
		entity.remove(Attackable.Attacked.class);
	}
}
