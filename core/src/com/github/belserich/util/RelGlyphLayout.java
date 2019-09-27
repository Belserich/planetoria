package com.github.belserich.util;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class RelGlyphLayout {
	
	private final GlyphLayout layout;
	private final float unitsX, unitsY;
	
	public RelGlyphLayout(GlyphLayout layout, float unitsX, float unitsY) {
		this.layout = layout;
		this.unitsX = unitsX;
		this.unitsY = unitsY;
	}
	
	public GlyphLayout layout() {
		return layout;
	}
	
	public float unitsX() {
		return unitsX;
	}
	
	public float unitsY() {
		return unitsY;
	}
}
