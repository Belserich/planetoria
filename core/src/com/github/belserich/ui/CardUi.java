package com.github.belserich.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.util.UiHelper;

public class CardUi extends Label {
	
	public static final float CARD_HEIGHT_FACTOR = 1.421f;
	public static final float MIN_CARD_WIDTH = 75f * 1.5f;
	public static final float MIN_CARD_HEIGHT = CARD_HEIGHT_FACTOR * MIN_CARD_WIDTH;
	
	public CardUi(String text) {
		super(text, new Label.LabelStyle(UiHelper.smallFont, Color.BLACK));
		super.setAlignment(Align.center);
		super.setWrap(true);
	}
}
