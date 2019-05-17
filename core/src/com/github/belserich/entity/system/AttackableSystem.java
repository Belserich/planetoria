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
	public void entityAdded(Entity entity) {
		
		AttackableComponent comp = entity.getComponent(AttackableComponent.class);
		comp.notifier = new TouchNotifier(this, entity);
		comp.uiObs.addListener(comp.notifier);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		// TODO
	}
	
	private void touched(Entity entity) {
		
		entity.add(new AttackableComponent.Touched());
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
