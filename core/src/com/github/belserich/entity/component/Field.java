package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class Field implements Component {
	
	public int id;
	
	public Field(int id) {
		this.id = id;
	}
}
