package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.EntityEvent;

public class CardInteractEvent extends EntityEvent {
	
	private final Entity destCard;
	
	public CardInteractEvent(Entity sourceCard, Entity destCard) {
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
