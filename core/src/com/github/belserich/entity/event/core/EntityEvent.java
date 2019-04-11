package com.github.belserich.entity.event.core;

import com.badlogic.ashley.core.Entity;

public class EntityEvent {
	
	private final Entity entity;
	
	public EntityEvent(Entity entity) {
		this.entity = entity;
	}
	
	public Entity entity() {
		return entity;
	}
}
