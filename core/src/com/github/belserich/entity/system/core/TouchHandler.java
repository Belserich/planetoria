package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EntityActorSystem;
import com.github.belserich.ui.core.UiService;

public class TouchHandler extends EntityActorSystem {
	
	private static final UiService service = Services.getUiService();
	
	public TouchHandler(int handleBits) {
		super(handleBits);
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
	public void act(Entity actor) {
		
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
			entity.add(new Touchable.Touched());
		}
	}
}
