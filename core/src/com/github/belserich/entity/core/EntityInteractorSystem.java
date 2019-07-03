package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
		
		FamilyListIterator it = new FamilyListIterator(iactors(), selection.iterator());
		if (it.hasNext()) {
			interact(actor, it);
		}
	}
	
	private static class FamilyListIterator implements Iterator<Entity> {
		
		private final Family fam;
		private final Iterator<Entity> it;
		
		private Entity next;
		private boolean isNext;
		
		public FamilyListIterator(Family fam, Iterator<Entity> it) {
			this.fam = fam;
			this.it = it;
		}
		
		@Override
		public boolean hasNext() {
			
			if (!isNext) {
				do {
					if (!it.hasNext()) {
						return false;
					}
					next = it.next();
				}
				while (!fam.matches(next));
			}
			
			isNext = true;
			return true;
		}
		
		@Override
		public Entity next() {
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			isNext = false;
			return next;
		}
		
		@Override
		public void remove() {
			it.remove();
		}
	}
}
