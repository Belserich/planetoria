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

public class UiRenderer extends EntityActor {
	
	private final Texture uiTex;
	
	public UiRenderer() {
		uiTex = new Texture(Gdx.files.internal("ui_default.png"));
	}
	
	@Override
	protected Family actors() {
		return Family.all(
				Ui.class,
				Name.class,
				Rect.class,
				Visible.class
		).get();
	}
	
	@Override
	public void entityUpdate(Entity actor) {
		
		Rect rc = actor.getComponent(Rect.class);
		Name nc = actor.getComponent(Name.class);
		
		Sprite uiSprite = new Sprite(uiTex);
		uiSprite.setSize(rc.width, rc.height);
		uiSprite.setCenter(rc.x, rc.y);
		
		RelGlyphLayout textLayout = Services.getRenderer().createRelGlyphLayout(
				nc.name, 0, 0,
				Color.WHITE,
				rc.width,
				Align.center,
				false
		);
		
		actor.add(new Draw(
				new Sprite[]{},
				new RelGlyphLayout[]{textLayout}
		));
	}
}
