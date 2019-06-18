package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.builder.EntityBuilder;
import com.github.belserich.entity.system.*;

public class GameClient extends ApplicationAdapter {
	
	private Engine engine;
	
	@Override
	public void create () {
		
		engine = new Engine();
		
		createSystems();
		createEntities();
	}
	
	public static void error(Object obj, String message) {
		log(obj, message);
	}
	
	private void createSystems() {
		
		engine.addSystem(new CardHandleSystem());
		engine.addSystem(new LpChangeSystem());
		engine.addSystem(new SpChangeSystem());
		engine.addSystem(new ZoneChangeSystem());
		
		engine.addSystem(new ZoneIdChangedSystem());
		
		engine.addSystem(new SelectableSystem());
		engine.addSystem(new TouchableSystem());
		engine.addSystem(new PlayCardFromHandSystem());

		engine.addSystem(new LifeSystem());
		engine.addSystem(new AttackSystem());
		engine.addSystem(new ShieldSystem());
	}
	
	@Override
	public void resize(int width, int height) {
		// TODO gameUi.resize(width, height);
	}
	
	@Override
	public void render() {
		
		float delta = Gdx.graphics.getDeltaTime();
		update(delta);
		
		try {
			Thread.sleep(2);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		EntityBuilder builder = new EntityBuilder();
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P0_BATTLE).attacker();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P1_BATTLE).attackable();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_A, Zones.P0_DECK).playable();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().zone(Zones.P0_BATTLE);
		for (int i = 0; i < 7; i++) {
			engine.addEntity(builder.field(i).build());
		}
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
	
	public void update(float delta) {
		engine.update(delta);
		Services.getUiService().update(delta);
	}
}
