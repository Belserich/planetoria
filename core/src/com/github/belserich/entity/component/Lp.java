package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Lp implements Component {
	
	public float pts;
	
	public Lp(float pts) {
		this.pts = pts;
	}
	
	public static class Changed implements Component {
		
		public float last, now;
		
		public Changed(float last, float now) {
			this.last = last;
			this.now = now;
		}
	}
}
