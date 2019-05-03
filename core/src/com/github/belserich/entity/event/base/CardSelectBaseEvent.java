package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.MultiEntityEvent;

public abstract class CardSelectBaseEvent extends MultiEntityEvent {
	
	public CardSelectBaseEvent(Entity primary, Entity... others) {
		super(primary, others);
	}
}
