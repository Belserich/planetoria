package com.github.belserich.entity.core;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.entity.event.core.EntityEvent;
import com.github.belserich.entity.event.core.EventQueue;

import java.util.HashSet;
import java.util.Set;

/**
 * An entity event system is an entity system that updates only interesting entities. An entity is considered interesting, when an event related to that entity has recently been
 * posted.
 *
 * @param <T> The component type
 */
public abstract class EntityEvSystem<T extends Component> extends EntitySystem implements EntityUpdater {
	
	/**
	 * Mapper corresponding to the defined component type this system handles
	 */
	protected final ComponentMapper<T> mapper;
	/**
	 * Family of this system
	 */
	private final Family family;
	/**
	 * Interesting entities flagged for addition
	 */
	private final Set<Entity> addition;
	/**
	 * Interesting entities that have been added
	 */
	private final Set<Entity> flagged;
	/**
	 * Entities flagged for removal
	 */
	private final Set<Entity> removal;
	/**
	 * Placeholder for individual components
	 */
	protected T comp;
	/**
	 * The elapsed time since last update
	 */
	protected float delta;
	/**
	 * Array of entities in this system
	 */
	private ImmutableArray<Entity> entities;
	/**
	 * Event queue for posting interesting entity events
	 */
	private EventQueue eventBus;
	
	private AutoFlagger autoFlagger;
	
	/**
	 * Creates a new entity system with specified queue object and component type. Prohibits automatic entity flagging.
	 *
	 * @param eventBus event handler
	 * @param clazz    component type (must be same as generic context)
	 */
	public EntityEvSystem(EventQueue eventBus, Class<T> clazz) {
		this(eventBus, false, clazz);
	}
	
	/**
	 * Creates a new entity system with specified queue object and component type and sets it up for automatic flagging of entities added to an engine.
	 *
	 * @param eventBus event handler
	 * @param autoFlag whether to automatically flag added entities as interesting or not
	 * @param clazz    component type (must be same as generic context)
	 */
	public EntityEvSystem(EventQueue eventBus, boolean autoFlag, Class<T> clazz) {
		super();
		this.eventBus = eventBus;
		
		mapper = ComponentMapper.getFor(clazz);
		family = Family.all(clazz).get();
		
		eventBus.register(this);
		
		addition = new HashSet<Entity>();
		flagged = new HashSet<Entity>();
		removal = new HashSet<Entity>();
		
		autoFlagger = new AutoFlagger(this, autoFlag);
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
		entities = null;
		engine.removeEntityListener(autoFlagger);
	}
	
	/**
	 * Updates the elapsed time, dispatches queued events and finally updates the entities in that order.
	 *
	 * @param delta time since last update in seconds
	 */
	@Override
	public void update(float delta) {
		updateDelta(delta);
		dispatchEvents();
		update();
	}
	
	/**
	 * Updates time (delta) since last update call
	 *
	 * @param delta time since last update in seconds
	 */
	protected void updateDelta(float delta) {
		this.delta = delta;
	}
	
	/**
	 * Posts queued events that have occurred since last dispatch.
	 */
	protected void dispatchEvents() {
		eventBus.dispatch();
	}
	
	/**
	 * Adds entities flagged for addition, then removes entities flagged for removal. Finally, calls {@link EntityEvSystem#justAdded(Entity)},
	 * {@link EntityEvSystem#updateInterestingEntities()} and {@link EntityEvSystem#justRemoved(Entity)} in that order.
	 */
	protected void update() {
		
		Entity[] additionArr = addition.toArray(new Entity[0]);
		Entity[] removalArr = removal.toArray(new Entity[0]);
		
		flagged.addAll(addition);
		addition.clear();
		
		flagged.removeAll(removal);
		removal.clear();
		
		for (Entity entity : additionArr) {
			justAdded(entity);
		}
		
		updateInterestingEntities();
		
		for (Entity entity : removalArr) {
			justRemoved(entity);
		}
	}
	
	protected void updateInterestingEntities() {
		for (Entity entity : flagged) {
			update(entity);
		}
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
	 * Flags an entity for removal. If it has been previously flagged it is never updated.
	 *
	 * @param entity entity to unflag
	 */
	public void unflag(Entity entity) {
		removal.add(entity);
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
	
	public void dispose() {
		
		comp = null;
		delta = 0;
		
		entities = null;
		
		addition.clear();
		flagged.clear();
		removal.clear();
		
		autoFlagger.dispose();
		autoFlagger = null;
	}
	
	/**
	 * An auto flagger handles automatic entity flagging upon addition to an engine
	 */
	class AutoFlagger implements EntityListener {
		
		private EntityEvSystem ref;
		private boolean autoFlag;
		
		public AutoFlagger(EntityEvSystem ref, boolean autoFlag) {
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
		
		private void dispose() {
			ref = null;
			autoFlag = false;
		}
	}
}
