package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

/**
 * An entity event engine is a standard engine with an additional dispose method.
 */
public class EntityEvEngine extends Engine {
	
	/**
	 * Removes all systems and disposes them.
	 */
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
