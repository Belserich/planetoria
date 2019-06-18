package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public interface UiService {
	
	int addCard(int zoneId, int fieldId, String name, float lp, float ap, float sp, boolean isCovered);
	
	int addCard(int zoneId, int fieldId, String name, String effect, boolean isCovered);
	
	int addCard(int zoneId, String name, float lp, float ap, float sp, boolean isCovered);
	
	int addCard(int zoneId, String name, String effect, boolean isCovered);
	
	void removeCard(int handle);
	
	void updateCardName(int handle, String name);
	
	void updateCardLp(int handle, float lp);
	
	void updateCardAp(int handle, float ap);
	
	void updateCardSp(int handle, float sp);
	
	void updateCardCovered(int handle, boolean isCovered);
	
	boolean changeCardField(int handle, int toZone, int toField);
	
	boolean isFieldUnoccupied(int zoneId, int fieldId);
	
	void setTurnCallback(Runnable clb);
	
	void removeTurnCallback();
	
	void setCardClickCallback(int cardHandle, ClickListener clb);
	
	void removeCardClickCallback(int cardHandle);
	
	void setFieldClickCallback(int zoneId, int fieldId, ClickListener clb);
	
	void removeFieldClickCallback(int zoneid, int fieldId);
	
	void update(float delta);
	
	void resize(int width, int height);
}
