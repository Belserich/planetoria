package com.github.belserich.entity.system;

import com.github.belserich.entity.component.SelectionComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.core.EventQueue;

public class SelectionSystem extends EntityEvSystem<SelectionComponent> {
	
	public SelectionSystem(EventQueue queue) {
		super(queue, true, SelectionComponent.class);
	}
	
	
}
