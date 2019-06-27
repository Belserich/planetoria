package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.EpConsuming;
import com.github.belserich.entity.component.Turn;
import com.github.belserich.entity.core.EntitySystem;

public class EpSystem extends EntitySystem {
	
	private Family epConsumers;
	
	public EpSystem() {
		super(Family.all(
				Turn.class,
				Ep.class
		).get());
		
		epConsumers = Family.all(
				EpConsuming.Is.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		Ep epc = entity.getComponent(Ep.class);
		ImmutableArray<Entity> epConsumerList = super.getEngine().getEntitiesFor(epConsumers);
		int epSum = 0;
		
		for (Entity consumer : epConsumerList) {
			
			EpConsuming ec = consumer.getComponent(EpConsuming.class);
			epSum += ec.val;
		}
		
		GameClient.log(this, "! Energy Loss. Reduce ep from %d (-%d).", epc.val, epSum);
		epc.val -= epSum;
	}
}
