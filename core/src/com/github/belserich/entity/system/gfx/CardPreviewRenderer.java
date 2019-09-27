package com.github.belserich.entity.system.gfx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.util.RelGlyphLayout;

import java.util.ArrayList;
import java.util.List;

public class CardPreviewRenderer extends EntityActor {
	
	private final List<Sprite> sprites;
	private final List<RelGlyphLayout> glyphLayouts;
	private Texture mainTex;
	private Texture nameTex;
	private Texture bubbleTex;
	
	public CardPreviewRenderer() {
		
		mainTex = new Texture(Gdx.files.internal("ui_card_main.png"));
		nameTex = new Texture(Gdx.files.internal("ui_card_bg_title.png"));
		bubbleTex = new Texture(Gdx.files.internal("ui_card_bubble.png"));
		
		sprites = new ArrayList<>();
		glyphLayouts = new ArrayList<>();
	}
	
	@Override
	protected Family actors() {
		return Family.all(
				CardId.class,
				Bounds.class,
				Visible.class,
				Name.class,
				Lp.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		final Bounds bc = actor.getComponent(Bounds.class);
		final Name nc = actor.getComponent(Name.class);
		
		final Lp lc = actor.getComponent(Lp.class);
		final Ap ac = actor.getComponent(Ap.class);
		final Sp sc = actor.getComponent(Sp.class);
		
		final float nameX = bc.x;
		final float nameY = bc.y + bc.height / 2f - 0.01f;
		
		final float nameWidth = bc.width - (bc.width / 8f);
		final float nameHeight = 0.07f;
		
		final float bubbleWidth = bc.width / 2.7f;
		final float bubbleHeight = nameHeight * 1.3f;
		
		final float bubbleShift = bc.width / 4f;
		final float bubbleX1 = bc.x - bubbleShift;
		final float bubbleX2 = bc.x;
		final float bubbleX3 = bc.x + bubbleShift;
		
		final float bubbleY1 = bc.y - bc.height / 4f;
		final float bubbleY2 = bc.y - bc.height / 2f;
		final float bubbleY3 = bc.y - bc.height / 2f;
		
		final Color greenHex = Color.valueOf("77e286");
		final Color blueHex = Color.valueOf("5fdcfc");
		final Color redHex = Color.valueOf("fd5b59");
		
		sprites.clear();
		glyphLayouts.clear();
		
		Sprite mainSprite = new Sprite(mainTex);
		mainSprite.setSize(bc.width, bc.height);
		mainSprite.setCenter(bc.x, bc.y);
		sprites.add(mainSprite);
		
		Sprite nameSprite = new Sprite(nameTex);
		nameSprite.setSize(nameWidth, nameHeight);
		nameSprite.setCenter(nameX, nameY);
		sprites.add(nameSprite);
		
		glyphLayouts.add(Services.getRenderer().createRelGlyphLayout(
				nc.name, nameX, -nameY - (nameHeight / 4f),
				Color.BLACK,
				nameWidth,
				Align.center,
				false
		));
		
		if (ac == null && sc == null) {
			createBubble(lc.pts, greenHex, bubbleX2, bubbleY2, bubbleWidth, bubbleHeight);
		} else if (sc == null) {
			
			createBubble(ac.pts, redHex, bubbleX1, bubbleY2, bubbleWidth, bubbleHeight);
			createBubble(lc.pts, greenHex, bubbleX3, bubbleY2, bubbleWidth, bubbleHeight);
		} else if (ac == null) {
			
			createBubble(sc.pts, blueHex, bubbleX1, bubbleY2, bubbleWidth, bubbleHeight);
			createBubble(lc.pts, greenHex, bubbleX3, bubbleY2, bubbleWidth, bubbleHeight);
		} else {
			
			createBubble(ac.pts, redHex, bubbleX2, bubbleY1, bubbleWidth, bubbleHeight);
			createBubble(sc.pts, blueHex, bubbleX1, bubbleY3, bubbleWidth, bubbleHeight);
			createBubble(lc.pts, greenHex, bubbleX3, bubbleY3, bubbleWidth, bubbleHeight);
		}
		
		actor.add(new Draw(
				sprites.toArray(new Sprite[0]),
				glyphLayouts.toArray(new RelGlyphLayout[0])
		));
	}
	
	private void createBubble(float val, Color col, float x, float y, float bubbleWidth, float bubbleHeight) {
		
		Sprite bubbleSprite = new Sprite(bubbleTex);
		bubbleSprite.setColor(col);
		bubbleSprite.setSize(bubbleWidth, bubbleHeight);
		bubbleSprite.setCenter(x, y);
		sprites.add(bubbleSprite);
		
		glyphLayouts.add(Services.getRenderer().createRelGlyphLayout(
				String.valueOf((int) val), x, -y - (bubbleHeight / 4f),
				Color.BLACK,
				bubbleWidth,
				Align.center,
				false
		));
	}
	
	@Override
	public void finalize() {
		
		mainTex.dispose();
		nameTex.dispose();
	}
}
