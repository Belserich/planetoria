package com.github.belserich.ui;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;

public class FieldUi extends Container<CardUi> {
	
	public FieldUi() {
		super();
		super.minSize(CardUi.MIN_CARD_WIDTH, CardUi.MIN_CARD_HEIGHT);
		super.setTouchable(Touchable.enabled);
		super.setDebug(true);
	}
}
