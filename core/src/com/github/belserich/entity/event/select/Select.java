package com.github.belserich.entity.event.select;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.SelectBase;

public final class Select extends SelectBase {
	
	public Select(Entity primary, Entity... others) {
		super(primary, others);
	}
}
