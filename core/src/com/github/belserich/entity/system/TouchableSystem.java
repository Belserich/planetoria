package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardHandle;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.component.ZoneId;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.ui.core.UiService;

import java.util.HashSet;
import java.util.Set;

public class TouchableSystem extends EventSystem implements EntityListener {
	
	private final UiService uiService;
	private final Set<Entity> touchedEntities;
	
	private Family sfam;
	
	public TouchableSystem() {
		super(Family.all(
				ZoneId.class,
				FieldId.class,
				Touchable.class
		).get(), Touchable.Touched.class);
		
		sfam = Family.all(
				ZoneId.class,
				FieldId.class,
				CardHandle.class,
				Touchable.class
		).get();
		
		uiService = Services.getUiService();
		touchedEntities = new HashSet<>();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addEntityListener(fam, this);
		engine.addEntityListener(sfam, this);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeEntityListener(this);
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		ZoneId zc = entity.getComponent(ZoneId.class);
		FieldId fc = entity.getComponent(FieldId.class);
		CardHandle hc = entity.getComponent(CardHandle.class);
		
		if (hc != null) {
			uiService.setCardTouchCallback(hc.handle, () -> touched(entity));
		} else {
			uiService.setFieldTouchCallback(zc.id, fc.id, () -> touched(entity));
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		ZoneId zc = entity.getComponent(ZoneId.class);
		FieldId fc = entity.getComponent(FieldId.class);
		CardHandle hc = entity.getComponent(CardHandle.class);
		
		if (hc != null) {
			uiService.removeCardTouchCallback(hc.handle);
		} else {
			uiService.removeFieldTouchCallback(zc.id, fc.id);
		}
	}
	
	public void touched(Entity entity) {
		
		synchronized (touchedEntities) {
			touchedEntities.add(entity);
		}
	}
	
	@Override
	public void updateSystemEntities() {
		
		synchronized (touchedEntities) {
			
			for (Entity entity : touchedEntities) {
				entity.add(new Touchable.Touched());
			}
			touchedEntities.clear();
		}
	}
}