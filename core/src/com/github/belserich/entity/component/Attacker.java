package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Attacker implements Component {
	
	public int max;
	public int curr;
	
	public Attacker(int max) {
		this.max = max;
		this.curr = max;
	}
}
