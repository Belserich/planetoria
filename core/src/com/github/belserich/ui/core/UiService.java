package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public interface UiService {
	
	int addCard(int fieldId);
	
	void removeCard(int cardId);
	
	CardUpdater getCardUpdater(int cardId);
	
	void setCardOnField(int cardId, int toFieldId);
	
	void setCardClickCallback(int cardHandle, ClickListener clb);
	
	void removeCardClickCallback(int cardHandle);
	
	// ---
	
	int addField(int zoneId);
	
	void removeField(int fieldId);
	
	void setFieldClickCallback(int fieldId, ClickListener clb);
	
	void removeFieldClickCallback(int fieldId);
	
	// ---
	
	int addZone(int zoneId);
	
	void removeZone(int zoneId);
	
	// ---
	
	void setTurnCallback(Runnable clb);
	
	void removeTurnCallback();
	
	// ---
	
	void update(float delta);
	
	void resize(int width, int height);
}
