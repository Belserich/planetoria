package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.AttackBase;

public final class AttackNoSp extends AttackBase {
	
	public AttackNoSp(Entity primary, Entity destCard, float attackPts, Entity... others) {
		super(primary, destCard, attackPts, others);
	}
}
