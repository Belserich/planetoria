package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityMaintainer;

public class TurnChangeSystem extends EntityMaintainer {
	
	private Family ownedByPlayer;
	private Family allPlayers;
	
	private Entity turnEntity;
	
	public TurnChangeSystem() {
		super(Family.all(
				PlayerId.class,
				Turn.class
		).get());
		
		ownedByPlayer = Family.all(
				PlayerOwned.class
		).get();
		
		allPlayers = Family.all(
				PlayerId.class
		).get();
		
		Services.getUiService().setTurnCallback(() -> {
			
			ImmutableArray<Entity> playerEntities = getEngine().getEntitiesFor(allPlayers);
			playerEntities.get((playerEntities.indexOf(turnEntity, true) + 1) % playerEntities.size()).add(new Turn());
		});
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		if (this.turnEntity != null) {
			this.turnEntity.remove(Turn.class);
		}
		this.turnEntity = entity;
		
		PlayerId pic = entity.getComponent(PlayerId.class);
		
		ImmutableArray<Entity> ownedList = super.getEngine().getEntitiesFor(ownedByPlayer);
		for (Entity owned : ownedList) {
			
			PlayerOwned pc = owned.getComponent(PlayerOwned.class);
			if (pc.id == pic.val) {
				owned.remove(Attackable.class);
				owned.add(new Selectable());
				owned.add(new Attacker());
			} else {
				owned.remove(Selectable.class);
				owned.remove(Attacker.class);
				owned.add(new Attackable());
			}
		}
		
		GameClient.log(this, "! Turn change. Player " + pic.val + "'s turn.");
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		// nothing
	}
}
