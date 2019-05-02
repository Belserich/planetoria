package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
	
	public float pts;
	public int attCount;
	
	public AttackComponent(float pts, int attCount) {
		this.pts = pts;
		this.attCount = attCount;
	}
}
