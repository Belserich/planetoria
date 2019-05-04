package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.AttackLp;
import com.github.belserich.entity.event.attack.AttackNoSp;
import com.github.belserich.entity.event.attack.DestroyLp;
import com.github.belserich.entity.event.core.EventQueue;
import com.google.common.eventbus.Subscribe;

public class LifeSystem extends EntityEvSystem<LifeComponent> {
	
	public LifeSystem(EventQueue eventBus) {
		super(eventBus, LifeComponent.class);
	}
	
	@Subscribe
	public void on(AttackNoSp ev) {
		
		Entity attacked = ev.attacked();
		if (mapper.has(attacked)) {
			
			comp = mapper.get(attacked);
			float oldPts = comp.pts;
			comp.pts = comp.pts - ev.attackPts();
			
			GameClient.log(this, "! Life attack. Old LP: " + oldPts + "; New LP: " + comp.pts);
			queueEvent(new AttackLp(ev, oldPts, comp.pts));
			
			if (comp.pts <= 0) {
				GameClient.log(this, "! Card death.");
				queueEvent(new DestroyLp(ev.primary(), ev.destCard(), ev.attackPts(), oldPts, ev.secondary()));
			}
		}
	}
}
