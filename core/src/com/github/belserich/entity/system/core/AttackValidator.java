package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityInteractor;

public class AttackValidator extends EntityInteractor {
	
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
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		float attackPts = 0f;
		int attCount = selection.size();
		
		for (Entity attacker : (Entity[]) selection.toArray(Entity.class)) {
			
			Ap ac = attacker.getComponent(Ap.class);
			Attacker atc = attacker.getComponent(Attacker.class);
			
			atc.curr--;
			attackPts += ac.pts;
			
			if (atc.curr <= 0) {
				attacker.remove(Selectable.class);
			}
			
			attacker.remove(Selectable.Selected.class);
			attacker.remove(Covered.class);
		}
		
		GameClient.log(this, "! Attack. Attackers: " + attCount + "; Attack points: " + attackPts);
		
		actor.remove(Touchable.Touched.class);
		
		if (attackPts > 0) {
			actor.remove(Covered.class);
			actor.add(new Attackable.Attacked(attackPts));
		}
	}
}
