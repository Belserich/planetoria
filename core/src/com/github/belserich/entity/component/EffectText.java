package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class EffectText implements Component {
	
	public String text;
	
	public EffectText(String text) {
		this.text = text;
	}
}
