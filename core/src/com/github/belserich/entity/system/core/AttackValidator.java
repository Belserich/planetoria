package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityInteractorSystem;

import java.util.Iterator;

public class AttackValidator extends EntityInteractorSystem {
	
	public AttackValidator(int handleBits) {
		super(handleBits);
	}
	
	@Override
	public Family actors() {
		return Family.all(
				Attackable.class,
				Touchable.Touched.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				Ap.class,
				com.github.belserich.entity.component.Attacker.class,
				Selectable.Selected.class
		).get();
	}
	
	@Override
	public void interact(Entity actor, Iterator<Entity> selection) {
		
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
		
		actor.remove(Touchable.Touched.class);
		actor.remove(Covered.class);
		actor.add(new Attackable.Attacked(attackPts));
	}
}
