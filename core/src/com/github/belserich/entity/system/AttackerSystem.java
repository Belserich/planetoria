package com.github.belserich.entity.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.Attacker;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.BaseEntitySystem;

public class AttackerSystem extends BaseEntitySystem {
	
	public AttackerSystem() {
		super(Family.all(
				Attacker.class,
				Touchable.Touched.class
		).get());
	}
	
	@Override
	public void update(Entity entity) {
		
		if (ComponentMapper.getFor(Attacker.Selected.class).has(entity)) {
			entity.remove(Attacker.Selected.class);
		} else entity.add(new Attacker.Selected());
	}
}
