package com.github.belserich.entity.system.gfx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.belserich.entity.component.BoardPos;
import com.github.belserich.entity.component.FieldId;

public class FieldRenderer extends BaseRenderer {
	
	private final Sprite fieldSprite;
	
	public FieldRenderer() {
		super();
		
		fieldSprite = new Sprite(new Texture(Gdx.files.internal("ui_field.png")));
		fieldSprite.setSize(1, 1);
	}
	
	@Override
	protected Family actors() {
		return Family.all(
				FieldId.class,
				BoardPos.class
		).get();
	}
	
	@Override
	public void entityUpdate(Entity actor) {
		
		BoardPos bpc = actor.getComponent(BoardPos.class);
		
		batch.setProjectionMatrix(boardCam.combined);
		fieldSprite.setPosition(bpc.x, bpc.y);
		fieldSprite.draw(batch);
	}
}
