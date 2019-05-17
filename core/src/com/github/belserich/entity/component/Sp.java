package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Sp implements Component {
	
	public float pts;
	
	public Sp(float pts) {
		this.pts = pts;
	}
	
	public static class Broke implements Component {
	}
}
