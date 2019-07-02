package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.EpConsuming;
import com.github.belserich.entity.component.Turn;
import com.github.belserich.entity.core.EIS;

import java.util.Iterator;

public class EpSystem extends EIS {
	
	public EpSystem() {
		super(Family.all(
				Turn.class,
				Ep.class
		).get(), Family.all(
				EpConsuming.Is.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity, Iterator<Entity> selection) {
		
		Ep epc = entity.getComponent(Ep.class);
		int epSum = 0;
		
		while (selection.hasNext()) {
			
			EpConsuming ec = selection.next().getComponent(EpConsuming.class);
			epSum += ec.val;
		}
		
		GameClient.log(this, "! Energy Loss. Reduce ep from %d (-%d).", epc.val, epSum);
		epc.val -= epSum;
		entity.add(new Ep.Update());
	}
}
