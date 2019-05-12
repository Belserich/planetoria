package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.asset.CardUi;
import com.github.belserich.asset.GameUi;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.event.select.Select;
import com.google.common.eventbus.Subscribe;

public class UiSystem extends EntityEvSystem<UiComponent> {
	
	private GameUi gameUi;
	
	public UiSystem(EventQueue eventBus, GameUi gameUi) {
		super(eventBus, true, UiComponent.class);
		this.gameUi = gameUi;
	}
	
	@Override
	protected void update() {
		super.update();
		gameUi.update(delta);
	}
	
	@Override
	public synchronized void justAdded(Entity entity) {
		
		comp = mapper.get(entity);
		
		String text = comp.displayName + "\nLP: " + comp.lpStr + "\nAP: " + comp.apStr + "\nSP: " + comp.spStr;
		CardUi cardUi = new CardUi(text);
		
		if (!gameUi.getZoneUi(comp.zone).tryAddCardUi(cardUi)) {
			GameClient.log(this, "Couldn't add card ui, no unoccupied fields remaining.");
		}
	}
	
	@Subscribe
	public void on(Select ev) {
		
		Entity entity = ev.entity();
		if (mapper.has(entity)) {
		}
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
	}
	
	public void resize(int width, int height) {
		// TODO
	}
}
