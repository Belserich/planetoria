package com.github.belserich.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.entity.component.Attacker;

import java.util.HashMap;
import java.util.Map;

public class AttackerSystem extends EntitySystem implements EntityListener {
	
	private Family fam;
	private Map<Entity, AttackerSystem.TouchNotifier> notifiers;
	
	public AttackerSystem() {
		fam = Family.all(Attacker.class).get();
		notifiers = new HashMap<>();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		Attacker comp = entity.getComponent(Attacker.class);
		AttackerSystem.TouchNotifier notifier = new AttackerSystem.TouchNotifier(entity, comp.uiObs);
		notifiers.put(entity, notifier);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		notifiers.get(entity).tryUnregister();
	}
	
	private void touched(Entity entity) {
		
		entity.add(new Attacker.Selected());
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
			this.obs = obs;
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
