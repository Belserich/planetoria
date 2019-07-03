package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardId;
import com.github.belserich.entity.component.Dead;
import com.github.belserich.entity.core.EntityActorSystem;
import com.github.belserich.ui.core.UiService;

public class CardUiRemover extends EntityActorSystem {
	
	private static final UiService service = Services.getUiService();
	
	public CardUiRemover(int handleBits) {
		super(handleBits);
	}
	
	@Override
	public Family actors() {
		return Family.all(
				CardId.class,
				Dead.class
		).get();
	}
	
	@Override
	public void act(Entity actor) {
		CardId cid = actor.getComponent(CardId.class);
		service.removeCard(cid.val);
	}
}
