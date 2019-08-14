package com.github.belserich.entity.system.core.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityInteractor;

public class TurnSwitcher extends EntityInteractor {
	
	@Override
	public Family actors() {
		return Family.all(
				Ui.class,
				Turnable.class,
				OwnedByPlayer.class,
				Touchable.Released.class
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
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		OwnedByPlayer obp = actor.getComponent(OwnedByPlayer.class);
		for (int i = 0; i < selection.size(); i++) {
			
			Entity player = selection.get(i);
			PlayerId pc = player.getComponent(PlayerId.class);
			if (pc.val == obp.id) {
				
				int nextIndex = (i + 1) % selection.size();
				player.remove(Turnable.HasTurn.class);
				
				Entity nextPlayer = selection.get(nextIndex);
				nextPlayer.add(new Turnable.HasTurn());
				
				GameClient.log(this, "! Turn change. Player " + nextPlayer.getComponent(PlayerId.class).val + "'s turn.");
			}
		}
	}
}
