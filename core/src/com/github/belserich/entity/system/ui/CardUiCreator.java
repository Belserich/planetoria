package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.OwnedByField;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.UiService;

public class CardUiCreator extends EntityActor {
	
	private static final UiService service = Services.getUiService();
	
	@Override
	public Family actors() {
		return Family.all(
				CardId.Request.class,
				OwnedByField.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		OwnedByField fc = actor.getComponent(OwnedByField.class);
		
		int cardId = service.addCard(fc.id);
		if (cardId != -1) {
			
			GameClient.log(this, "! Card creation. Creating card %d on field %d.", cardId, fc.id);
			actor.remove(CardId.Request.class);
			actor.add(new CardId(cardId));
		}
	}
}
