package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.SelectionComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.event.select.Select;

public class SelectionSystem extends EntityEvSystem<SelectionComponent> {
	
	public SelectionSystem(EventQueue queue) {
		super(queue, true, SelectionComponent.class);
	}
	
	@Override
	public void justAdded(Entity entity) {
		
		comp = mapper.get(entity);
		comp.notifier = new SelectionNotifier(this, entity);
		comp.observable.addListener(comp.notifier);
	}
	
	@Override
	public void justRemoved(Entity entity) {
		
		comp = mapper.get(entity);
		comp.observable.removeListener(comp.notifier);
		comp.notifier = null;
	}
	
	private void clicked(Entity entity) {
		
		comp = mapper.get(entity);
		
		if (!comp.selection.contains(entity)) {
			comp.selection.add(entity);
			queueEvent(new Select(comp.selection.primary().get(), comp.selection.secondary()));
		} else {
			comp.selection.remove(entity);
		}
		GameClient.log(this, "Selection modify (count: " + comp.selection.count() + ")");
	}
	
	public class SelectionNotifier extends ClickListener {
		
		private SelectionSystem sys;
		private Entity entity;
		
		public SelectionNotifier(SelectionSystem sys, Entity entity) {
			this.sys = sys;
			this.entity = entity;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			sys.clicked(entity);
		}
	}
}
