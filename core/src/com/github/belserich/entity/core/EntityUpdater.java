package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Entity;

public interface EntityUpdater {
	
	void justAdded(Entity entity);
	
	void justRemoved(Entity entity);
	
	void update(Entity entity);
}
