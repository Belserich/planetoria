package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Entity;

public abstract class EntityActorSystem extends BaseEntitySystem implements EntityActor {
	
	public EntityActorSystem(int handleBits) {
		this(10, handleBits);
	}
	
	public EntityActorSystem(int priority, int handleBits) {
		super(priority, handleBits);
	}
	
	@Override
	public void handle(Entity actor) {
		act(actor);
	}
}
