package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardHandle;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.core.EntitySystem;

public class CardSpChangeSystem extends EntitySystem {
	
	public CardSpChangeSystem() {
		super(Family.all(
				CardHandle.class
		).one(
				Sp.Broke.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
		
		CardHandle cc = entity.getComponent(CardHandle.class);
		
		Services.getUiService().updateCardSp(cc.handle, 0);
	}
}
