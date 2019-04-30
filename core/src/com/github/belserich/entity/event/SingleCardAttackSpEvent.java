package com.github.belserich.entity.event;

import com.badlogic.ashley.core.Entity;

public class SingleCardAttackSpEvent extends SingleCardInteractEvent {
	
	private final float oldSp, newSp, spShift;
	
	public SingleCardAttackSpEvent(SingleCardInteractEvent ev, float oldSp, float newSp) {
		this(ev.sourceCard(), ev.destCard(), oldSp, newSp, newSp - oldSp);
	}
	
	public SingleCardAttackSpEvent(Entity sourceCard, Entity destCard, float oldSp, float newSp, float spShift) {
		super(sourceCard, destCard);
		this.oldSp = oldSp;
		this.newSp = newSp;
		this.spShift = spShift;
	}
	
	public float oldSp() {
		return oldSp;
	}
	
	public float newSp() {
		return newSp;
	}
	
	public float spShift() {
		return spShift;
	}
}
