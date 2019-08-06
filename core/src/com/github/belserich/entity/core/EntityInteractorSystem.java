package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

public abstract class EntityInteractorSystem extends BaseEntitySystem implements EntityInteractor {
	
	private ImmutableArray<Entity> selection;
	
	public EntityInteractorSystem(int handleBits) {
		this(10, handleBits);
	}
	
	public EntityInteractorSystem(int priority, int handleBits) {
		super(priority, handleBits);
	}
	
	public EntityInteractorSystem(int handleBits, EntityInteractor handler) {
		this(10, handleBits, handler);
	}
	
	public EntityInteractorSystem(int priority, int handleBits, EntityInteractor handler) {
		super(priority, handleBits);
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
	public void handle(Entity actor) {
		ImmutableArray<Entity> ients = getEngine().getEntitiesFor(iactors());
		if (ients.size() > 0) {
			interact(actor, ients);
		}
	}
}
