package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.CardAttackEvent;
import com.github.belserich.entity.event.attack.CardAttackLpEvent;
import com.github.belserich.entity.event.core.EventQueue;
import com.google.common.eventbus.Subscribe;

public class LifeSystem extends EntityEvSystem<LifeComponent> {
	
	public LifeSystem(EventQueue eventBus) {
		super(eventBus, LifeComponent.class);
	}
	
	@Subscribe
	public void on(CardAttackEvent ev) {
		
		Entity attacked = ev.attacked();
		if (mapper.has(attacked)) {
			
			comp = mapper.get(attacked);
			float oldPts = comp.pts;
			comp.pts = comp.pts - ev.attackPts();
			
			GameClient.log(this, "Card attacked! Old LP: " + oldPts + "; New LP: " + comp.pts);
			queueEvent(new CardAttackLpEvent(ev, oldPts, comp.pts));
		}
	}
}
