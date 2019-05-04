package com.github.belserich;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvEngine;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.system.AttackSystem;
import com.github.belserich.entity.system.LifeSystem;
import com.github.belserich.entity.system.ShieldSystem;
import com.github.belserich.entity.system.UiSystem;

public class GameClient extends ApplicationAdapter {
	
	private EntityEvEngine engine;
	private EventQueue queue;
	
	private LifeSystem lifeSys;
	private AttackSystem attackSys;
	private ShieldSystem shieldSys;
	private UiSystem uiSys;
	
	@Override
	public void create () {
		
		engine = new EntityEvEngine();
		queue = new EventQueue();
		
		createEntities();
		createSystems();
	}
	
	private void createEntities() {
		
		log(this, "Initializing game entities.");
		
		engine.addEntity(createShipA(new Entity(), UiZones.P0_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P0_BATTLE));
		
		engine.addEntity(createShipA(new Entity(), UiZones.P1_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P1_BATTLE));
		engine.addEntity(createShipA(new Entity(), UiZones.P1_BATTLE));
		engine.addEntity(createShipB(new Entity(), UiZones.P1_BATTLE));
	}
	
	private void createSystems() {
		
		log(this, "Initializing entity systems.");
		
		lifeSys = new LifeSystem(queue);
		engine.addSystem(lifeSys);
		
		attackSys = new AttackSystem(queue);
		engine.addSystem(attackSys);
		
		shieldSys = new ShieldSystem(queue);
		engine.addSystem(shieldSys);
		
		uiSys = new UiSystem(queue);
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
}
