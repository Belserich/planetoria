package com.github.belserich.entity.system.core.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.UiService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TouchHandler extends EntityActor {
	
	private static final UiService service = Services.getUiService();
	
	private final Set<Entity> justTouched;
	private final Set<Entity> processed;
	
	public TouchHandler() {
		
		justTouched = Collections.synchronizedSet(new HashSet<>());
		processed = Collections.synchronizedSet(new HashSet<>());
	}
	
	@Override
	public Family actors() {
		return Family.all(
				Touchable.class
		).one(
				CardId.class,
				FieldId.class
		).get();
	}
	
	@Override
	public void updateEntities() {
		
		Iterator<Entity> it = processed.iterator();
		Entity actor;
		
		while (it.hasNext()) {
			
			actor = it.next();
			actor.remove(Touchable.Touched.class);
			it.remove();
		}
		
		synchronized (justTouched) {
			
			it = justTouched.iterator();
			while (it.hasNext()) {
				
				actor = it.next();
				actor.add(new Touchable.Touched());
				processed.add(actor);
				it.remove();
			}
		}
		
		super.updateEntities();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		FieldId fc = actor.getComponent(FieldId.class);
		CardId hc = actor.getComponent(CardId.class);
		
		if (hc != null) {
			service.setCardClickCallback(hc.val, new TouchNotifier(actor));
		} else {
			service.setFieldClickCallback(fc.id, new TouchNotifier(actor));
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
			synchronized (justTouched) {
				justTouched.add(entity);
			}
		}
	}
}
