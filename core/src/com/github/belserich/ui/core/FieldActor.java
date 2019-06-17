package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

public class FieldActor extends Container<CardActor> {
	
	public FieldActor() {
		super();
		super.minSize(CardActor.MIN_CARD_WIDTH, CardActor.MIN_CARD_HEIGHT);
		super.setTouchable(Touchable.enabled);
		super.setDebug(true);
	}
	
	public boolean setCardIfEmpty(CardActor actor) {
		
		if (hasCard()) {
			return false;
		}
		setCard(actor);
		return true;
	}
	
	public boolean hasCard() {
		return this.hasChildren();
	}
	
	public CardActor getCard() {
		return super.getActor();
	}
	
	public void setCard(CardActor actor) {
		this.setActor(actor);
	}
}
