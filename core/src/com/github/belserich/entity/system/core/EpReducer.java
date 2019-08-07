package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.EpConsuming;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityInteractor;

public class EpReducer extends EntityInteractor {
	
	@Override
	public Family actors() {
		return Family.all(
				Turnable.On.class,
				Ep.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				EpConsuming.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		Ep epc = actor.getComponent(Ep.class);
		int epSum = 0;
		
		for (Entity sel : selection) {
			
			EpConsuming ec = sel.getComponent(EpConsuming.class);
			epSum += ec.val;
		}
		
		GameClient.log(this, "! Energy Loss. Reduce ep from %d (-%d).", epc.val, epSum);
		epc.val -= epSum;
		actor.add(new Ep.Update());
	}
}
