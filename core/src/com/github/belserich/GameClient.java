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
import com.github.belserich.entity.system.UiSystem;

public class GameClient extends ApplicationAdapter {
	
	private EntityEvEngine engine;
	private EventQueue queue;
	
	private UiSystem uiSys;
	private AttackSystem attackSys;
	private LifeSystem lifeSys;
	
	@Override
	public void create () {
		
		engine = new EntityEvEngine();
		queue = new EventQueue();
		
		createEntities();
		createSystems();
	}
	
	private void createEntities() {
		
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
		
		uiSys = new UiSystem(queue);
		engine.addSystem(uiSys);
		
		attackSys = new AttackSystem(queue);
		engine.addSystem(attackSys);
		
		lifeSys = new LifeSystem(queue);
		engine.addSystem(lifeSys);
	}
	
	private Entity createShipA(Entity entity, UiZones zone) {
		
		entity.add(new LifeComponent(1));
		entity.add(new ShieldComponent(1));
		entity.add(new AttackComponent(1));
		entity.add(new UiComponent(zone, "Raumschiff A", "1", "1", "1"));
		return entity;
	}
	
	private Entity createShipB(Entity entity, UiZones zone) {
		
		entity.add(new LifeComponent(2));
		entity.add(new ShieldComponent(2));
		entity.add(new AttackComponent(2));
		entity.add(new UiComponent(zone, "Raumschiff B", "2", "2", "2"));
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
		engine.dispose();
		uiSys.dispose();
	}
	
	public static void log(Object obj, String message) {
		Gdx.app.log(obj.getClass().getSimpleName(), message);
	}
}
