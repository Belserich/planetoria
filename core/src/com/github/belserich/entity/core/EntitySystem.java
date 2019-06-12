package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public abstract class EntitySystem extends com.badlogic.ashley.core.EntitySystem {
	
	protected Family fam;
	protected ImmutableArray<Entity> entities;
	
	private float delta;
	
	public EntitySystem(Family fam) {
		this(fam, 10);
	}
	
	public EntitySystem(Family fam, int priority) {
		super(priority);
		this.fam = fam;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		entities = engine.getEntitiesFor(fam);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.addedToEngine(engine);
		entities = null;
	}
	
	@Override
	public void update(float delta) {
		this.delta = delta;
		super.update(delta);
		if (entities != null) {
			for (Entity entity : entities) {
				update(entity);
			}
		}
	}
	
	public void update(Entity entity) {
	}
	
	public float delta() {
		return delta;
	}
}
