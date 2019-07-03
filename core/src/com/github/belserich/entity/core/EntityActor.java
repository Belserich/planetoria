package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

public interface EntityActor {
	
	Family actors();
	
	void act(Entity actor);
}
