package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.CardAttackEvent;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.event.interact.CardInteractEvent;
import com.google.common.eventbus.Subscribe;

public class AttackSystem extends EntityEvSystem<AttackComponent> {
	
	public AttackSystem(EventQueue eventBus) {
		super(eventBus, AttackComponent.class);
	}
	
	@Subscribe
	public void on(CardInteractEvent ev) {
		
		Entity source = ev.sourceCard();
		if (mapper.has(source)) {
			
			comp = mapper.get(source);
			if (comp.attCount > 0) {
				queueEvent(new CardAttackEvent(ev, comp.pts));
				comp.attCount--;
			}
		}
	}
}
