package com.github.belserich.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.entity.component.AttackableComponent;

public class AttackableSystem extends EntitySystem implements EntityListener {
	
	private Family fam;
	
	public AttackableSystem() {
		fam = Family.all(AttackableComponent.class).get();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		engine.addEntityListener(fam, this);
		for (Entity entity : engine.getEntitiesFor(fam)) {
			entityAdded(entity);
		}
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		engine.removeEntityListener(this);
		for (Entity entity : engine.getEntitiesFor(fam)) {
			entityRemoved(entity);
		}
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		System.out.println("ADD");
		AttackableComponent comp = entity.getComponent(AttackableComponent.class);
		comp.notifier = new TouchNotifier(this, entity);
		comp.uiObs.addListener(comp.notifier);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		AttackableComponent comp = entity.getComponent(AttackableComponent.class);
		comp.uiObs.removeListener(comp.notifier);
		comp.notifier = null;
	}
	
	private void touched(Entity entity) {
		System.out.println("HAII");
	}
	
	public class TouchNotifier extends ClickListener {
		
		private AttackableSystem sys;
		private Entity entity;
		
		public TouchNotifier(AttackableSystem sys, Entity entity) {
			this.sys = sys;
			this.entity = entity;
		}
		
		@Override
		public void clicked(InputEvent ev, float x, float y) {
			sys.touched(entity);
		}
	}
}
