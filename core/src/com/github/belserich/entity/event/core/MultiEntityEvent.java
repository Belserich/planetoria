package com.github.belserich.entity.event.core;

import com.badlogic.ashley.core.Entity;

public class MultiEntityEvent extends EntityEvent {
	
	private final Entity[] all;
	
	public MultiEntityEvent(Entity primary, Entity... others) {
		super(primary);
		
		all = new Entity[others.length + 1];
		all[0] = primary;
		for (int i = 0; i < others.length; i++) {
			all[i + 1] = others[i];
		}
	}
	
	public Entity primary() {
		return entity();
	}
	
	public Entity[] all() {
		return all;
	}
	
	public int entityCount() {
		return all.length;
	}
}
