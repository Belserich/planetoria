package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.entity.component.OwnedByPlayer;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityInteractor;

public class TurnChanger extends EntityInteractor {
	
	@Override
	public Family actors() {
		return Family.all(
				PlayerId.class,
				Turnable.On.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				OwnedByPlayer.class,
				Turnable.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		PlayerId pc = actor.getComponent(PlayerId.class);
		
		for (Entity next : selection) {
			
			OwnedByPlayer opc = next.getComponent(OwnedByPlayer.class);
			
			if (opc.id == pc.val) {
				next.add(new Turnable.On());
			} else {
				next.remove(Turnable.On.class);
			}
		}
	}
}
