package com.github.belserich.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

public class UiHelper {
	
	public static final BitmapFont smallFont;
	public static final BitmapFont largeFont;
	
	static {
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gugi.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		
		param.size = 18;
		smallFont = fontGen.generateFont(param);
		
		param.size = 30;
		largeFont = fontGen.generateFont(param);
		
		fontGen.dispose();
	}
	
	public static HorizontalGroup horizontalGroup(Actor... actors) {
		HorizontalGroup group = new HorizontalGroup();
		for (Actor actor : actors) {
			group.addActor(actor);
		}
		return group;
	}
	
	public static VerticalGroup verticalGroup(Actor... actors) {
		VerticalGroup group = new VerticalGroup();
		for (Actor actor : actors) {
			group.addActor(actor);
		}
		return group;
	}
}
