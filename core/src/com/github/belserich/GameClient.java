package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityEvEngine;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.system.*;
import com.github.belserich.ui.GameUi;

public class GameClient extends ApplicationAdapter {
	
	private Engine engine;
	private EventQueue queue;
	private GameUi gameUi;
	
	@Override
	public void create () {
		
		engine = new EntityEvEngine();
		queue = new EventQueue();
		gameUi = new GameUi();
		
		createEntities();
		createSystems();
	}
	
	public static void error(Object obj, String message) {
		log(obj, message);
	}
	
	private void createSystems() {
		
		engine.addSystem(new LifeSystem(queue));
		engine.addSystem(new AttackSystem(queue));
		engine.addSystem(new ShieldSystem(queue));
		engine.addSystem(new AttackableSystem());
		engine.addSystem(new UiSystem(queue, gameUi));
	}
	
	private Entity createShipA(Entity entity, UiZones zone) {
		
		entity.add(new LifeComponent(1));
		entity.add(new ShieldComponent(1));
		entity.add(new AttackComponent(1, 1));
		entity.add(new UiComponent(zone, "Raumschiff A", "1.0", "1.0", "1.0"));
		return entity;
	}
	
	private Entity createShipB(Entity entity, UiZones zone) {
		
		entity.add(new LifeComponent(2));
		entity.add(new ShieldComponent(2));
		entity.add(new AttackComponent(2, 1));
		entity.add(new UiComponent(zone, "Raumschiff B", "2.0", "2.0", "2.0"));
		return entity;
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
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		Entity p0b0 = new Entity();
		Entity p0b1 = new Entity();
		Entity p0b2 = new Entity();
		Entity p0b3 = new Entity();
		
		engine.addEntity(createShipB(p0b0, UiZones.P0_BATTLE));
		engine.addEntity(createShipB(p0b1, UiZones.P0_BATTLE));
		engine.addEntity(createShipA(p0b2, UiZones.P0_BATTLE));
		engine.addEntity(createShipA(p0b3, UiZones.P0_BATTLE));
		
		Entity p1b0 = new Entity();
		Entity p1b1 = new Entity();
		Entity p1b2 = new Entity();
		Entity p1b3 = new Entity();
		p1b3.add(new AttackableComponent(gameUi.getZoneUi(UiZones.P1_BATTLE).getFieldUi(0)));
		
		engine.addEntity(createShipA(p1b0, UiZones.P1_BATTLE));
		engine.addEntity(createShipA(p1b1, UiZones.P1_BATTLE));
		engine.addEntity(createShipA(p1b2, UiZones.P1_BATTLE));
		engine.addEntity(createShipB(p1b3, UiZones.P1_BATTLE));
		
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
	}
}
