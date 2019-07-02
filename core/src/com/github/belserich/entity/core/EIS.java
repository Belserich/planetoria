package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * An entity interaction system.
 */
public class EIS extends EAS {
	
	private Family sFam;
	private ImmutableArray<Entity> sTemplate;
	
	public EIS(Family pFam, Family sFam, EntitySystem... subSystems) {
		this(10, pFam, sFam, subSystems);
	}
	
	public EIS(int priority, Family pFam, Family sFam, EntitySystem... subSystems) {
		super(priority, pFam, subSystems);
		this.sFam = sFam;
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		sTemplate = engine.getEntitiesFor(sFam);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		sTemplate = new ImmutableArray<>(new Array<>());
	}
	
	@Override
	public void updateEntities() {
		
		List<Entity> selection = Lists.newArrayList(sTemplate.iterator());
		for (Iterator<Entity> it = entities.iterator(); it.hasNext() && !selection.isEmpty(); ) {
			update(it.next(), selection.iterator());
		}
	}
	
	@Override
	public final void entityAdded(Entity entity) {
		
		if (sTemplate.size() > 0) {
			entityAdded(entity, sTemplate.iterator());
		}
	}
	
	public void entityAdded(Entity entity, Iterator<Entity> selection) {
	}
	
	@Override
	public final void update(Entity entity) {
	}
	
	public void update(Entity entity, Iterator<Entity> selection) {
	}
}
