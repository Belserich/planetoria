package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class CardId implements Component {
	
	public int id;
	
	public CardId(int id) {
		this.id = id;
	}
	
	public static class Request implements Component {
	}
}
