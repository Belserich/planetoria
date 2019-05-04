package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.AttackBase;
import com.github.belserich.entity.event.base.InteractBase;

public final class Attack extends AttackBase {
	
	public Attack(InteractBase ev, float attackPts) {
		super(ev, attackPts, ev.all());
	}
	
	public Attack(Entity sourceCard, Entity destCard, float attackPts, Entity... others) {
		super(sourceCard, destCard, attackPts, others);
	}
}
