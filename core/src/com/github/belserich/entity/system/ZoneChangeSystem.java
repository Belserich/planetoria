package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.OwnedByZone;
import com.github.belserich.entity.core.EntitySystem;

public class ZoneChangeSystem extends EntitySystem {
	
	public ZoneChangeSystem() {
		super(Family.all(
				CardId.class,
				OwnedByZone.Changed.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {

//		CardId hc = entity.getComponent(CardId.class);
//		OwnedByZone.Changed zc = entity.getComponent(OwnedByZone.Changed.class);
//
//		Services.getUiService().setCardOnField(hc.id, zc.now.ordinal(), zc.nowField);
	}
}
