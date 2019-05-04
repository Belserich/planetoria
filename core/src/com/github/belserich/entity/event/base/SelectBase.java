package com.github.belserich.entity.event.base;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.core.MultiEntityEvent;

public abstract class SelectBase extends MultiEntityEvent {
	
	public SelectBase(Entity primary, Entity... others) {
		super(primary, others);
	}
}
