package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class OwnedByPlayer implements Component {
	
	public int id;
	
	public OwnedByPlayer(int id) {
		this.id = id;
	}
}
