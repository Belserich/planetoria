package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Effect implements Component {
	
	public String text;
	
	public Effect(String text) {
		this.text = text;
	}
}
