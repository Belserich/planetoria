package com.github.belserich.entity.core;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public abstract class EntityActor extends EntitySystem implements EntityListener {
	
	private ImmutableArray<Entity> actors;
	private float delta;
	
	public EntityActor() {
		super(10);
	}
	
	public EntityActor(int priority) {
		super(priority);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		
		engine.addEntityListener(actors(), this);
		actors = engine.getEntitiesFor(actors());
		
		setProcessing(true);
	}
	
	protected Family actors() {
		return null;
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		
		actors = new ImmutableArray<>(new Array<>());
		engine.removeEntityListener(this);
		
		setProcessing(false);
	}
	
	@Override
	public final void update(float delta) {
		
		this.delta = delta;
		updateEntities();
	}
	
	public void updateEntities() {
		
		for (Entity actor : actors) {
			entityUpdate(actor);
		}
	}
	
	public void entityUpdate(Entity entity) {
		// to be overwritten
	}
	
	@Override
	public void entityAdded(Entity entity) {
		// to be overwritten
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		// to be overwritten
	}
	
	public float delta() {
		return delta;
	}
}
