package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.EntityEvent;

public abstract class CardInteractBaseEvent extends EntityEvent {
	
	private final Entity destCard;
	
	public CardInteractBaseEvent(Entity sourceCard, Entity destCard) {
		super(sourceCard);
		this.destCard = destCard;
	}
	
	public Entity sourceCard() {
		return entity();
	}
	
	public Entity destCard() {
		return destCard;
	}
}
