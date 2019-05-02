package com.github.belserich.entity.event.attack;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;
import com.github.belserich.entity.event.base.CardInteractBaseEvent;

public class CardAttackEvent extends CardAttackBaseEvent {
	
	public CardAttackEvent(CardInteractBaseEvent ev, float attackPts) {
		super(ev, attackPts);
	}
	
	public CardAttackEvent(Entity sourceCard, Entity destCard, float attackPts) {
		super(sourceCard, destCard, attackPts);
	}
}
