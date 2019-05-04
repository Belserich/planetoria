package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;

public class CardDestroyLpEvent extends CardAttackBaseEvent {
	
	private final float prevLp;
	
	public CardDestroyLpEvent(Entity primary, Entity destCard, float attackPts, float prevLp, Entity... others) {
		super(primary, destCard, attackPts, others);
		this.prevLp = prevLp;
	}
	
	public float prevLp() {
		return prevLp;
	}
}
