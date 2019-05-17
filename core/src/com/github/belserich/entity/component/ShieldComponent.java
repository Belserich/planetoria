package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class ShieldComponent implements Component {
	
	public float pts;
	
	public ShieldComponent(float pts) {
		this.pts = pts;
	}
	
	public static class Broke implements Component {
	}
}
