package com.github.belserich.entity.event.interact;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardInteractBaseEvent;

public class CardInteractEvent extends CardInteractBaseEvent {
	
	public CardInteractEvent(CardInteractBaseEvent ev) {
		super(ev.sourceCard(), ev.destCard());
	}
	
	public CardInteractEvent(Entity sourceCard, Entity destCard) {
		super(sourceCard, destCard);
	}
}
