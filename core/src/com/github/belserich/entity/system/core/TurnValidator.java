package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityInteractorSystem;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class TurnValidator extends EntityInteractorSystem {
	
	private AtomicBoolean turnChanged;
	
	public TurnValidator(int handleBits) {
		super(handleBits);
		
		turnChanged = new AtomicBoolean(false);
		Services.getUiService().setTurnCallback(() -> turnChanged.set(true));
	}
	
	@Override
	public void update(float delta) {
		if (turnChanged.getAndSet(false)) {
			super.update(delta);
		}
	}
	
	@Override
	public void entityAdded(Entity entity) {
		if (turnChanged.getAndSet(false)) {
			super.entityAdded(entity);
		}
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		if (turnChanged.getAndSet(false)) {
			super.entityRemoved(entity);
		}
	}
	
	@Override
	public Family actors() {
		return Family.all(
				PlayerId.class,
				Turnable.class,
				Turnable.On.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				PlayerId.class,
				Turnable.class
		).get();
	}
	
	@Override
	public void interact(Entity actor, Iterator<Entity> selection) {
		
		Entity next, curr;
		Entity first = selection.next();
		
		curr = first;
		
		while (curr != actor && selection.hasNext()) {
			curr = selection.next();
		}
		
		if (selection.hasNext()) {
			next = selection.next();
		} else next = first;
		
		PlayerId pc = next.getComponent(PlayerId.class);
		
		GameClient.log(this, "! Turn change. Player " + pc.val + "'s turn.");
		
		next.add(new Turnable.On());
		actor.remove(Turnable.On.class);
	}
}
