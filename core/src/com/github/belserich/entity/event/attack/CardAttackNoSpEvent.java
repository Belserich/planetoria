package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;

public final class CardAttackNoSpEvent extends CardAttackBaseEvent {
	
	public CardAttackNoSpEvent(Entity primary, Entity destCard, float attackPts, Entity... others) {
		super(primary, destCard, attackPts, others);
	}
}
