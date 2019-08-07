package com.github.belserich.entity.system.core.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityInteractor;

import java.util.concurrent.atomic.AtomicBoolean;

public class TurnHandler extends EntityInteractor {
	
	private AtomicBoolean turnChanged;
	
	public TurnHandler() {
		
		turnChanged = new AtomicBoolean(false);
		Services.getUiService().setTurnCallback(() -> turnChanged.set(true));
	}
	
	@Override
	public void updateEntities() {
		if (turnChanged.getAndSet(false)) {
			super.updateEntities();
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
	public void entityUpdate(Entity actor, ImmutableArray<Entity> selection) {
		
		int nextIndex = (selection.indexOf(actor, true) + 1) % selection.size();
		Entity next = selection.get(nextIndex);
		
		PlayerId pc = next.getComponent(PlayerId.class);
		
		GameClient.log(this, "! Turn change. Player " + pc.val + "'s turn.");
		
		next.add(new Turnable.On());
		actor.remove(Turnable.On.class);
	}
}
