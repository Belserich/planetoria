package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Rect implements Component {
	
	public float x, y;
	public float width, height;
	
	public Rect(float x, float y) {
		this(x, y, 1, 1);
	}
	
	public Rect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
