package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.CardTypes;

public class CardType implements Component {
	
	public CardTypes type;
	
	public CardType(CardTypes type) {
		this.type = type;
	}
}
