package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.Card;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.core.EntitySystem;

public class CardAttChangeSystem extends EntitySystem {
	
	public CardAttChangeSystem() {
		super(Family.all(
				Card.class
		).one(
				Sp.Broke.class,
				Lp.Changed.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
//		Services.getUiService().updateCard(entity); TODO
	}
}
