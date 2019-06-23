package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class OwnedByField implements Component {
	
	public int id;
	
	public OwnedByField(int id) {
		this.id = id;
	}
	
	public static class Request implements Component {
	}
}
