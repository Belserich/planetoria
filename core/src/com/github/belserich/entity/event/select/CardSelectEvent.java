package com.github.belserich.entity.event.select;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardSelectBaseEvent;

public final class CardSelectEvent extends CardSelectBaseEvent {
	
	public CardSelectEvent(Entity primary, Entity... others) {
		super(primary, others);
	}
}
