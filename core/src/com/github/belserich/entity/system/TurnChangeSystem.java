package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.OwnedByPlayer;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.component.Turn;
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
				OwnedByPlayer.class
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
			
			OwnedByPlayer pc = owned.getComponent(OwnedByPlayer.class);
			if (pc.id == pic.val) {
				owned.add(new Turn());
			} else {
				owned.remove(Turn.class);
			}
		}
		
		GameClient.log(this, "! Turn change. Player " + pic.val + "'s turn.");
	}
	
	@Override
	public void entityRemoved(Entity entity) {
		// nothing
	}
}
