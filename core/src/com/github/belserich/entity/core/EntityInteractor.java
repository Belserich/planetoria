package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;

import java.util.Iterator;

public interface EntityInteractor {
	
	Family actors();
	
	Family iactors();
	
	void interact(Entity actor, Iterator<Entity> selection);
}
