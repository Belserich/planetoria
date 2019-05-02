package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;

public class SingleCardUiInteractEvent extends SingleCardInteractEvent {
	
	public SingleCardUiInteractEvent(SingleCardInteractEvent ev) {
		super(ev.sourceCard(), ev.destCard());
	}
	
	public SingleCardUiInteractEvent(Entity sourceCard, Entity destCard) {
		super(sourceCard, destCard);
	}
}
