package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;

public class TurnSystem extends EntitySystem {
	
	private Family ownedByPlayer;
	private Family players;
	
	private Entity turnEntity;
	
	public TurnSystem() {
		super(Family.all(
				PlayerId.class,
				Turn.class
				).get(),
				new EpConsumerHandler());
		
		ownedByPlayer = Family.all(
				OwnedByPlayer.class
		).get();
		
		players = Family.all(
				PlayerId.class
		).get();
		
		Services.getUiService().setTurnCallback(() -> {
			
			ImmutableArray<Entity> playerEntities = getEngine().getEntitiesFor(players);
			playerEntities.get((playerEntities.indexOf(turnEntity, true) + 1) % playerEntities.size()).add(new Turn());
		});
	}
	
	@Override
	public void entityAdded(Entity entity) {
		
		if (this.turnEntity != null) {
			this.turnEntity.remove(Turn.class);
		}
		
		PlayerId pic = entity.getComponent(PlayerId.class);
		
		ImmutableArray<Entity> ownedList = super.getEngine().getEntitiesFor(ownedByPlayer);
		for (Entity owned : ownedList) {
			
			OwnedByPlayer pc = owned.getComponent(OwnedByPlayer.class);
			if (pc.id == pic.val) {
				owned.add(new Turn());
			} else owned.remove(Turn.class);
		}
		
		GameClient.log(this, "! Turn change. Player " + pic.val + "'s turn.");
		
		this.turnEntity = entity;
		entity.add(new Turn());
	}
	
	private static class EpConsumerHandler extends EntitySystem {
		
		public EpConsumerHandler() {
			super(Family.all(
					Turn.class,
					Playable.Just.class,
					EpConsuming.class
			).get());
		}
		
		@Override
		public void entityAdded(Entity entity) {
			
			EpConsuming ec = entity.getComponent(EpConsuming.class);
			entity.add(new EpConsuming.Is(ec.val));
		}
		
		@Override
		public void entityRemoved(Entity entity) {
			
			entity.remove(EpConsuming.Is.class);
		}
	}
}
