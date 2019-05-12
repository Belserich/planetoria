package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.asset.GameUi;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.util.UiHelper;

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
		
		Label label = new Label(text, new Label.LabelStyle(UiHelper.smallFont, Color.BLACK));
		label.setAlignment(Align.center);
		label.setWrap(true);
		
		Container<Label> fieldUi = gameUi.getZoneUi(comp.zone).nextFreeFieldUi();
		if (fieldUi != null) {
			fieldUi.setActor(label);
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
