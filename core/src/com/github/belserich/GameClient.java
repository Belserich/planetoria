package com.github.belserich;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

public class GameClient extends ApplicationAdapter {
	
	@Override
	public void create () {
	
	}

	@Override
	public void render () {
	}
	
	@Override
	public void dispose () {
	}
	
	public static void log(Object obj, String message) {
		Gdx.app.log(obj.getClass().getSimpleName(), message);
	}
}
