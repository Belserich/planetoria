package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.Attack;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.event.interact.Interact;
import com.google.common.eventbus.Subscribe;

public class AttackSystem extends EntityEvSystem<AttackComponent> {
	
	public AttackSystem(EventQueue eventBus) {
		super(eventBus, AttackComponent.class);
	}
	
	@Subscribe
	public void on(Interact ev) {
		
		Entity source = ev.sourceCard();
		if (mapper.has(source)) {
			
			Entity[] attackers = ev.all();
			float attackPts = 0;
			
			// ensure all selected entities can attack
			for (Entity att : attackers) {
				comp = mapper.get(att);
				if (comp.attCount <= 0) {
					return;
				}
			}
			
			for (Entity att : attackers) {
				comp = mapper.get(att);
				attackPts += comp.pts;
				comp.attCount--;
			}
			
			queueEvent(new Attack(ev, attackPts));
			GameClient.log(this, "! Attack. Attackers: " + attackers.length + "; Attack points: " + attackPts);
		}
	}
}
