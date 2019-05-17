package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class LifeComponent implements Component {
	
	public float pts;
	
	public LifeComponent(float pts) {
		this.pts = pts;
	}
	
	public static class Changed implements Component {
	}
}
