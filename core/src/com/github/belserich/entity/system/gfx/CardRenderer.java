package com.github.belserich.entity.system.gfx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.github.belserich.entity.component.BoardPos;
import com.github.belserich.entity.component.CardId;

public class CardRenderer extends BaseRenderer {
	
	private Sprite cardSprite;
	
	public CardRenderer() {
		super();
		
		cardSprite = new Sprite(new Texture(Gdx.files.internal("ui_card.png")));
		cardSprite.setSize(1, 1);
	}
	
	@Override
	protected Family actors() {
		return Family.all(
				CardId.class,
				BoardPos.class
		).get();
	}
	
	@Override
	public void entityUpdate(Entity actor) {
		
		BoardPos bpc = actor.getComponent(BoardPos.class);
		Vector3 textVec = new Vector3(bpc.x, bpc.y, 0);
		boardCam.project(textVec);
		
		batch.setProjectionMatrix(boardCam.combined);
		cardSprite.setPosition(bpc.x, bpc.y);
		cardSprite.draw(batch);
		
		batch.setProjectionMatrix(textCam.combined);
		font.draw(batch, "Test", textVec.x + 10, textVec.y + 30);
	}
}
