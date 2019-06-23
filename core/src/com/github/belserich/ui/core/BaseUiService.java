package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.IntMap;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Zones;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseUiService implements UiService {
	
	private IntMap<ZoneActor> zoneActors;
	private IntMap<FieldActor> fieldActors;
	private IntMap<CardActor> cardActors;
	
	private int nextFieldId, nextCardId;
	
	private Map<Actor, ClickListener> clickListeners;
	private Runnable turnCallback;
	
	public BaseUiService() {
		
		zoneActors = new IntMap<>();
		fieldActors = new IntMap<>();
		cardActors = new IntMap<>();
		
		nextFieldId = 0;
		nextCardId = 0;
		
		clickListeners = new HashMap<>();
	}
	
	public void addZone(Zones zone) {
		addZone(zone.ordinal());
	}
	
	protected ZoneActor getZone(Zones zone) {
		
		return zoneActors.get(zone.ordinal());
	}
	
	@Override
	public int addCard(int fieldId) {
		
		CardActor card = new CardActor();
		FieldActor field = validateFieldActor(fieldId);
		if (field != null) {
			
			field.addCard(card);
			
			card.setUserObject(nextCardId);
			cardActors.put(nextCardId, card);
			
			return nextCardId++;
		}
		
		return -1;
	}
	
	@Override
	public void removeCard(int cardId) {
		
		CardActor card = validateCardActor(cardId);
		if (card != null) {
			card.remove();
		}
	}
	
	@Override
	public CardUpdater getCardUpdater(int cardId) {
		
		CardActor card = validateCardActor(cardId);
		if (card != null) {
			return new CardUpdater(card);
		}
		return null;
	}
	
	@Override
	public void setCardOnField(int cardId, int toFieldId) {
		
		CardActor card = validateCardActor(cardId);
		FieldActor toField = validateFieldActor(toFieldId);
		
		if (card != null & toField != null) {
			
			card.remove();
			toField.addCard(card);
		}
	}
	
	@Override
	public void removeCardClickCallback(int cardHandle) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			
			ClickListener clb = clickListeners.remove(card);
			if (clb != null) {
				card.removeListener(clb);
			} else GameClient.error(this, "Failed to remove touch callback. No instance associated with the specified card cardId.");
		}
	}
	
	@Override
	public int addField(int zoneId) {
		
		ZoneActor zone = validateZoneActor(zoneId);
		if (zone != null) {
			
			FieldActor field = new FieldActor();
			field.setUserObject(nextFieldId);
			
			fieldActors.put(nextFieldId, field);
			zone.addActor(field);
			
			return nextFieldId++;
		}
		
		return -1;
	}
	
	@Override
	public void removeField(int fieldId) {
		
		FieldActor field = validateFieldActor(fieldId);
		if (field != null) {
			field.remove();
		}
	}
	
	@Override
	public void setFieldClickCallback(int fieldId, ClickListener clb) {
		
		FieldActor field = validateFieldActor(fieldId);
		if (field != null) {
			
			clickListeners.put(field, clb);
			field.addListener(clb);
		}
	}
	
	@Override
	public void setTurnCallback(Runnable clb) {
		
		this.turnCallback = clb;
	}
	
	@Override
	public void removeTurnCallback() {
		
		this.turnCallback = null;
	}
	
	@Override
	public void setCardClickCallback(int cardHandle, ClickListener clb) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			
			clickListeners.put(card, clb);
			card.addListener(clb);
		}
	}
	
	@Override
	public void removeFieldClickCallback(int fieldId) {
		
		FieldActor field = validateFieldActor(fieldId);
		if (field != null) {
			
			ClickListener clb = clickListeners.remove(field);
			if (clb != null) {
				field.removeListener(clb);
			} else GameClient.error(this, "Failed to remove touch callback. No instance associated with the specified field id (%d).", fieldId);
		}
	}
	
	@Override
	public int addZone(int zoneId) {
		
		ZoneActor zone = new ZoneActor();
		zone.setUserObject(zoneId);
		zoneActors.put(zoneId, zone);
		return zoneId;
	}
	
	@Override
	public void removeZone(int zoneId) {
		
		ZoneActor zone = validateZoneActor(zoneId);
		if (zone != null) {
			zone.remove();
		}
	}
	
	protected void fireTurnCallbacks() {
		
		if (turnCallback != null) {
			turnCallback.run();
		}
	}
	
	private ZoneActor validateZoneActor(int zoneId) {
		
		ZoneActor zone = zoneActors.get(zoneId);
		if (zone != null) {
			return zone;
		} else {
			GameClient.error(this, "Failed to retrieve zone actor (id: %d)", zoneId);
			return null;
		}
	}
	
	private CardActor validateCardActor(int cardId) {
		
		CardActor card = cardActors.get(cardId);
		if (card != null) {
			return card;
		} else {
			GameClient.error(this, "Failed to retrieve card actor (id: %d)", cardId);
			return null;
		}
	}
	
	private FieldActor validateFieldActor(int fieldId) {
		
		FieldActor field = fieldActors.get(fieldId);
		if (field != null) {
			return field;
		} else {
			GameClient.error(this, "Failed to retrieve field actor (id: %d)", fieldId);
			return null;
		}
	}
}
