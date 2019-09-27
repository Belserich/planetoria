package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Bounds implements Component {
	
	public float x, y, width, height;
	
	public Bounds(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
