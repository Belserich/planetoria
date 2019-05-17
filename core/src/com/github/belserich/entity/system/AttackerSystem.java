package com.github.belserich.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.entity.component.Attacker;

public class AttackerSystem extends EntitySystem implements EntityListener {
	
	private Family fam;
	
	public AttackerSystem() {
		fam = Family.all(Attacker.class).get();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		Attacker comp = entity.getComponent(Attacker.class);
		comp.notifier = new AttackerSystem.TouchNotifier(this, entity);
		comp.uiObs.addListener(comp.notifier);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		Attacker comp = entity.getComponent(Attacker.class);
		comp.uiObs.removeListener(comp.notifier);
		comp.notifier = null;
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
		
		private AttackerSystem sys;
		private Entity entity;
		
		public TouchNotifier(AttackerSystem sys, Entity entity) {
			this.sys = sys;
			this.entity = entity;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			sys.touched(entity);
		}
	}
}
