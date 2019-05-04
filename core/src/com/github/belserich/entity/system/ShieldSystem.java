package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.CardAttackEvent;
import com.github.belserich.entity.event.attack.CardAttackNoSpEvent;
import com.github.belserich.entity.event.attack.CardDestroySpEvent;
import com.github.belserich.entity.event.core.EventQueue;
import com.google.common.eventbus.Subscribe;

public class ShieldSystem extends EntityEvSystem<ShieldComponent> {
	
	public ShieldSystem(EventQueue eventBus) {
		super(eventBus, ShieldComponent.class);
	}
	
	@Subscribe
	public void on(CardAttackEvent ev) {
		
		Entity attacked = ev.attacked();
		if (mapper.has(attacked)) {
			
			// TODO Logger-Klasse erstellen
			comp = mapper.get(attacked);
			String specLog = "SP: " + comp.pts + "; Attack points: " + ev.attackPts();
			
			if (comp.pts <= 0) {
				
				queueEvent(new CardAttackNoSpEvent(
						ev.sourceCard(),
						ev.destCard(),
						ev.attackPts(),
						ev.secondary()
				));
			} else if (comp.pts <= ev.attackPts()) {
				
				comp.pts = 0;
				
				GameClient.log(this, "! Shield break. " + specLog);
				queueEvent(new CardDestroySpEvent(
						ev.sourceCard(),
						ev.destCard(),
						ev.attackPts(),
						comp.pts,
						ev.secondary())
				);
			} else {
				GameClient.log(this, "! Useless Shield attack. " + specLog);
			}
		}
	}
}
