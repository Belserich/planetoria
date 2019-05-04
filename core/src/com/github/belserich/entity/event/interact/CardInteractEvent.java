package com.github.belserich.entity.event.interact;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardInteractBaseEvent;

public final class CardInteractEvent extends CardInteractBaseEvent {
	
	public CardInteractEvent(CardInteractBaseEvent ev) {
		super(ev.sourceCard(), ev.destCard(), ev.all());
	}
	
	public CardInteractEvent(Entity primary, Entity destCard, Entity... others) {
		super(primary, destCard, others);
	}
}
