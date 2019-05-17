package com.github.belserich.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.util.UiHelper;

public class CardUi extends Label {
	
	public static final float CARD_HEIGHT_FACTOR = 1.421f;
	public static final float MIN_CARD_WIDTH = 75f * 1.5f;
	public static final float MIN_CARD_HEIGHT = CARD_HEIGHT_FACTOR * MIN_CARD_WIDTH;
	
	private String title, lp, ap, sp;
	
	public CardUi(String title, String lp, String ap, String sp) {
		super("", new Label.LabelStyle(UiHelper.smallFont, Color.BLACK));
		super.setAlignment(Align.center);
		super.setWrap(true);
		
		this.title = title;
		this.lp = lp;
		this.ap = ap;
		this.sp = sp;
		
		updateText();
	}
	
	public void updateText() {
		super.setText(title + "\nLP: " + lp + "\nAP: " + ap + "\nSP: " + sp);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		updateText();
	}
	
	public String getLp() {
		return lp;
	}
	
	public void setLp(String lp) {
		this.lp = lp;
		updateText();
	}
	
	public String getAp() {
		return ap;
	}
	
	public void setAp(String ap) {
		this.ap = ap;
		updateText();
	}
	
	public String getSp() {
		return sp;
	}
	
	public void setSp(String sp) {
		this.sp = sp;
		updateText();
	}
}
