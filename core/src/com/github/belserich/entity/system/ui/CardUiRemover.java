package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.Dead;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.UiService;

public class CardUiRemover extends EntityActor {
	
	private static final UiService service = Services.getUiService();
	
	@Override
	public Family actors() {
		return Family.all(
				CardId.class,
				Dead.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		CardId cid = actor.getComponent(CardId.class);
		service.removeCard(cid.val);
	}
}
