package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.Card;
import com.github.belserich.entity.core.EntityMaintainer;

public class CardUiSystem extends EntityMaintainer {
	
	public CardUiSystem() {
		
		super(Family.all(
				Card.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity) {
		Services.getUiService().addCard(entity);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		Services.getUiService().removeCard(entity);
	}
}
