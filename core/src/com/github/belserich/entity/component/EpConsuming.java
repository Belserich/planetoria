package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class EpConsuming implements Component {
	
	public int val;
	
	public EpConsuming(int val) {
		this.val = val;
	}
}
