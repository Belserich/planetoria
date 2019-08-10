package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Scene implements Component {
	
	public int id;
	
	public Scene(int id) {
		this.id = id;
	}
}
