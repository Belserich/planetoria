package com.github.belserich.ui.core;

public class CardLocator {
	
	private final int zoneId, fieldId, cardId;
	
	public CardLocator(int zoneId, int fieldId, int cardId) {
		this.zoneId = zoneId;
		this.fieldId = fieldId;
		this.cardId = cardId;
	}
	
	public int zoneId() {
		return zoneId;
	}
	
	public int fieldId() {
		return fieldId;
	}
	
	public int cardId() {
		return cardId;
	}
}
