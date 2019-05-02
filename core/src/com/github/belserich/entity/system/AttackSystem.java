package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.SingleCardAttackEvent;
import com.github.belserich.entity.event.SingleCardUiInteractEvent;
import com.github.belserich.entity.event.core.EventQueue;
import com.google.common.eventbus.Subscribe;

public class AttackSystem extends EntityEvSystem<AttackComponent> {
	
	public AttackSystem(EventQueue eventBus) {
		super(eventBus, AttackComponent.class);
	}
	
	@Subscribe
	public void on(SingleCardUiInteractEvent ev) {
		
		Entity source = ev.sourceCard();
		if (mapper.has(source)) {
			
			comp = mapper.get(source);
			queueEvent(new SingleCardAttackEvent(ev, comp.pts));
		}
	}
}
