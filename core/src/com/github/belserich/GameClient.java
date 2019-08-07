package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.builder.EntityBuilder;
import com.github.belserich.entity.system.card.CardPlayer;
import com.github.belserich.entity.system.core.*;
import com.github.belserich.entity.system.core.input.SelectHandler;
import com.github.belserich.entity.system.core.input.TouchHandler;
import com.github.belserich.entity.system.core.input.TurnHandler;
import com.github.belserich.entity.system.field.FieldOwnerSetter;
import com.github.belserich.entity.system.ui.*;

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
		
		engine.addSystem(new FieldUiCreator());
		engine.addSystem(new CardUiUpdater());
		engine.addSystem(new CardUiCreator());
		engine.addSystem(new CardUiRemover());
		engine.addSystem(new CardUiMover());
		engine.addSystem(new EnergyUiUpdater());
		
		engine.addSystem(new FieldOwnerSetter());
		engine.addSystem(new CardPlayer());
		
		engine.addSystem(new EpReducer());
		engine.addSystem(new SelectHandler());
		engine.addSystem(new TouchHandler());
		
		engine.addSystem(new LpAttacker());
		engine.addSystem(new AttackValidator());
		engine.addSystem(new SpAttacker());
		
		engine.addSystem(new TurnHandler());
		engine.addSystem(new TurnGiver());
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
	}
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		EntityBuilder builder = new EntityBuilder();
		
		thisPlayer = builder.reset().player(0).turnableOn().build();
		opponentPlayer = builder.reset().player(1).build();
		
		// FIELDS
		
		builder.reset().field(Zones.P0_BATTLE, 0).occupiable();
		for (int i = 0; i < 7; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_REPAIR, 0);
		for (int i = 0; i < 5; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_DECK, 0).occupiable();
		for (int i = 0; i < 8; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_MOTHER, 0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_PLANET, 0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_YARD, 0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_BATTLE, 1).occupiable();
		for (int i = 0; i < 7; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_REPAIR, 1);
		for (int i = 0; i < 5; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_DECK, 1);
		for (int i = 0; i < 8; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_MOTHER, 1);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_PLANET, 1);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_YARD, 1);
		engine.addEntity(builder.build());
		
		// CARDS
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P0_BATTLE, 0).attacker();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P1_BATTLE, 1).covered().attackable();
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_A, Zones.P0_DECK, 0).attacker().playable();
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
