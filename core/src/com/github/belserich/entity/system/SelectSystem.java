package com.github.belserich.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Selectable;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.component.Turn;
import com.github.belserich.entity.core.EntitySystem;

public class SelectSystem extends EntitySystem {
	
	public SelectSystem() {
		super(Family.all(
				Turn.class,
				Touchable.Touched.class,
				Selectable.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		entity.remove(Touchable.Touched.class);
		
		if (ComponentMapper.getFor(Selectable.Selected.class).has(entity)) {
			entity.remove(Selectable.Selected.class);
			GameClient.log(this, "! Entity unselected.");
		} else {
			GameClient.log(this, "! Entity selected.");
			entity.add(new Selectable.Selected());
		}
	}
}
