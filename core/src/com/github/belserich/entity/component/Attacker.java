package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Attacker implements Component {
	
	public int attCount;
	
	public Attacker(int attCount) {
		this.attCount = attCount;
	}
}
