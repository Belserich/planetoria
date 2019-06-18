package com.github.belserich.ui.core;

public interface UiService {
	
	int addCard(int zoneId, int fieldId, String name, float lp, float ap, float sp);
	
	int addCard(int zoneId, String name, float lp, float ap, float sp);
	
	void removeCard(int handle);
	
	void updateCardName(int handle, String name);
	
	void updateCardLp(int handle, float lp);
	
	void updateCardAp(int handle, float ap);
	
	void updateCardSp(int handle, float sp);
	
	boolean changeCardField(int handle, int toZone, int toField);
	
	boolean isFieldUnoccupied(int zoneId, int fieldId);
	
	void setCardTouchCallback(int cardHandle, Runnable run);
	
	void removeCardTouchCallback(int cardHandle);
	
	void setFieldTouchCallback(int zoneId, int fieldId, Runnable run);
	
	void removeFieldTouchCallback(int zoneid, int fieldId);
	
	void update(float delta);
	
	void resize(int width, int height);
}
