package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.github.belserich.data.selection.Selection;
import com.github.belserich.entity.system.SelectionSystem;

public class SelectionComponent implements Component {
	
	public Actor observable;
	public SelectionSystem.SelectionNotifier notifier;
	
	public Selection<Entity> selection;
	
	public SelectionComponent(Actor observable, Selection<Entity> selection) {
		this.selection = selection;
		this.observable = observable;
	}
}
