package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;

public class SingleCardAttackLpEvent extends SingleCardInteractEvent {
	
	private final float oldLp, newLp, lpShift;
	
	public SingleCardAttackLpEvent(SingleCardInteractEvent ev, float oldLp, float newLp) {
		this(ev.sourceCard(), ev.destCard(), oldLp, newLp, newLp - oldLp);
	}
	
	public SingleCardAttackLpEvent(Entity sourceCard, Entity destCard, float oldLp, float newLp, float lpShift) {
		super(sourceCard, destCard);
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
