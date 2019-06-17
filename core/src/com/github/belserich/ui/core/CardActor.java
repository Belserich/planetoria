package com.github.belserich.ui.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.util.UiHelper;

public class CardActor extends Label {
	
	public static final float CARD_HEIGHT_FACTOR = 1.421f;
	public static final float MIN_CARD_WIDTH = 75f * 1.5f;
	public static final float MIN_CARD_HEIGHT = CARD_HEIGHT_FACTOR * MIN_CARD_WIDTH;
	
	private String title;
	private float lp, ap, sp;
	
	public CardActor(String title, float lp, float ap, float sp) {
		super("", new Label.LabelStyle(UiHelper.smallFont, Color.BLACK));
		super.setAlignment(Align.center);
		super.setWrap(true);
		
		this.title = title;
		this.lp = lp;
		this.ap = ap;
		this.sp = sp;
		
		update();
	}
	
	public void update() {
		super.setText(title + "\nLP: " + lp + "\nAP: " + ap + "\nSP: " + sp);
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
}
