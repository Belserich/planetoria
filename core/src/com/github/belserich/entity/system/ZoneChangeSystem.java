package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardHandle;
import com.github.belserich.entity.component.Zone;
import com.github.belserich.entity.core.EntitySystem;

public class ZoneChangeSystem extends EntitySystem {
	
	public ZoneChangeSystem() {
		super(Family.all(
				CardHandle.class,
				Zone.Changed.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
		
		CardHandle hc = entity.getComponent(CardHandle.class);
		Zone.Changed zc = entity.getComponent(Zone.Changed.class);
		
		Services.getUiService().changeCardZone(hc.handle, zc.now.ordinal());
	}
}
