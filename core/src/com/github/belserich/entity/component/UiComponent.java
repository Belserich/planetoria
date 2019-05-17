package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.ui.CardUi;

public class UiComponent implements Component {
	
	public static class Card implements Component {
		
		public CardUi ui;
		
		public Card(CardUi ui) {
			this.ui = ui;
		}
	}
}
