package com.github.belserich.entity.event.interact;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.InteractBase;

public final class Interact extends InteractBase {
	
	public Interact(InteractBase ev) {
		super(ev.sourceCard(), ev.destCard(), ev.all());
	}
	
	public Interact(Entity primary, Entity destCard, Entity... others) {
		super(primary, destCard, others);
	}
}
