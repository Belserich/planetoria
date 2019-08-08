package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class BoardPos implements Component {
	
	public float x, y;
	
	public BoardPos(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
