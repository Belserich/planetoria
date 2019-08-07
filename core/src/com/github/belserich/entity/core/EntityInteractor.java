package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public abstract class EntityInteractor extends EntityActor {
	
	private ImmutableArray<Entity> selection;
	
	public EntityInteractor() {
		super();
	}
	
	public EntityInteractor(int priority) {
		super(priority);
	}
	
	protected Family iactors() {
		return null;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		selection = engine.getEntitiesFor(iactors());
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		selection = new ImmutableArray<>(new Array<>());
	}
	
	@Override
	public final void entityUpdate(Entity actor) {
		entityUpdate(actor, selection);
	}
	
	@Override
	public final void entityAdded(Entity actor) {
		entityAdded(actor, selection);
	}
	
	@Override
	public final void entityRemoved(Entity actor) {
		entityRemoved(actor, selection);
	}
	
	public void entityRemoved(Entity actor, ImmutableArray<Entity> selection) {
		// to be overwritten
	}
	
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		// to be overwritten
	}
	
	public void entityUpdate(Entity actor, ImmutableArray<Entity> selection) {
		// to be overwritten
	}
}
