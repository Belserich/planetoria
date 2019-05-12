package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.asset.UiZones;
import com.github.belserich.asset.ZoneUi;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.util.UiHelper;

import java.util.HashMap;
import java.util.Map;

import static com.github.belserich.asset.UiZones.*;

public class UiSystem extends EntityEvSystem<UiComponent> {
	
	private Stage stage;
	private VerticalGroup rootGroup;
	
	private Map<UiZones, ZoneUi> zones;
	
	public UiSystem(EventQueue eventBus) {
		super(eventBus, true, UiComponent.class);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		createBoardUi();
	}
	
	private void createBoardUi() {
		
		createZoneUi();
		
		rootGroup = new VerticalGroup();
		rootGroup.setFillParent(true);
		rootGroup.pad(70);
		rootGroup.space(10);
		
		rootGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P1_YARD), zones.get(P1_PLANET), zones.get(P1_REPAIR), zones.get(P1_MOTHER)));
		rootGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P1_BATTLE)));
		rootGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P0_BATTLE)));
		rootGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P0_MOTHER), zones.get(P0_REPAIR), zones.get(P0_PLANET), zones.get(P0_YARD)));
		
		stage.addActor(rootGroup);
	}
	
	private void createZoneUi() {
		
		zones = new HashMap<UiZones, ZoneUi>();
		
		zones.put(UiZones.P0_BATTLE, new ZoneUi(7));
		zones.put(UiZones.P1_BATTLE, new ZoneUi(7));
		zones.put(UiZones.P0_REPAIR, new ZoneUi(5));
		zones.put(UiZones.P1_REPAIR, new ZoneUi(5));
		
		zones.put(UiZones.P0_YARD, new ZoneUi(1));
		zones.put(UiZones.P1_YARD, new ZoneUi(1));
		zones.put(UiZones.P0_PLANET, new ZoneUi(1));
		zones.put(UiZones.P1_PLANET, new ZoneUi(1));
		zones.put(UiZones.P0_MOTHER, new ZoneUi(1));
		zones.put(UiZones.P1_MOTHER, new ZoneUi(1));
		
		zones.put(UiZones.P0_DECK, new ZoneUi(8));
		zones.put(UiZones.P1_DECK, new ZoneUi(8));
	}
	
	@Override
	public synchronized void justAdded(Entity entity) {
		
		comp = mapper.get(entity);
		
		String text = comp.displayName + "\nLP: " + comp.lpStr + "\nAP: " + comp.apStr + "\nSP: " + comp.spStr;
		
		Label label = new Label(text, new Label.LabelStyle(UiHelper.smallFont, Color.BLACK));
		label.setAlignment(Align.center);
		label.setWrap(true);
		
		Container<Label> fieldUi = zones.get(comp.zone).nextFreeFieldUi();
		if (fieldUi != null) {
			fieldUi.setActor(label);
		}
	}
	
	@Override
	protected void update() {
		super.update();
		
		Gdx.gl20.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public synchronized void dispose() {
		super.dispose();
	}
	
	public void resize(int width, int height) {
		// TODO
	}
}
