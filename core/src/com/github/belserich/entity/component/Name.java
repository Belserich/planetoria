package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Name implements Component {
	
	public String name;
	
	public Name(String name) {
		this.name = name;
	}
}
