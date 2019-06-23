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
				CardId.class
		).exclude(
				Dead.class
		).get());
		
		service = Services.getUiService();
		
		subSystems = Arrays.asList(new Creator(), new Destroyer());
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
		
		Name nc = card.getComponent(Name.class);
		Effect ec = card.getComponent(Effect.class);
		Lp lc = card.getComponent(Lp.class);
		Ap ac = card.getComponent(Ap.class);
		Sp sc = card.getComponent(Sp.class);
		Covered cc = card.getComponent(Covered.class);
		
		Modification mc = card.getComponent(Modification.class);
		
		String name = nc != null ? nc.name : "???";
		String effect = ec != null ? ec.text : "";
		float lp = lc != null ? lc.pts : 0;
		float ap = ac != null ? ac.pts : 0;
		float sp = sc != null ? sc.pts : 0;
		boolean isCovered = cc != null;
		
		CardUpdater up = service.getCardUpdater(hc.id);
		if (up != null) {
			up.setTitle(name);
			up.setEffect(effect);
			up.setLp(lp);
			up.setAp(ap);
			up.setSp(sp);
			up.setCovered(isCovered);
			up.update();
		}
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
			service.removeCard(cid.id);
		}
		
		@Override
		public void entityRemoved(Entity entity) {
			// nothing
		}
	}
}
