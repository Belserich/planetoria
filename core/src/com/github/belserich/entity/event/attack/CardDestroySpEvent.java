package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;

public final class CardDestroySpEvent extends CardAttackBaseEvent {
	
	private final float prevSp;
	
	public CardDestroySpEvent(Entity sourceCard, Entity destCard, float attackPts, float prevSp, Entity... others) {
		super(sourceCard, destCard, attackPts, others);
		this.prevSp = prevSp;
	}
	
	public float prevSp() {
		return prevSp;
	}
}
