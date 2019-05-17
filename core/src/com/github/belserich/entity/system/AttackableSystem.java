package com.github.belserich.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.entity.component.Attackable;

import java.util.HashMap;
import java.util.Map;

public class AttackableSystem extends EntitySystem implements EntityListener {
	
	private Family fam;
	private Map<Entity, TouchNotifier> notifiers;
	
	public AttackableSystem() {
		fam = Family.all(Attackable.class).get();
		notifiers = new HashMap<Entity, TouchNotifier>();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		Attackable comp = entity.getComponent(Attackable.class);
		TouchNotifier notifier = new TouchNotifier(entity, comp.uiObs);
		notifiers.put(entity, notifier);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		notifiers.get(entity).tryUnregister();
	}
	
	private void touched(Entity entity) {
		
		entity.add(new Attackable.Touched());
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addEntityListener(fam, this);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeEntityListener(this);
	}
	
	public class TouchNotifier extends ClickListener {
		
		private Entity entity;
		private Actor obs;
		
		public TouchNotifier(Entity entity, Actor obs) {
			this.entity = entity;
			obs.addListener(this);
		}
		
		private void tryUnregister() {
			if (obs != null) {
				obs.removeListener(this);
			}
		}
		
		@Override
		public void clicked(InputEvent ev, float x, float y) {
			touched(entity);
		}
	}
}
