package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class PlayerId implements Component {
	
	public int val;
	
	public PlayerId(int val) {
		this.val = val;
	}
}
