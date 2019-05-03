package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
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
			
			Entity[] attackers = ev.all();
			float attackPts = 0;
			
			for (Entity att : attackers) {
				comp = mapper.get(att);
				if (comp.attCount > 0) {
					attackPts += comp.pts;
					comp.attCount--;
				}
			}
			
			queueEvent(new CardAttackEvent(ev, attackPts));
			GameClient.log(this, "Card attack! Attackers: " + attackers.length + "; Attack points: " + attackPts);
		}
	}
}
