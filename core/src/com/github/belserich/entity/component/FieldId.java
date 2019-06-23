package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class FieldId implements Component {
	
	public int id;
	
	public FieldId(int id) {
		this.id = id;
	}
	
	public static class Request implements Component {
	}
}
