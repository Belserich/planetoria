package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class PlayerOwned implements Component {
	
	public int id;
	
	public PlayerOwned(int id) {
		this.id = id;
	}
}
