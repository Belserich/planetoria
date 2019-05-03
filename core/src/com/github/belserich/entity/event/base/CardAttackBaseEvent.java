package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;

public abstract class CardAttackBaseEvent extends CardInteractBaseEvent {
	
	private final float attackPts;
	
	public CardAttackBaseEvent(CardInteractBaseEvent ev, float attackPts, Entity... others) {
		this(ev.sourceCard(), ev.destCard(), attackPts, others);
	}
	
	public CardAttackBaseEvent(Entity primary, Entity destCard, float attackPts, Entity... others) {
		super(primary, destCard, others);
		this.attackPts = attackPts;
	}
	
	public Entity[] attackers() {
		return all();
	}
	
	public Entity attacked() {
		return destCard();
	}
	
	public float attackPts() {
		return attackPts;
	}
}
