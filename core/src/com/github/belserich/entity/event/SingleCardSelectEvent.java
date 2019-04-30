package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.EntityEvent;

public class SingleCardSelectEvent extends EntityEvent {
	
	public SingleCardSelectEvent(Entity entity) {
		super(entity);
	}
}
