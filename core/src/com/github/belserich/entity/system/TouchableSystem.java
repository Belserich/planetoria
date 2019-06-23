package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.ui.core.UiService;

import java.util.HashSet;
import java.util.Set;

public class TouchableSystem extends EventSystem implements EntityListener {
	
	private final UiService uiService;
	private final Set<Entity> touchedEntities;
	
	public TouchableSystem() {
		super(Family.all(
				Touchable.class
		).one(
				CardId.class,
				FieldId.class
		).get(), Touchable.Touched.class);
		
		uiService = Services.getUiService();
		touchedEntities = new HashSet<>();
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
		
		FieldId fc = entity.getComponent(FieldId.class);
		CardId hc = entity.getComponent(CardId.class);
		
		if (hc != null) {
			uiService.setCardClickCallback(hc.id, new TouchNotifier(entity));
		} else {
			uiService.setFieldClickCallback(fc.id, new TouchNotifier(entity));
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		FieldId fc = entity.getComponent(FieldId.class);
		CardId hc = entity.getComponent(CardId.class);
		
		if (hc != null) {
			uiService.removeCardClickCallback(hc.id);
		} else {
			uiService.removeFieldClickCallback(fc.id);
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
	
	private class TouchNotifier extends ClickListener {
		
		private Entity entity;
		
		TouchNotifier(Entity entity) {
			this.entity = entity;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			touched(entity);
		}
	}
}
