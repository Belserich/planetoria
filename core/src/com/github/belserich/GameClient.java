package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.builder.EntityBuilder;
import com.github.belserich.entity.system.*;

public class GameClient extends ApplicationAdapter {
	
	private Engine engine;
	
	private Entity thisPlayer, opponentPlayer;
	
	@Override
	public void create () {
		
		engine = new Engine();
		
		createSystems();
		createEntities();
	}
	
	public static void error(Object obj, String message, Object... args) {
		log(obj, String.format(message, args));
	}
	
	private void createSystems() {
		
		engine.addSystem(new FieldUiSystem());
		engine.addSystem(new CardUiSystem());
		
		engine.addSystem(new SelectableSystem());
		engine.addSystem(new TouchableSystem());
		
		engine.addSystem(new TurnChangeSystem());
		
		engine.addSystem(new PlayCardFromHandSystem());
		
		engine.addSystem(new LpAttackSystem());
		engine.addSystem(new AttackSystem());
		engine.addSystem(new ShieldSystem());
	}
	
	@Override
	public void resize(int width, int height) {
		Services.getUiService().resize(width, height);
	}
	
	float timer;
	
	@Override
	public void render() {
		
		float delta = Gdx.graphics.getDeltaTime();
		update(delta);
		
		timer += delta;
		
		try {
			Thread.sleep(2);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		
		if ((timer += delta) >= 5) {
//			thisPlayer.remove(Turn.class);
//			opponentPlayer.add(new Turn());
		}
	}
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		EntityBuilder builder = new EntityBuilder();
		
		thisPlayer = builder.reset().ownedByPlayer(0).turn().build();
		opponentPlayer = builder.reset().ownedByPlayer(1).build();
		
		// FIELDS
		
		builder.reset().field(Zones.P0_BATTLE);
		for (int i = 0; i < 7; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_REPAIR);
		for (int i = 0; i < 5; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_DECK);
		for (int i = 0; i < 8; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_MOTHER);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_PLANET);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_YARD);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_BATTLE);
		for (int i = 0; i < 7; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_REPAIR);
		for (int i = 0; i < 5; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_DECK);
		for (int i = 0; i < 8; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_MOTHER);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_PLANET);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_YARD);
		engine.addEntity(builder.build());
		
		// CARDS
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P0_BATTLE, 0);
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P1_BATTLE, 1).covered();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_A, Zones.P0_DECK, 0).playable();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.STRATEGY_1, Zones.P0_DECK, 0).playable();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		engine.addEntity(thisPlayer);
		engine.addEntity(opponentPlayer);
	}
	
	@Override
	public void dispose () {
		
		log(this, "Disposing game entities.");
		
		engine.removeAllEntities();
		engine = null;
	}
	
	public static void log(Object obj, String message, Object... args) {
		Gdx.app.log(obj.getClass().getSimpleName(), String.format(message, args));
	}
	
	public void update(float delta) {
		engine.update(delta);
		Services.getUiService().update(delta);
	}
}
