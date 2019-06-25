package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EntitySystem;
import com.github.belserich.ui.core.UiService;

import java.util.HashSet;
import java.util.Set;

public class TouchSystem extends EntitySystem {
	
	private final Set<Entity> touchedEntities;
	private final UiService uiService;
	
	public TouchSystem() {
		super(Family.all(
				Touchable.class
		).one(
				CardId.class,
				FieldId.class
		).get());
		
		touchedEntities = new HashSet<>();
		uiService = Services.getUiService();
	}

	@Override
	public void entityAdded(Entity entity) {
		
		FieldId fc = entity.getComponent(FieldId.class);
		CardId hc = entity.getComponent(CardId.class);
		
		if (hc != null) {
			uiService.setCardClickCallback(hc.val, new TouchNotifier(entity));
		} else {
			uiService.setFieldClickCallback(fc.id, new TouchNotifier(entity));
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		FieldId fc = entity.getComponent(FieldId.class);
		CardId hc = entity.getComponent(CardId.class);
		
		if (hc != null) {
			uiService.removeCardClickCallback(hc.val);
		} else {
			uiService.removeFieldClickCallback(fc.id);
		}
	}
	
	@Override
	public void update(Entity entity) {
		
		synchronized (touchedEntities) {
			
			if (touchedEntities.remove(entity)) {
				entity.add(new Touchable.Touched());
			}
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
			synchronized (touchedEntities) {
				touchedEntities.add(entity);
			}
		}
	}
}
