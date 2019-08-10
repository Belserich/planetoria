package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Switch implements Component {
	
	public int toId;
	
	public Switch(int toId) {
		this.toId = toId;
	}
}
