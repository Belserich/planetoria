package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.Zone;
import com.github.belserich.entity.core.EntitySystem;

public class ZoneChangeSystem extends EntitySystem {
	
	public ZoneChangeSystem() {
		super(Family.all(
				Zone.Changed.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
//		Services.getUiService().changeCardZone(entity); TODO
	}
}
