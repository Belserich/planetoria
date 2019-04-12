package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
	
	public float pts;
	
	public AttackComponent(float pts) {
		this.pts = pts;
	}
}
