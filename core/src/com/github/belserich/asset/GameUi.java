package com.github.belserich.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.github.belserich.util.UiHelper;

import java.util.HashMap;
import java.util.Map;

import static com.github.belserich.asset.UiZones.*;

public class GameUi {
	
	private Stage stage;
	private VerticalGroup rootGroup;
	
	private Map<UiZones, ZoneUi> zones;
	
	public GameUi() {
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
	
	public void update(float delta) {
		Gdx.gl20.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
	public ZoneUi getZoneUi(UiZones zone) {
		return zones.get(zone);
	}
}
