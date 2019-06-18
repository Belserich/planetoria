package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.IntMap;
import com.github.belserich.ui.StdZoneStrategy;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseUiService implements UiService {
	
	protected ZoneStrategy zoneStrat;
	
	private IntMap<CardActor> cardActors;
	private Map<Actor, TouchNotifier> notifiers;
	private int nextCardHandle;
	
	public BaseUiService() {
		
		zoneStrat = new StdZoneStrategy();
		cardActors = new IntMap<>();
		notifiers = new HashMap<>();
		nextCardHandle = 0;
	}
	
	@Override
	public int addCard(int zoneId, int fieldId, String name, float lp, float ap, float sp) {
		
		ZoneActor zone = zoneStrat.get(zoneId);
		
		if (zone != null) {
			return addCard(zone, fieldId, name, lp, ap, sp);
		} else debug("Failed to add card '%s'. Invalid zone id (%d).", name, zoneId);
		
		return -1;
	}
	
	@Override
	public int addCard(int zoneId, String name, float lp, float ap, float sp) {
		
		ZoneActor zone = zoneStrat.get(zoneId);
		
		if (zone != null) {
			int fieldId = zone.nextFreeFieldId();
			if (fieldId != -1) {
				return addCard(zone, fieldId, name, lp, ap, sp);
			} else debug("Failed to add card '%s'. Zone %d is fully occupied.", name, zoneId);
		} else debug("Failed to add card '%s'. Invalid zone id (%d).", name, zoneId);
		
		return -1;
	}
	
	@Override
	public void removeCard(int handle) {
		
		CardActor card = cardActors.remove(handle);
		if (card != null) {
			card.remove();
		} else debug("Failed to remove card. Invalid handle (%d).", handle);
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
	public void setCardTouchCallback(int cardHandle, Runnable run) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			
			TouchNotifier notifier = new TouchNotifier(run);
			notifiers.put(card, notifier);
			card.addListener(notifier);
		}
	}
	
	@Override
	public void removeCardTouchCallback(int cardHandle) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			
			TouchNotifier notifier = notifiers.remove(card);
			if (notifier != null) {
				card.removeListener(notifier);
			} else debug("Failed to remove touch callback. No instance associated with the specified card handle.");
		}
	}
	
	@Override
	public void setFieldTouchCallback(int zoneId, int fieldId, Runnable run) {
		
		FieldActor field = validateFieldActor(zoneId, fieldId);
		if (field != null) {
			
			TouchNotifier notifier = new TouchNotifier(run);
			notifiers.put(field, notifier);
			field.addListener(notifier);
		}
	}
	
	@Override
	public void removeFieldTouchCallback(int zoneId, int fieldId) {
		
		FieldActor field = validateFieldActor(zoneId, fieldId);
		if (field != null) {
			
			TouchNotifier notifier = notifiers.remove(field);
			if (notifier != null) {
				field.removeListener(notifier);
			} else debug("Failed to remove touch callback. No instance associated with the specified field id (%d).", fieldId);
		}
	}
	
	private CardActor validateCardActor(int handle) {
		
		CardActor card = cardActors.get(handle);
		if (card == null) {
			debug("Failed to retrieve card. Invalid handle (%d).", handle);
			return null;
		} else return card;
	}
	
	private void updateCard(CardActor card, String name, float lp, float ap, float sp) {
		
		card.setTitle(name);
		card.setLp(lp);
		card.setAp(ap);
		card.setSp(sp);
		card.update();
	}
	
	private FieldActor validateFieldActor(int zoneId, int fieldId) {
		
		ZoneActor zone = validateZoneActor(zoneId);
		if (zone != null) {
			
			FieldActor field = zone.getFieldActor(fieldId);
			if (field == null) {
				debug("Failed to retrieve field actor (zid: %d, fid: %d)", zoneId, fieldId);
				return null;
			} else return field;
		} else return null;
	}
	
	private ZoneActor validateZoneActor(int zoneId) {
		
		ZoneActor zone = zoneStrat.get(zoneId);
		if (zone == null) {
			debug("Failed to retrieve zone actor (id: %d)", zoneId);
			return null;
		} else return zone;
	}
	
	private int addCard(ZoneActor zone, int fieldId, String name, float lp, float ap, float sp) {
		
		CardActor card = new CardActor(name, lp, ap, sp);
		
		if (zone.addCardActor(fieldId, card)) {
			cardActors.put(nextCardHandle, card);
			return nextCardHandle++;
		} else debug("Failed to add card '%s'. Field %d on zone %d is already occupied or doesn't exist.", name, fieldId, zone.id());
		
		return -1;
	}
	
	private static void debug(String fstr, Object... args) {
		System.err.println(String.format(Locale.getDefault(), fstr, args));
	}
	
	class TouchNotifier extends ClickListener {
		
		private Runnable runnable;
		
		public TouchNotifier(Runnable runnable) {
			this.runnable = runnable;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			runnable.run();
		}
	}
}
