package com.github.belserich.entity.system.gfx;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.github.belserich.Services;
import com.github.belserich.entity.core.EntityActor;

public abstract class BaseRenderer extends EntityActor {
	
	protected final Batch batch;
	
	protected final Camera boardCam;
	protected final Camera textCam;
	
	protected final BitmapFont font;
	
	public BaseRenderer() {
		
		batch = Services.getBatch();
		
		boardCam = Services.getBoardCamera();
		textCam = Services.getTextCamera();
		
		font = Services.getFont();
	}
	
	@Override
	public void updateEntities() {
		
		boardCam.update();
		textCam.update();
		
		batch.begin();
		super.updateEntities();
		batch.end();
	}
}
