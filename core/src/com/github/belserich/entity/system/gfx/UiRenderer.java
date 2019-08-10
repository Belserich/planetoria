package com.github.belserich.entity.system.gfx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.github.belserich.entity.component.Name;
import com.github.belserich.entity.component.Rect;
import com.github.belserich.entity.component.Ui;
import com.github.belserich.entity.component.Visible;

public class UiRenderer extends BaseRenderer {
	
	private final Sprite uiSprite;
	
	public UiRenderer() {
		uiSprite = new Sprite(new Texture(Gdx.files.internal("ui_default.png")));
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
		
		Vector3 textVec = new Vector3(rc.x, rc.y, 0);
		boardCam.project(textVec);
		
		uiSprite.setPosition(rc.x, rc.y);
		uiSprite.setSize(rc.width, rc.height);
		
		batch.setProjectionMatrix(boardCam.combined);
		uiSprite.draw(batch);
		
		batch.setProjectionMatrix(textCam.combined);
		font.draw(batch, nc.name, textVec.x + 10, textVec.y + 25);
	}
}
