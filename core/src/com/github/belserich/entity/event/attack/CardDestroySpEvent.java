package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;

public final class CardDestroySpEvent extends CardAttackBaseEvent {
	
	private final float shieldPoints;
	
	public CardDestroySpEvent(Entity sourceCard, Entity destCard, float attackPts, float shieldPoints, Entity... others) {
		super(sourceCard, destCard, attackPts, others);
		this.shieldPoints = shieldPoints;
	}
	
	public float shieldPoints() {
		return shieldPoints;
	}
}
