package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.IntMap;
import com.github.belserich.ui.StdZoneStrategy;

import java.util.Locale;

public abstract class BaseUiService implements UiService {
	
	protected ZoneStrategy zoneStrat;
	
	private IntMap<CardActor> cardActors;
	private IntMap<TouchNotifier> notifiers;
	private int nextCardHandle;
	
	public BaseUiService() {
		
		zoneStrat = new StdZoneStrategy();
		cardActors = new IntMap<>();
		notifiers = new IntMap<>();
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
	public boolean changeCardZone(int handle, int toZoneId) {
		
		CardActor card = validateCardActor(handle);
		
		if (card != null) {
			
			card.remove();
			ZoneActor toZone = zoneStrat.get(toZoneId);
			
			if (toZone != null) {
				return toZone.addCardActor(card);
			} else {
				debug("Failed to move card. Invalid zone id (%d).", toZoneId);
			}
		}
		
		return false;
	}
	
	@Override
	public void setCardTouchCallback(int cardHandle, Runnable run) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			TouchNotifier notifier = new TouchNotifier(run);
			notifiers.put(cardHandle, notifier);
			card.addListener(notifier);
		}
	}
	
	@Override
	public void removeCardTouchCallback(int cardHandle) {
		
		CardActor card = validateCardActor(cardHandle);
		if (card != null) {
			TouchNotifier notifier = notifiers.remove(cardHandle);
			if (notifier != null) {
				card.removeListener(notifier);
			} else debug("Failed to remove touch callback. No instance associated with the speicifed card handle.");
		}
	}
	
	private CardActor validateCardActor(int handle) {
		
		CardActor card = cardActors.get(handle);
		if (card == null) {
			debug("Failed to update card. Invalid handle (%d).", handle);
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
