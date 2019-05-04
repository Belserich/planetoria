package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.MultiEntityEvent;

public abstract class InteractBase extends MultiEntityEvent {
	
	private final Entity destCard;
	
	public InteractBase(Entity primary, Entity destCard, Entity... others) {
		super(primary, others);
		this.destCard = destCard;
	}
	
	public Entity sourceCard() {
		return entity();
	}
	
	public Entity destCard() {
		return destCard;
	}
}
