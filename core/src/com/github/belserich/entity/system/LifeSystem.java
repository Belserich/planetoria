package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Attackable;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.core.EventSystem;

public class LifeSystem extends EventSystem {
	
	public LifeSystem() {
		super(Family.all(
				Lp.class,
				Attackable.Attacked.class
		).exclude(
				Sp.class,
				Sp.Broke.class
		).get(), Lp.Changed.class);
	}
	
	@Override
	public void update(Entity entity) {
		
		Lp lc = entity.getComponent(Lp.class);
		Attackable.Attacked ac = entity.getComponent(Attackable.Attacked.class);
		
		float last = lc.pts;
		lc.pts -= ac.pts;
		
		GameClient.log(this, "! Life attack. Old LP: " + last + "; New LP: " + lc.pts);
		entity.add(new Lp.Changed(last, lc.pts));
		
		if (lc.pts <= 0) {
			GameClient.log(this, "! Card death.");
			entity.remove(Lp.class);
		}
	}
}
