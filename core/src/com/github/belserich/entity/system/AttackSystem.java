package com.github.belserich.entity.system;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.component.AttackableComponent;
import com.github.belserich.entity.component.AttackerComponent;

public class AttackSystem extends EntitySystem implements EntityListener {
	
	private Family fam;
	private Family selection;
	
	public AttackSystem() {
		
		fam = Family.all(
				AttackableComponent.class,
				AttackableComponent.Attacked.class
		).get();
		
		selection = Family.all(
				AttackComponent.class,
				AttackerComponent.class,
				AttackerComponent.Select.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
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
			} else GameClient.log(this, "* Attack. Not all selected entities have remaining attacks!");
		}
		
		entity.remove(AttackableComponent.Attacked.class);
		for (Entity other : sel) {
			other.remove(AttackerComponent.Select.class);
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
	
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addEntityListener(fam, this);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.addEntityListener(fam, this);
	}
	
//	@Subscribe
//	public void on(Interact ev) {
//
//		Entity source = ev.sourceCard();
//		if (mapper.has(source)) {
//
//			Entity[] attackers = ev.all();
//			float attackPts = 0;
//
//			// ensure all selected entities can attack
//			for (Entity att : attackers) {
//				comp = mapper.get(att);
//				if (comp.attCount <= 0) {
//					return;
//				}
//			}
//
//			for (Entity att : attackers) {
//				comp = mapper.get(att);
//				attackPts += comp.pts;
//				comp.attCount--;
//			}
//
//			queueEvent(new Attack(ev, attackPts));
//			GameClient.log(this, "! Attack. Attackers: " + attackers.length + "; Attack points: " + attackPts);
//		}
//	}
}
