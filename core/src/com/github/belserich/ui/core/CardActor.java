package com.github.belserich.ui.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.asset.CardTypes;
import com.github.belserich.util.UiHelper;

public class CardActor extends Label {
	
	public static final float CARD_HEIGHT_FACTOR = 1.421f;
	public static final float MIN_CARD_WIDTH = 75f * 1.5f;
	public static final float MIN_CARD_HEIGHT = CARD_HEIGHT_FACTOR * MIN_CARD_WIDTH;
	
	private CardTypes type;
	private String title, effect;
	private float lp, ap, sp;
	private boolean isCovered;
	
	public CardActor(String title, float lp, float ap, float sp, boolean isCovered) {
		this(CardTypes.DEFAULT, title, null, lp, ap, sp, isCovered);
	}
	
	public CardActor(CardTypes type, String title, String effect, float lp, float ap, float sp, boolean isCovered) {
		super("", new Label.LabelStyle(UiHelper.smallFont, Color.BLACK));
		super.setAlignment(Align.center);
		super.setWrap(true);
		
		this.type = type;
		this.title = title;
		this.effect = effect;
		this.lp = lp;
		this.ap = ap;
		this.sp = sp;
		this.isCovered = isCovered;
		
		update();
	}
	
	public void update() {
		
		if (isCovered) {
			super.setText("COVERED");
			return;
		}
		
		switch (type) {
			
			case DEFAULT:
				super.setText(title + "\nLP: " + lp + "\nAP: " + ap + "\nSP: " + sp);
				break;
			
			case STRATEGY:
				super.setText(title + "\n---\n" + effect);
		}
	}
	
	public CardActor(String title, String effect, boolean isCovered) {
		this(CardTypes.STRATEGY, title, effect, -1f, -1f, -1f, isCovered);
	}
	
	public CardActor() {
		this("???", -1f, -1f, -1f, false);
	}
	
	public CardTypes getType() {
		return type;
	}
	
	public void setType(CardTypes type) {
		this.type = type;
	}
	
	public String getEffect() {
		return effect;
	}
	
	public void setEffect(String effect) {
		this.effect = effect;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public float getLp() {
		return lp;
	}
	
	public void setLp(float lp) {
		this.lp = lp;
	}
	
	public float getAp() {
		return ap;
	}
	
	public void setAp(float ap) {
		this.ap = ap;
	}
	
	public float getSp() {
		return sp;
	}
	
	public void setSp(float sp) {
		this.sp = sp;
	}
	
	public boolean isCovered() {
		return isCovered;
	}
	
	public void setCovered(boolean covered) {
		isCovered = covered;
	}
}
