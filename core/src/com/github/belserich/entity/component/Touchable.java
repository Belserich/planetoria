package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Touchable implements Component {
	
	public Actor obs;
	
	public Touchable(Actor obs) {
		this.obs = obs;
	}
	
	public static class Touched implements Component {
	}
}
