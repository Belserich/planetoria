package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityInteractorSystem;

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
				Attacker.class,
				Selectable.Selected.class
		).get();
	}
	
	@Override
	public void interact(Entity actor, ImmutableArray<Entity> selection) {
		
		float attackPts = 0f;
		
		for (int i = 0; i < selection.size(); i++) {
			
			Entity attacker = selection.get(i);
			Ap ac = attacker.getComponent(Ap.class);
			Attacker atc = attacker.getComponent(Attacker.class);
			
			atc.attCount--;
			attackPts += ac.pts;
			
			if (atc.attCount <= 0) {
				attacker.remove(Attacker.class);
			}
			attacker.remove(Selectable.Selected.class);
			attacker.remove(Covered.class);
		}
		
		GameClient.log(this, "! Attack. Attackers: " + selection.size() + "; Attack points: " + attackPts);
		
		actor.remove(Touchable.Touched.class);
		actor.remove(Covered.class);
		actor.add(new Attackable.Attacked(attackPts));
	}
}
