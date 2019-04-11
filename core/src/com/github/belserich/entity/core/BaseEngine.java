package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

public class BaseEngine extends Engine {
	
	public void dispose() {
		
		this.removeAllEntities();
		
		for (EntitySystem sys : this.getSystems()) {
			this.removeSystem(sys);
			if (sys instanceof EntityFlagSystem) {
				((EntityFlagSystem) sys).dispose();
			}
		}
	}
}
