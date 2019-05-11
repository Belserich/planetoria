package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.AttackBase;

public class DestroyLp extends AttackBase {
	
	private final float prevLp;
	
	public DestroyLp(Entity primary, Entity destCard, float attackPts, float prevLp, Entity... others) {
		super(primary, destCard, attackPts, others);
		this.prevLp = prevLp;
	}
	
	public float prevLp() {
		return prevLp;
	}
}