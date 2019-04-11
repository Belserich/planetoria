package com.github.belserich.entity.core;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.entity.event.core.EntityEvent;
import com.github.belserich.entity.event.core.QueuedEventBus;

import java.util.HashSet;
import java.util.Set;

public class EntityFlagSystem<T extends Component> extends EntitySystem {
	
	protected ComponentMapper<T> mapper;
	protected T comp;
	protected float delta;
	
	private ImmutableArray<Entity> entities;
	private Family family;
	
	private QueuedEventBus eventBus;
	
	/**
	 * All entities in this set are being individually updated.
	 */
	private Set<Entity> addition;
	private Set<Entity> flagged;
	private Set<Entity> removal;
	
	private AutoFlagger autoFlagger;
	
	public EntityFlagSystem(QueuedEventBus eventBus, Class<T> clazz) {
		this(eventBus, false, clazz);
	}
	
	public EntityFlagSystem(QueuedEventBus eventBus, boolean autoFlag, Class<T> clazz) {
		super();
		this.eventBus = eventBus;
		
		eventBus.register(this);
		family = Family.all(clazz).get();
		
		addition = new HashSet<Entity>();
		flagged = new HashSet<Entity>();
		removal = new HashSet<Entity>();
		
		autoFlagger = new AutoFlagger(this, autoFlag);
		
		mapper = ComponentMapper.getFor(clazz);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		entities = engine.getEntitiesFor(family);
		engine.addEntityListener(family, autoFlagger);
		for (Entity entity : entities) {
			autoFlagger.entityAdded(entity);
		}
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		for (Entity entity : entities) {
			autoFlagger.entityRemoved(entity);
		}
		engine.removeEntityListener(autoFlagger);
		entities = null;
	}
	
	@Override
	public void update(float delta) {
		updateDelta(delta);
		dispatchEvents();
		updateEntities();
	}
	
	protected void updateDelta(float delta) {
		this.delta = delta;
	}
	
	protected void dispatchEvents() {
		eventBus.dispatch();
	}
	
	protected void updateEntities() {
		
		Entity[] additionArr = addition.toArray(new Entity[0]);
		Entity[] removalArr = removal.toArray(new Entity[0]);
		
		flagged.addAll(addition);
		addition.clear();
		
		flagged.removeAll(removal);
		removal.clear();
		
		for (Entity entity : additionArr) {
			justFlagged(entity);
		}
		
		updateFlaggedEntities();
		
		for (Entity entity : removalArr) {
			justUnflagged(entity);
		}
	}
	
	protected void justFlagged(Entity entity) {
	}
	
	protected void updateFlaggedEntities() {
		for (Entity entity : flagged) {
			update(entity);
		}
	}
	
	protected void justUnflagged(Entity entity) {
	}
	
	protected void update(Entity entity) {
	}
	
	public boolean isAutoFlag() {
		return autoFlagger.autoFlag;
	}
	
	public void setAutoFlag(boolean autoFlag) {
		autoFlagger.autoFlag = autoFlag;
	}
	
	protected void queueEvent(EntityEvent event) {
		eventBus.queue(event);
	}
	
	/**
	 * Flags an entity for updates.
	 *
	 * @param entity entity to flag
	 */
	public void flag(Entity entity) {
		if (entities.contains(entity, true)) {
			addition.add(entity);
		}
	}
	
	/**
	 * Flags an entity for removal, it will be removed after the next update cycle. If the entity has been flagged
	 * previously it is never updated.
	 *
	 * @param entity entity to unflag
	 */
	public void unflag(Entity entity) {
		removal.add(entity);
	}
	
	public void dispose() {
		
		mapper = null;
		comp = null;
		delta = 0;
		
		family = null;
		eventBus = null;
		autoFlagger.ref = null;
		autoFlagger.autoFlag = false;
		autoFlagger = null;
		
		addition.clear();
		flagged.clear();
		removal.clear();
	}
	
	class AutoFlagger implements EntityListener {
		
		private EntityFlagSystem ref;
		private boolean autoFlag;
		
		public AutoFlagger(EntityFlagSystem ref, boolean autoFlag) {
			this.ref = ref;
			this.autoFlag = autoFlag;
		}
		
		@Override
		public void entityAdded(Entity entity) {
			if (autoFlag) {
				ref.flag(entity);
			}
		}
		
		@Override
		public void entityRemoved(Entity entity) {
			ref.unflag(entity);
		}
	}
}
