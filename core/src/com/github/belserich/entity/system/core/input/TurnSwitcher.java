package com.github.belserich.entity.system.core.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.OwnedByPlayer;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.core.EntityInteractor;

public class TurnSwitcher extends EntityInteractor {
	
	@Override
	public Family actors() {
		return Family.all(
				PlayerId.class,
				Turnable.class,
				Touchable.Released.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				OwnedByPlayer.class,
				Turnable.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		ImmutableArray<Entity> players = getEngine().getEntitiesFor(Family.all(PlayerId.class).get());
		int nextIndex = players.indexOf(actor, true);
		if (nextIndex < 0) {
			GameClient.error(this, "Player %d is invalid!", actor.getComponent(PlayerId.class).val);
			return;
		}
		
		nextIndex = (nextIndex + 1) % players.size();
		PlayerId pc = players.get(nextIndex).getComponent(PlayerId.class);
		
		GameClient.log(this, "! Turn change. Player " + pc.val + "'s turn.");
		
		for (Entity other : selection) {
			
			OwnedByPlayer obp = other.getComponent(OwnedByPlayer.class);
			if (obp.id == pc.val) {
				other.add(new Turnable.On());
			} else other.remove(Turnable.On.class);
		}
	}
}
