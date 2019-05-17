package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

public abstract class BaseEntitySystem extends EntitySystem {
	
	protected Family fam;
	protected ImmutableArray<Entity> entities;
	
	private float delta;
	
	public BaseEntitySystem(Family fam) {
		this(fam, 10);
	}
	
	public BaseEntitySystem(Family fam, int priority) {
		super(priority);
		this.fam = fam;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		entities = engine.getEntitiesFor(fam);
	}
	
	@Override
	public void update(float delta) {
		this.delta = delta;
		super.update(delta);
		for (Entity entity : entities) {
			update(entity);
		}
	}
	
	public void update(Entity entity) {
	}
	
	public float delta() {
		return delta;
	}
}
