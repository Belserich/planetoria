package com.github.belserich;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.GameUi;
import com.github.belserich.asset.UiZones;
import com.github.belserich.data.selection.LifoSelection;
import com.github.belserich.data.selection.Selection;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityEvEngine;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.system.*;

public class GameClient extends ApplicationAdapter {
	
	private EntityEvEngine engine;
	private EventQueue queue;
	private GameUi gameUi;
	
	private LifeSystem lifeSys;
	private AttackSystem attackSys;
	private ShieldSystem shieldSys;
	private SelectionSystem selectionSys;
	
	private UiSystem uiSys;
	
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
		
		log(this, "Initializing entity systems.");
		
		lifeSys = new LifeSystem(queue);
		engine.addSystem(lifeSys);
		
		attackSys = new AttackSystem(queue);
		engine.addSystem(attackSys);
		
		shieldSys = new ShieldSystem(queue);
		engine.addSystem(shieldSys);
		
		selectionSys = new SelectionSystem(queue);
		engine.addSystem(selectionSys);
		
		uiSys = new UiSystem(queue, gameUi);
		engine.addSystem(uiSys);
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
		uiSys.resize(width, height);
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
		engine.dispose();
		
		log(this, "Disposing game systems.");
		
		lifeSys.dispose();
		attackSys.dispose();
		shieldSys.dispose();
		
		uiSys.dispose();
	}
	
	public static void log(Object obj, String message) {
		Gdx.app.log(obj.getClass().getSimpleName(), message);
	}
	
	private void createEntities() {
		
		log(this, "Initializing game entities.");
		
		Selection<Entity> p0bSelection = new LifoSelection<Entity>(Entity.class);
		
		Entity p0b0 = new Entity();
		p0b0.add(new SelectionComponent(gameUi.getZoneUi(UiZones.P0_BATTLE).getFieldUi(0), p0bSelection));
		
		Entity p0b1 = new Entity();
		p0b1.add(new SelectionComponent(gameUi.getZoneUi(UiZones.P0_BATTLE).getFieldUi(1), p0bSelection));
		
		Entity p0b2 = new Entity();
		p0b2.add(new SelectionComponent(gameUi.getZoneUi(UiZones.P0_BATTLE).getFieldUi(2), p0bSelection));
		
		Entity p0b3 = new Entity();
		p0b3.add(new SelectionComponent(gameUi.getZoneUi(UiZones.P0_BATTLE).getFieldUi(3), p0bSelection));
		
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_DECK));
		engine.addEntity(createShipB(p0b0, UiZones.P0_BATTLE));
		engine.addEntity(createShipB(p0b1, UiZones.P0_BATTLE));
		engine.addEntity(createShipA(p0b2, UiZones.P0_BATTLE));
		engine.addEntity(createShipA(p0b3, UiZones.P0_BATTLE));
		
		engine.addEntity(createShipA(new Entity(), UiZones.P1_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P1_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P1_BATTLE));
		engine.addEntity(createShipB(new Entity(), UiZones.P1_BATTLE));
	}
}
