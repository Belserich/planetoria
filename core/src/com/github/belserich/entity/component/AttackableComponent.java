package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.belserich.entity.system.AttackableSystem;

public class AttackableComponent implements Component {
	
	public Actor uiObs;
	public AttackableSystem.TouchNotifier notifier;
	
	public AttackableComponent(Actor uiObs) {
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
