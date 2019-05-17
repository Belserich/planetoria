package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Attackable implements Component {
	
	public Actor uiObs;
	
	public Attackable(Actor uiObs) {
		this.uiObs = uiObs;
	}
	
	public static class Attacked implements Component {
		
		public float pts;
		
		public Attacked(float pts) {
			this.pts = pts;
		}
	}
	
	public static class Touched implements Component {
	}
}
