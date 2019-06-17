package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardHandle;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.ui.core.UiService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TouchableSystem extends EventSystem implements EntityListener {
	
	private UiService uiService;
	private Set<Entity> touchedEntities;
	
	public TouchableSystem() {
		super(Family.all(
				CardHandle.class,
				Touchable.class
		).get(), Touchable.Touched.class);
		
		uiService = Services.getUiService();
		touchedEntities = Collections.synchronizedSet(new HashSet<>());
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
		
		CardHandle hc = entity.getComponent(CardHandle.class);
		
		uiService.setCardTouchCallback(hc.handle, () -> touchedEntities.add(entity));
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		CardHandle hc = entity.getComponent(CardHandle.class);
		
		uiService.removeCardTouchCallback(hc.handle);
	}
	
	@Override
	public void updateSystemEntities() {
		
		for (Entity entity : touchedEntities) {
			entity.add(new Touchable.Touched());
		}
		touchedEntities.clear();
	}
}
