package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityMaintainer;
import com.github.belserich.entity.core.EntitySystem;
import com.github.belserich.ui.core.CardUpdater;
import com.github.belserich.ui.core.UiService;

import java.util.Arrays;
import java.util.List;

public class CardUiSystem extends EntitySystem {
	
	private UiService service;
	
	private List<EntitySystem> subSystems;
	
	public CardUiSystem() {
		super(Family.all(
				CardId.class,
				CardType.class
		).exclude(
				Dead.class
		).get());
		
		service = Services.getUiService();
		
		subSystems = Arrays.asList(new Creator(), new Destroyer(), new Mover());
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		for (EntitySystem sys : subSystems) {
			engine.addSystem(sys);
		}
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		for (EntitySystem sys : subSystems) {
			engine.addSystem(sys);
		}
	}
	
	@Override
	public void update(Entity card) {
		
		CardId hc = card.getComponent(CardId.class);
		CardType tc = card.getComponent(CardType.class);
		
		CardUpdater up = service.getCardUpdater(hc.val);
		up.setType(tc.type);
		
		Name nc = card.getComponent(Name.class);
		up.setTitle(nc != null ? nc.name : "???");
		
		Covered cc = card.getComponent(Covered.class);
		up.setCovered(cc != null);
		
		switch (tc.type) {
			
			case DEFAULT:
				
				Lp lc = card.getComponent(Lp.class);
				Ap ac = card.getComponent(Ap.class);
				Sp sc = card.getComponent(Sp.class);
				
				up.setLp(lc != null ? lc.pts : 0);
				up.setAp(ac != null ? ac.pts : 0);
				up.setSp(sc != null ? sc.pts : 0);
				
				break;
			
			case STRATEGY:
				
				Effect ec = card.getComponent(Effect.class);
				up.setEffect(ec != null ? ec.text : "");
				
				break;
			
			default:
				
				GameClient.error(this, "* Card update. Unknown card type: %s", tc.type);
				break;
		}
		
		up.update();
	}
	
	private class Creator extends EntitySystem {
		
		public Creator() {
			super(Family.all(
					CardId.Request.class,
					OwnedByField.class
			).get());
		}
		
		@Override
		public void update(Entity entity) {
			
			OwnedByField fc = entity.getComponent(OwnedByField.class);
			
			int cardId = service.addCard(fc.id);
			if (cardId != -1) {
				
				GameClient.log(this, "! Card creation. Creating card %d.", cardId);
				entity.remove(CardId.Request.class);
				entity.add(new CardId(cardId));
			}
		}
	}
	
	private class Destroyer extends EntityMaintainer {
		
		public Destroyer() {
			super(Family.all(
					CardId.class,
					Dead.class
			).get());
		}
		
		@Override
		public void entityAdded(Entity entity) {
			
			CardId cid = entity.getComponent(CardId.class);
			service.removeCard(cid.val);
		}
		
		@Override
		public void entityRemoved(Entity entity) {
			// nothing
		}
	}
	
	private class Mover extends EntityMaintainer {
		
		public Mover() {
			super(Family.all(
					OwnedByField.class,
					CardId.class,
					Playable.Just.class
			).get());
		}
		
		@Override
		public void entityAdded(Entity entity) {
			
			OwnedByField oc = entity.getComponent(OwnedByField.class);
			CardId cid = entity.getComponent(CardId.class);
			
			service.setCardOnField(cid.val, oc.id);
		}
		
		@Override
		public void entityRemoved(Entity entity) {
			// nothing
		}
	}
}
