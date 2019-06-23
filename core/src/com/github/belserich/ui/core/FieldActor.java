package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

public class FieldActor extends Container<Stack> {
	
	private final Stack stack;
	
	public FieldActor() {
		super();
		super.minSize(CardActor.MIN_CARD_WIDTH, CardActor.MIN_CARD_HEIGHT);
		super.setTouchable(Touchable.enabled);
		super.setDebug(true);
		
		stack = new Stack();
		super.setActor(stack);
	}
	
	public void addCard(CardActor card) {
		stack.addActor(card);
	}
	
	public void removeCard(CardActor card) {
		stack.removeActor(card);
	}
	
	public boolean hasCard() {
		return stack.hasChildren();
	}
}
