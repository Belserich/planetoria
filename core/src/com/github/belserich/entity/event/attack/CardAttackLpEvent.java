package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;

public class CardAttackLpEvent extends CardAttackBaseEvent {
	
	private final float oldLp, newLp, lpShift;
	
	public CardAttackLpEvent(CardAttackBaseEvent ev, float oldLp, float newLp) {
		this(ev.attacker(), ev.attacked(), ev.attackPts(), oldLp, newLp, newLp - oldLp);
	}
	
	public CardAttackLpEvent(Entity sourceCard, Entity destCard, float attackPts, float oldLp, float newLp, float lpShift) {
		super(sourceCard, destCard, attackPts);
		this.oldLp = oldLp;
		this.newLp = newLp;
		this.lpShift = lpShift;
	}
	
	public float oldLp() {
		return oldLp;
	}
	
	public float newLp() {
		return newLp;
	}
	
	public float lpShift() {
		return lpShift;
	}
}
