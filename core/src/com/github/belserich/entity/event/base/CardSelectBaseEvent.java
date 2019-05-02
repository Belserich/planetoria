package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.EntityEvent;

public abstract class CardSelectBaseEvent extends EntityEvent {
	
	public CardSelectBaseEvent(Entity entity) {
		super(entity);
	}
}
