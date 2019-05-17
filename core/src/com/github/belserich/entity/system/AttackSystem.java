package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.component.AttackableComponent;
import com.github.belserich.entity.component.AttackerComponent;
import com.github.belserich.entity.core.EventSystem;

public class AttackSystem extends EventSystem {
	
	private Family selection;
	
	public AttackSystem() {
		
		super(Family.all(
				AttackableComponent.class,
				AttackableComponent.Touched.class
				).get(),
				AttackableComponent.Attacked.class,
				AttackableComponent.Touched.class);
		
		selection = Family.all(
				AttackComponent.class,
				AttackerComponent.class,
				AttackerComponent.Selected.class
		).get();
	}
	
	@Override
	public void update(Entity entity) {
		
		ImmutableArray<Entity> sel = getEngine().getEntitiesFor(selection);
		AttackComponent comp;
		float attackPts = 0f;
		
		if (sel.size() != 0) {
			
			for (Entity attacker : sel) {
				
				comp = attacker.getComponent(AttackComponent.class);
				if (comp.attCount <= 0) {
					attackPts = -1f;
					break;
				}
				comp.attCount--;
				attackPts += comp.pts;
			}
			
			if (attackPts != -1) {
				GameClient.log(this, "! Attack. Attackers: " + sel.size() + "; Attack points: " + attackPts);
				entity.add(new AttackableComponent.Attacked(attackPts));
				
			} else GameClient.log(this, "* Attack. Not all selected entities have remaining attacks!");
		}
		
		for (Entity other : sel) {
			other.remove(AttackerComponent.Selected.class);
		}
	}
}
