package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.system.*;
import com.github.belserich.ui.CardUi;
import com.github.belserich.ui.GameUi;

public class GameClient extends ApplicationAdapter {
	
	private Engine engine;
	private GameUi gameUi;
	
	@Override
	public void create () {
		
		engine = new Engine();
		gameUi = new GameUi();
		
		createSystems();
		createEntities();
	}
	
	public static void error(Object obj, String message) {
		log(obj, message);
	}
	
	private void createSystems() {
		
		engine.addSystem(new AttackerSystem());
		engine.addSystem(new AttackableSystem());
		engine.addSystem(new ZoneParentSystem());
		
		engine.addSystem(new LifeSystem());
		engine.addSystem(new AttackSystem());
		engine.addSystem(new ShieldSystem());
		
		engine.addSystem(new UiSystem(gameUi));
	}
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		Entity p0b0 = new Entity();
		Entity p0b1 = new Entity();
		Entity p0b2 = new Entity();
		Entity p0b3 = new Entity();
		
		engine.addEntity(createShipB(p0b0, UiZones.P0_BATTLE, false));
		engine.addEntity(createShipB(p0b1, UiZones.P0_BATTLE, false));
		engine.addEntity(createShipA(p0b2, UiZones.P0_BATTLE, false));
		engine.addEntity(createShipA(p0b3, UiZones.P0_BATTLE, false));
		
		Entity p1b0 = new Entity();
		Entity p1b1 = new Entity();
		Entity p1b2 = new Entity();
		Entity p1b3 = new Entity();
		
		engine.addEntity(createShipA(p1b0, UiZones.P1_BATTLE, true));
		engine.addEntity(createShipA(p1b1, UiZones.P1_BATTLE, true));
		engine.addEntity(createShipA(p1b2, UiZones.P1_BATTLE, true));
		engine.addEntity(createShipB(p1b3, UiZones.P1_BATTLE, true));
		
		Entity de0 = new Entity();
		Entity de1 = new Entity();
		Entity de2 = new Entity();
		Entity de3 = new Entity();
		
		createDeckShipA(de0, UiZones.P0_DECK);
		createDeckShipA(de1, UiZones.P0_DECK);
		createDeckShipA(de2, UiZones.P0_DECK);
		createDeckShipA(de3, UiZones.P0_DECK);
		
		engine.addEntity(de0);
		engine.addEntity(de1);
		engine.addEntity(de2);
		engine.addEntity(de3);
	}
	
	private Entity createShipB(Entity entity, UiZones zone, boolean attackable) {
		
		CardUi ui = createDeckShipB(entity, zone);
		if (attackable) {
			entity.add(new Attackable(ui));
		} else entity.add(new Attacker(ui));
		
		return entity;
	}
	
	private Entity createShipA(Entity entity, UiZones zone, boolean attackable) {
		
		CardUi ui = createDeckShipA(entity, zone);
		if (attackable) {
			entity.add(new Attackable(ui));
		} else entity.add(new Attacker(ui));
		
		return entity;
	}
	
	private CardUi createDeckShipA(Entity entity, UiZones zone) {
		
		entity.add(new Lp(1));
		entity.add(new Sp(1));
		entity.add(new Ap(1, 2));
		entity.add(new ZoneParent(UiZones.P0_BATTLE));
		
		CardUi ui = new CardUi("Raumschiff A", "1.0", "1.0", "1.0");
		gameUi.getZoneUi(zone).addCardUi(ui);
		entity.add(new Ui.Card(ui));
		
		return ui;
	}
	
	private CardUi createDeckShipB(Entity entity, UiZones zone) {
		
		entity.add(new Lp(2));
		entity.add(new Sp(2));
		entity.add(new Ap(2, 2));
		entity.add(new ZoneParent(UiZones.P0_BATTLE));
		
		CardUi ui = new CardUi("Raumschiff B", "2.0", "2.0", "2.0");
		gameUi.getZoneUi(zone).addCardUi(ui);
		entity.add(new Ui.Card(ui));
		
		return ui;
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO gameUi.resize(width, height);
	}
	
	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		update(delta);
	}
	
	public void update(float delta) {
		engine.update(delta);
		gameUi.update(delta);
	}
	
	@Override
	public void dispose () {
		
		log(this, "Disposing game entities.");
		engine.removeAllEntities();
		engine = null;
	}
	
	public static void log(Object obj, String message) {
		Gdx.app.log(obj.getClass().getSimpleName(), message);
	}
}
