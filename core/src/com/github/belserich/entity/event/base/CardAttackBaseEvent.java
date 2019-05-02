package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;

public abstract class CardAttackBaseEvent extends CardInteractBaseEvent {
	
	private final float attackPts;
	
	public CardAttackBaseEvent(CardInteractBaseEvent ev, float attackPts) {
		this(ev.sourceCard(), ev.destCard(), attackPts);
	}
	
	public CardAttackBaseEvent(Entity sourceCard, Entity destCard, float attackPts) {
		super(sourceCard, destCard);
		this.attackPts = attackPts;
	}
	
	public Entity attacker() {
		return sourceCard();
	}
	
	public Entity attacked() {
		return destCard();
	}
	
	public float attackPts() {
		return attackPts;
	}
}
