package com.github.belserich.entity.builder;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.*;
import com.github.belserich.ui.CardUi;
import com.github.belserich.ui.GameUi;

public class CardBuilder {
	
	private final GameUi gameUi;
	
	private String label;
	private float lp, sp, ap;
	private int atcks;
	
	private CardUi ui;
	private UiZones zone;
	
	private boolean isAttacker, isAttackable;
	
	public CardBuilder(GameUi gameUi) {
		this.gameUi = gameUi;
	}
	
	public Entity build() {
		
		Entity entity = new Entity();
		
		entity.add(new Lp(lp));
		entity.add(new Sp(sp));
		entity.add(new Ap(ap, atcks));
		
		ui = new CardUi(label, String.valueOf(lp), String.valueOf(ap), String.valueOf(sp));
		entity.add(new Ui.Card(ui));
		
		entity.add(new ZoneParent(zone));
		gameUi.getZoneUi(zone).addCardUi(ui);
		
		if (isAttacker) {
			entity.add(new Attacker(ui));
		}
		
		if (isAttackable) {
			entity.add(new Attackable(ui));
		}
		
		return entity;
	}
	
	public CardBuilder type(Cards template) {
		
		switch (template) {
			
			case SPACESHIP_A:
				
				lp = 1;
				sp = 1;
				ap = 1;
				atcks = 2;
				label = "Raumschiff A";
				
				return this;
			
			case SPACESHIP_B:
				
				lp = 2;
				sp = 2;
				ap = 2;
				atcks = 2;
				label = "Raumschiff B";
				
				return this;
			
			default:
				
				GameClient.error(this, "* Entity creation. Tried to create unregistered entity: " + template + ".");
				return this;
		}
	}
	
	public CardBuilder zone(UiZones template) {
		
		zone = template;
		return this;
	}
	
	public CardBuilder attacker() {
		
		isAttacker = true;
		return this;
	}
	
	public CardBuilder attackable() {
		
		isAttackable = true;
		return this;
	}
	
	@Override
	public void finalize() {
		reset();
	}
	
	public CardBuilder reset() {
		
		label = null;
		lp = -1;
		sp = -1;
		ap = -1;
		atcks = -1;
		
		ui = null;
		zone = null;
		
		isAttacker = false;
		isAttackable = false;
		
		return this;
	}
}
