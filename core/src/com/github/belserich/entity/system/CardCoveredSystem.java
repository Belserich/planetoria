package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.CardHandle;
import com.github.belserich.entity.component.Covered;
import com.github.belserich.entity.core.EntityMaintainer;

public class CardCoveredSystem extends EntityMaintainer {
	
	public CardCoveredSystem() {
		super(Family.all(
				CardHandle.class,
				Covered.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		CardHandle cc = entity.getComponent(CardHandle.class);
		Services.getUiService().updateCardCovered(cc.handle, true);
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		
		CardHandle cc = entity.getComponent(CardHandle.class);
		Services.getUiService().updateCardCovered(cc.handle, false);
	}
}
