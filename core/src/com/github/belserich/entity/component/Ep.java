package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Ep implements Component {
	
	public int def;
	public int val;
	
	public Ep(int val) {
		this.def = val;
		this.val = val;
	}
	
	public static class Update implements Component {
	}
	
	public static class Updatable implements Component {
	}
}
