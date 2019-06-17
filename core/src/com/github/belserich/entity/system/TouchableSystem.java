package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.ui.core.UiService;

public class TouchableSystem extends EventSystem implements EntityListener {
	
	private UiService uiService;
	
	public TouchableSystem() {
		super(Family.all(
				Touchable.class
		).get(), Touchable.Touched.class);
		
		uiService = Services.getUiService();
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
	
	@Override
	public void entityAdded(Entity entity) {
//		uiService.setCardTouchCallback(entity); TODO
	}
	
	@Override
	public void entityRemoved(Entity entity) {
//		uiService.removeCardTouchCallback(entity);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
//		for (Entity entity : Services.getUiService().touchedEntities()) {
//			entity.add(new Touchable.Touched());
//		}
	}
}
