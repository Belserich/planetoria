package com.github.belserich.ui.core;

import com.github.belserich.asset.CardTypes;

public class CardUpdater {
	
	private final CardActor card;
	
	public CardUpdater(CardActor card) {
		this.card = card;
	}
	
	public void setType(CardTypes type) {
		card.setType(type);
	}
	
	public void setEffect(String effect) {
		card.setEffect(effect);
	}
	
	public void setTitle(String title) {
		card.setTitle(title);
	}
	
	public void setLp(float lp) {
		card.setLp(lp);
	}
	
	public void setAp(float ap) {
		card.setAp(ap);
	}
	
	public void setSp(float sp) {
		card.setSp(sp);
	}
	
	public void setCovered(boolean covered) {
		card.setCovered(covered);
	}
	
	public void update() {
		card.update();
	}
}
