package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.belserich.entity.system.AttackerSystem;

public class AttackerComponent implements Component {
	
	public Actor uiObs;
	public AttackerSystem.TouchNotifier notifier;
	
	public AttackerComponent(Actor uiObs) {
		this.uiObs = uiObs;
	}
	
	public static class Select implements Component {
	}
}
