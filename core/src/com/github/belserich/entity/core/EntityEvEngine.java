package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

public class EntityEvEngine extends Engine {
	
	@Override
	public void addSystem(EntitySystem system) {
		if (system instanceof EntityEvSystem)
			super.addSystem(system);
		else invalidSystem();
	}
	
	private void invalidSystem() {
		throw new IllegalArgumentException("Specified system is not an entity event system!");
	}
	
	public void dispose() {
		
		this.removeAllEntities();
		
		for (EntitySystem sys : this.getSystems()) {
			this.removeSystem(sys);
			if (sys instanceof EntityEvSystem) {
				((EntityEvSystem) sys).dispose();
			}
		}
	}
}
