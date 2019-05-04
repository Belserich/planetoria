package com.github.belserich.entity.event.core;

import com.badlogic.ashley.core.Entity;

public class MultiEntityEvent extends EntityEvent {
	
	private final Entity[] secondary;
	private final Entity[] all;
	
	public MultiEntityEvent(Entity primary, Entity... secondary) {
		super(primary);
		this.secondary = secondary;
		
		all = new Entity[secondary.length + 1];
		all[0] = primary;
		for (int i = 0; i < secondary.length; i++) {
			all[i + 1] = secondary[i];
		}
	}
	
	public Entity primary() {
		return entity();
	}
	
	public Entity[] secondary() {
		return secondary;
	}
	
	public int secondaryCount() {
		return secondary.length;
	}
	
	public Entity[] all() {
		return all;
	}
	
	public int allCount() {
		return all.length;
	}
}
