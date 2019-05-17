package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Ap;
import com.github.belserich.entity.component.Attackable;
import com.github.belserich.entity.component.Attacker;
import com.github.belserich.entity.core.EventSystem;

public class AttackSystem extends EventSystem {
	
	private Family selection;
	
	public AttackSystem() {
		
		super(Family.all(
				Attackable.class,
				Attackable.Touched.class
				).get(),
				Attackable.Attacked.class,
				Attackable.Touched.class);
		
		selection = Family.all(
				Ap.class,
				Attacker.class,
				Attacker.Selected.class
		).get();
	}
	
	@Override
	public void update(Entity entity) {
		
		ImmutableArray<Entity> sel = getEngine().getEntitiesFor(selection);
		Ap comp;
		float attackPts = 0f;
		
		if (sel.size() != 0) {
			
			for (Entity attacker : sel) {
				
				comp = attacker.getComponent(Ap.class);
				if (comp.attCount <= 0) {
					attackPts = -1f;
					break;
				}
				comp.attCount--;
				attackPts += comp.pts;
			}
			
			if (attackPts != -1) {
				GameClient.log(this, "! Attack. Attackers: " + sel.size() + "; Attack points: " + attackPts);
				entity.add(new Attackable.Attacked(attackPts));
				
			} else GameClient.log(this, "* Attack. Not all selected entities have remaining attacks!");
		}
		
		for (Entity other : sel) {
			other.remove(Attacker.Selected.class);
		}
	}
}
