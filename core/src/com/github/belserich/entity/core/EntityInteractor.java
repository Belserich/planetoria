package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public interface EntityInteractor {
	
	Family actors();
	
	Family iactors();
	
	void interact(Entity actor, ImmutableArray<Entity> selection);
}
