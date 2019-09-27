package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.belserich.util.RelGlyphLayout;

public class Draw implements Component {
	
	public Sprite[] sprites;
	public RelGlyphLayout[] glyphs;
	
	public Draw(Sprite[] sprites) {
		this.sprites = sprites;
		this.glyphs = new RelGlyphLayout[0];
	}
	
	public Draw(Sprite[] sprites, RelGlyphLayout[] glyphs) {
		this.sprites = sprites;
		this.glyphs = glyphs;
	}
}
