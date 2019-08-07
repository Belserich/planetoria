package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.OwnedByField;
import com.github.belserich.entity.component.Playable;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.UiService;

public class CardUiMover extends EntityActor {
	
	private static final UiService service = Services.getUiService();
	
	@Override
	public Family actors() {
		return Family.all(
				OwnedByField.class,
				CardId.class,
				Playable.Just.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		OwnedByField oc = entity.getComponent(OwnedByField.class);
		CardId cid = entity.getComponent(CardId.class);
		
		GameClient.log(this, "! Card move. Move card %d to field %d.", cid.val, oc.id);
		service.setCardOnField(cid.val, oc.id);
	}
}
