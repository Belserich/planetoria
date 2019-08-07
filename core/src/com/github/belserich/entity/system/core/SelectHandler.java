package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Selectable;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityActor;

public class SelectHandler extends EntityActor {
	
	@Override
	public Family actors() {
		return Family.all(
				Turnable.On.class,
				Touchable.Touched.class,
				Selectable.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		actor.remove(Touchable.Touched.class);
		
		if (ComponentMapper.getFor(Selectable.Selected.class).has(actor)) {
			actor.remove(Selectable.Selected.class);
			GameClient.log(this, "! Entity unselected.");
		} else {
			GameClient.log(this, "! Entity selected.");
			actor.add(new Selectable.Selected());
		}
	}
}
