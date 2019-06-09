package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EventSystem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TouchableSystem extends EventSystem implements EntityListener {
	
	private Map<Entity, TouchNotifier> notifiers;
	private Set<Entity> touchedEntities;
	
	public TouchableSystem() {
		super(Family.all(
				Touchable.class
				).get(),
				Touchable.Touched.class);
		
		notifiers = new HashMap<>();
		touchedEntities = new HashSet<>();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addEntityListener(fam, this);
	}
	
	@Override
	public void update(float delta) {
		
		super.update(delta);
		for (Entity entity : touchedEntities) {
			entity.add(new Touchable.Touched());
		}
		touchedEntities.clear();
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeEntityListener(this);
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		Touchable tc = entity.getComponent(Touchable.class);
		TouchNotifier notifier = new TouchNotifier(entity, tc.obs);
		notifiers.put(entity, notifier);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		notifiers.remove(entity).tryUnregister();
	}
	
	private void touched(Entity entity) {
		touchedEntities.add(entity);
	}
	
	private class TouchNotifier extends ClickListener {
		
		private Entity entity;
		private Actor obs;
		
		TouchNotifier(Entity entity, Actor obs) {
			this.entity = entity;
			this.obs = obs;
			obs.addListener(this);
		}
		
		void tryUnregister() {
			if (obs != null) {
				obs.removeListener(this);
			}
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			touched(entity);
		}
	}
}
