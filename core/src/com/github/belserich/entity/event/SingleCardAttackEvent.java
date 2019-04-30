package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;

public class SingleCardAttackEvent extends SingleCardInteractEvent {
	
	private final float attackPts;
	
	public SingleCardAttackEvent(SingleCardInteractEvent ev, float attackPts) {
		this(ev.sourceCard(), ev.destCard(), attackPts);
	}
	
	public SingleCardAttackEvent(Entity sourceCard, Entity destCard, float attackPts) {
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
