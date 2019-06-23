package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;

public class CardId implements Component {
	
	public int val;
	
	public CardId(int val) {
		this.val = val;
	}
	
	public static class Request implements Component {
	}
}
