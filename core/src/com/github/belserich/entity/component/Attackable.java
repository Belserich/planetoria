package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Attackable implements Component {
	
	public static class Attacked implements Component {
		
		public float pts;
		
		public Attacked(float pts) {
			this.pts = pts;
		}
	}
}
