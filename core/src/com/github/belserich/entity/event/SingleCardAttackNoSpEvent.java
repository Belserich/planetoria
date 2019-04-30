package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;

public class SingleCardAttackNoSpEvent extends SingleCardInteractEvent {
	
	public SingleCardAttackNoSpEvent(SingleCardInteractEvent ev) {
		this(ev.sourceCard(), ev.destCard());
	}
	
	public SingleCardAttackNoSpEvent(Entity sourceCard, Entity destCard) {
		super(sourceCard, destCard);
	}
}
