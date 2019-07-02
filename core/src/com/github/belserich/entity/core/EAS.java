package com.github.belserich.entity.core;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import java8.util.stream.StreamSupport;

import java.util.Arrays;

/**
 * An entity action system.
 */
public abstract class EAS extends EntitySystem implements EntityListener {
	
	private final EntitySystem[] subSystems;
	
	private final Family family;
	protected ImmutableArray<Entity> entities;
	private float delta;
	
	public EAS(Family family, EntitySystem... subSystems) {
		this(10, family, subSystems);
	}
	
	public EAS(int priority, Family family, EntitySystem... subSystems) {
		super(priority);
		this.family = family;
		this.subSystems = subSystems;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		
		super.addedToEngine(engine);
		
		engine.addEntityListener(family, this);
		
		entities = engine.getEntitiesFor(family);
		StreamSupport.stream(Arrays.asList(subSystems)).forEach(engine::addSystem);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		
		super.addedToEngine(engine);
		
		engine.removeEntityListener(this);
		
		entities = new ImmutableArray<>(new Array<>());
		StreamSupport.stream(Arrays.asList(subSystems)).forEach(engine::removeSystem);
	}
	
	@Override
	public void update(float delta) {
		
		super.update(delta);
		this.delta = delta;
		updateEntities();
	}
	
	public void updateEntities() {
		
		for (Entity entity : entities) {
			update(entity);
		}
	}
	
	@Override
	public void entityAdded(Entity entity) {
	}
	
	@Override
	public void entityRemoved(Entity entity) {
	}
	
	public void update(Entity entity) {
	}
	
	public float delta() {
		return delta;
	}
}
