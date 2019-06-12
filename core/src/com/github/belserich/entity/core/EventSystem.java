package com.github.belserich.entity.core;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

import java.util.HashMap;
import java.util.Map;

public class EventSystem extends EntitySystem {
	
	/**
	 * References all entities with certain components
	 */
	private Map<Class<? extends Component>, ImmutableArray<Entity>> eventEntities;
	
	/**
	 * Indicates event component types for cyclic removal
	 */
	private Class<? extends Component>[] eventTypes;
	
	public EventSystem(Family fam, Class<? extends Component>... eventTypes) {
		this(fam, 10, eventTypes);
	}
	
	public EventSystem(Family fam, int priority, Class<? extends Component>... eventTypes) {
		super(fam, priority);
		this.eventTypes = eventTypes;
		this.eventEntities = new HashMap<>();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		for (Class<? extends Component> clazz : eventTypes) {
			eventEntities.put(clazz, engine.getEntitiesFor(Family.all(clazz).get()));
		}
	}
	
	/**
	 * Removes all event components from entities before updating.
	 *
	 * @param delta time since last update
	 */
	@Override
	public void update(float delta) {
		
		Class<? extends Component> clazz;
		for (Map.Entry<Class<? extends Component>, ImmutableArray<Entity>> set : eventEntities.entrySet()) {
			for (Entity entity : set.getValue()) {
				clazz = set.getKey();
				entity.remove(clazz);
			}
		}
		super.update(delta);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		eventEntities.clear();
	}
}
