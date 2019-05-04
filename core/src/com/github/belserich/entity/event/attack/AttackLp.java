package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.AttackBase;

public final class AttackLp extends AttackBase {
	
	private final float oldLp, newLp, lpShift;
	
	public AttackLp(AttackBase ev, float oldLp, float newLp) {
		this(ev.sourceCard(), ev.attacked(), ev.attackPts(), oldLp, newLp, newLp - oldLp, ev.attackers());
	}
	
	public AttackLp(Entity sourceCard, Entity destCard, float attackPts, float oldLp, float newLp, float lpShift, Entity... others) {
		super(sourceCard, destCard, attackPts, others);
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
