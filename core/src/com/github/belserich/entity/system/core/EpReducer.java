package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.EpConsuming;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityInteractorSystem;

import java.util.Iterator;

public class EpReducer extends EntityInteractorSystem {
	
	public EpReducer(int handleBits) {
		super(handleBits);
	}
	
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
	public void interact(Entity actor, Iterator<Entity> selection) {
		
		Ep epc = actor.getComponent(Ep.class);
		int epSum = 0;
		
		while (selection.hasNext()) {
			
			EpConsuming ec = selection.next().getComponent(EpConsuming.class);
			epSum += ec.val;
		}
		
		GameClient.log(this, "! Energy Loss. Reduce ep from %d (-%d).", epc.val, epSum);
		epc.val -= epSum;
		actor.add(new Ep.Update());
	}
}
