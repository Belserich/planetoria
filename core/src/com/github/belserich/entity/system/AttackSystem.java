package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EIS;

import java.util.Iterator;

public class AttackSystem extends EIS {
	
	public AttackSystem() {
		
		super(Family.all(
				Attackable.class,
				Touchable.Touched.class
		).get(), Family.all(
				Ap.class,
				Attacker.class,
				Selectable.Selected.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity, Iterator<Entity> selection) {
		
		float attackPts = 0f;
		int count = 0;
		
		for (; selection.hasNext(); count++) {
			
			Entity attacker = selection.next();
			Ap ac = attacker.getComponent(Ap.class);
			
			ac.attCount--;
			attackPts += ac.pts;
			
			attacker.remove(Selectable.Selected.class);
			attacker.remove(Covered.class);
		}
		
		GameClient.log(this, "! Attack. Attackers: " + count + "; Attack points: " + attackPts);
		
		entity.remove(Touchable.Touched.class);
		entity.remove(Covered.class);
		entity.add(new Attackable.Attacked(attackPts));
	}
}
