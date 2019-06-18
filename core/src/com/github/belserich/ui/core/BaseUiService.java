package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.IntMap;
import com.github.belserich.GameClient;
import com.github.belserich.ui.StdZoneStrategy;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseUiService implements UiService {
	
	protected ZoneStrategy zoneStrat;
	
	private IntMap<CardActor> cardActors;
	private Map<Actor, ClickListener> clickListeners;
	private int nextCardHandle;
	
	private Runnable turnCallback;
	
	public BaseUiService() {
		
		zoneStrat = new StdZoneStrategy();
		cardActors = new IntMap<>();
		clickListeners = new HashMap<>();
		nextCardHandle = 0;
	}
	
	@Override
	public int addCard(int zoneId, int fieldId, String name, float lp, float ap, float sp) {
		
		ZoneActor zone = zoneStrat.get(zoneId);
		
		if (zone != null) {
			return addCard(zone, fieldId, name, lp, ap, sp);
		} else GameClient.error(this, "Failed to add card '%s'. Invalid zone id (%d).", name, zoneId);
		
		return -1;
	}
	
	@Override
	public int addCard(int zoneId, String name, float lp, float ap, float sp) {
		
		ZoneActor zone = zoneStrat.get(zoneId);
		
		if (zone != null) {
			int fieldId = zone.nextFreeFieldId();
			if (fieldId != -1) {
				return addCard(zone, fieldId, name, lp, ap, sp);
			} else GameClient.error(this, "Failed to add card '%s'. Zone %d is fully occupied.", name, zoneId);
		} else GameClient.error(this, "Failed to add card '%s'. Invalid zone id (%d).", name, zoneId);
		
		return -1;
	}
	
	@Override
	public void removeCard(int handle) {
		
		CardActor card = cardActors.remove(handle);
		if (card != null) {
			card.remove();
		} else GameClient.error(this, "Failed to remove card. Invalid handle (%d).", handle);
	}
	
	@Override
	public void updateCardName(int handle, String name) {
		
		CardActor card = validateCardActor(handle);
		if (card != null) {
			updateCard(card, name, card.getLp(), card.getAp(), card.getSp());
		}
	}
	
	@Override
	public void updateCardLp(int handle, float lp) {
		
		CardActor card = validateCardActor(handle);
		if (card != null) {
			updateCard(card, card.getTitle(), lp, card.getAp(), card.getSp());
		}
	}
	
	@Override
	public void updateCardAp(int handle, float ap) {
		
		CardActor card = validateCardActor(handle);
		if (card != null) {
			updateCard(card, card.getTitle(), card.getLp(), ap, card.getSp());
		}
	}
	
	@Override
	public void updateCardSp(int handle, float sp) {
		
		CardActor card = validateCardActor(handle);
		if (card != null) {
			updateCard(card, card.getTitle(), card.getLp(), card.getAp(), sp);
		}
	}
	
	@Override
	public boolean changeCardField(int handle, int toZoneId, int toFieldId) {
		
		CardActor card = validateCardActor(handle);
		
		if (card != null) {
			
			card.remove();
			FieldActor toField = validateFieldActor(toZoneId, toFieldId);
			
			if (toField != null) {
				return toField.setCardIfEmpty(card);
			}
		}
		
		return false;
	}
	
	@Override
	public boolean isFieldUnoccupied(int zoneId, int fieldId) {
		
		FieldActor field = validateFieldActor(zoneId, fieldId);
		
		if (field != null) {
			return !field.hasCard();
		}
		
		return false;
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
	public void removeCardClickCallback(int cardHandle) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			
			ClickListener clb = clickListeners.remove(card);
			if (clb != null) {
				card.removeListener(clb);
			} else GameClient.error(this, "Failed to remove touch callback. No instance associated with the specified card handle.");
		}
	}
	
	@Override
	public void setFieldClickCallback(int zoneId, int fieldId, ClickListener clb) {
		
		FieldActor field = validateFieldActor(zoneId, fieldId);
		if (field != null) {
			
			clickListeners.put(field, clb);
			field.addListener(clb);
		}
	}
	
	@Override
	public void removeFieldClickCallback(int zoneId, int fieldId) {
		
		FieldActor field = validateFieldActor(zoneId, fieldId);
		if (field != null) {
			
			ClickListener clb = clickListeners.remove(field);
			if (clb != null) {
				field.removeListener(clb);
			} else GameClient.error(this, "Failed to remove touch callback. No instance associated with the specified field id (%d).", fieldId);
		}
	}
	
	protected void fireTurnCallbacks() {
		
		if (turnCallback != null) {
			turnCallback.run();
		}
	}
	
	private FieldActor validateFieldActor(int zoneId, int fieldId) {
		
		ZoneActor zone = validateZoneActor(zoneId);
		if (zone != null) {
			
			FieldActor field = zone.getFieldActor(fieldId);
			if (field == null) {
				GameClient.error(this, "Failed to retrieve field actor (zid: %d, fid: %d)", zoneId, fieldId);
				return null;
			} else return field;
		} else return null;
	}
	
	private void updateCard(CardActor card, String name, float lp, float ap, float sp) {
		
		card.setTitle(name);
		card.setLp(lp);
		card.setAp(ap);
		card.setSp(sp);
		card.update();
	}
	
	private ZoneActor validateZoneActor(int zoneId) {
		
		ZoneActor zone = zoneStrat.get(zoneId);
		if (zone == null) {
			GameClient.error(this, "Failed to retrieve zone actor (id: %d)", zoneId);
			return null;
		} else return zone;
	}
	
	private CardActor validateCardActor(int handle) {
		
		CardActor card = cardActors.get(handle);
		if (card == null) {
			GameClient.error(this, "Failed to retrieve card. Invalid handle (%d).", handle);
			return null;
		} else return card;
	}
	
	private int addCard(ZoneActor zone, int fieldId, String name, float lp, float ap, float sp) {
		
		CardActor card = new CardActor(name, lp, ap, sp);
		
		if (zone.addCardActor(fieldId, card)) {
			cardActors.put(nextCardHandle, card);
			return nextCardHandle++;
		} else GameClient.error(this, "Failed to add card '%s'. Field %d on zone %d is already occupied or doesn't exist.", name, fieldId, zone.id());
		
		return -1;
	}
}
