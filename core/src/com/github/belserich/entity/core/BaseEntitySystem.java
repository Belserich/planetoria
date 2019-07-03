package com.github.belserich.entity.core;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import static com.github.belserich.entity.core.EntityHandler.*;

public abstract class BaseEntitySystem extends EntitySystem implements EntityListener {
	
	private ImmutableArray<Entity> actors;
	
	private int handleBits;
	private float delta;
	
	public BaseEntitySystem(int priority, int handleBits) {
		super(priority);
		this.handleBits = handleBits;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		
		engine.addEntityListener(actors(), this);
		actors = engine.getEntitiesFor(actors());
	}
	
	public Family actors() {
		return null;
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		
		actors = new ImmutableArray<>(new Array<>());
		engine.removeEntityListener(this);
		
		setProcessing(false);
	}
	
	@Override
	public void update(float delta) {
		
		this.delta = delta;
		if ((handleBits & ENTITY_UPDATE) != 0) {
			for (Entity actor : actors) {
				handle(actor);
			}
		}
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		if ((handleBits & ENTITY_ADDED) != 0) {
			handle(entity);
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		if ((handleBits & ENTITY_REMOVED) != 0) {
			handle(entity);
		}
	}
	
	public void handle(Entity actor) {
	}
	
	public float delta() {
		return delta;
	}
}
