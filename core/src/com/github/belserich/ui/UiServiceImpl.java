package com.github.belserich.ui;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.*;
import com.github.belserich.util.UiHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.github.belserich.asset.Zones.*;

public class UiServiceImpl implements UiService {
	
	private Stage stage;
	private VerticalGroup rootGroup;
	
	private VerticalGroup mainGroup;
	private VerticalGroup deckGroup;
	
	private Label deckToggle;
	private boolean toggled;
	
	private Map<Zones, ZoneUi> zones;
	private Map<Entity, Actor> actors;
	
	private Set<Entity> touchedEntities;
	
	public UiServiceImpl() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		createBoardUi();
		actors = new HashMap<>();
		
		touchedEntities = new HashSet<>();
	}
	
	private void createBoardUi() {
		
		createZoneUi();
		
		rootGroup = new VerticalGroup();
		rootGroup.setFillParent(true);
		stage.addActor(rootGroup);
		
		{
			mainGroup = new VerticalGroup();
			mainGroup.pad(70);
			mainGroup.space(10);
			
			mainGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P1_YARD), zones.get(P1_PLANET), zones.get(P1_REPAIR), zones.get(P1_MOTHER)));
			mainGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P1_BATTLE)));
			mainGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P0_BATTLE)));
			mainGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P0_MOTHER), zones.get(P0_REPAIR), zones.get(P0_PLANET), zones.get(P0_YARD)));
			
			rootGroup.addActor(mainGroup);
		}
		
		{
			deckGroup = new VerticalGroup();
			deckGroup.pad(70);
			deckGroup.space(100);
			
			deckGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P0_DECK)));
			deckGroup.addActor(UiHelper.horizontalGroup(10, zones.get(P1_DECK)));
		}
		
		// ---
		
		deckToggle = new Label("Hand", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
		deckToggle.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				toggleDeck();
			}
		});
		rootGroup.addActor(deckToggle);
	}
	
	private void createZoneUi() {
		
		zones = new HashMap<>();
		
		zones.put(Zones.P0_BATTLE, new ZoneUi(7));
		zones.put(Zones.P1_BATTLE, new ZoneUi(7));
		zones.put(Zones.P0_REPAIR, new ZoneUi(5));
		zones.put(Zones.P1_REPAIR, new ZoneUi(5));
		
		zones.put(Zones.P0_YARD, new ZoneUi(1));
		zones.put(Zones.P1_YARD, new ZoneUi(1));
		zones.put(Zones.P0_PLANET, new ZoneUi(1));
		zones.put(Zones.P1_PLANET, new ZoneUi(1));
		zones.put(Zones.P0_MOTHER, new ZoneUi(1));
		zones.put(Zones.P1_MOTHER, new ZoneUi(1));
		
		zones.put(Zones.P0_DECK, new ZoneUi(8));
		zones.put(Zones.P1_DECK, new ZoneUi(8));
	}
	
	private void toggleDeck() {
		
		toggled = !toggled;
		if (toggled) {
			rootGroup.removeActor(mainGroup);
			rootGroup.addActorBefore(deckToggle, deckGroup);
		} else {
			rootGroup.removeActor(deckGroup);
			rootGroup.addActorBefore(deckToggle, mainGroup);
		}
	}
	
	@Override
	public void addCard(Entity entity) {
		
		String name = entity.getComponent(Name.class).name;
		float lp = entity.getComponent(Lp.class).pts;
		float ap = entity.getComponent(Ap.class).pts;
		float sp = entity.getComponent(Sp.class).pts;
		
		Zones zone = entity.getComponent(Zone.class).zone;
		CardUi card = new CardUi(name, String.valueOf(lp), String.valueOf(ap), String.valueOf(sp));
		
		if (ComponentMapper.getFor(Field.class).has(entity)) {
			
			int fieldId = entity.getComponent(Field.class).id;
			zones.get(zone).addCardActor(fieldId, card);
		} else zones.get(zone).addCardActor(card);
		
		actors.put(entity, card);
	}
	
	@Override
	public void removeCard(Entity entity) {
		
		Zones zone = entity.getComponent(Zone.class).zone;
		zones.get(zone).removeCardActor(actors.get(entity));
		actors.remove(entity);
	}
	
	@Override
	public void changeZone(Entity card) {
	
	}
	
	@Override
	public void updateCard(Entity entity) {
		System.out.println("UPDATE");
	}
	
	@Override
	public void addTouchNotifier(Entity entity) {
		Actor obs = actors.get(entity);
		obs.addListener(new TouchNotifer(entity));
	}
	
	@Override
	public void removeTouchNotifier(Entity entity) {
	
	}
	
	@Override
	public Entity[] touchedEntities() {
		Entity[] ret = touchedEntities.toArray(new Entity[0]);
		touchedEntities.clear();
		return ret;
	}
	
	public void update(float delta) {
		Gdx.gl20.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
	private void touched(Entity entity) {
		touchedEntities.add(entity);
	}
	
	public int zoneCount() {
		return zones.size();
	}
	
	public int fieldCount(Zones zone) {
		return zones.get(zone).fieldCount();
	}
	
	private class TouchNotifer extends ClickListener {
		
		private Entity entity;
		
		public TouchNotifer(Entity entity) {
			this.entity = entity;
		}
		
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			touched(entity);
		}
	}
}
