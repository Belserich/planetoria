package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.builder.CardBuilder;
import com.github.belserich.entity.system.*;
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
		
		CardBuilder card = new CardBuilder(gameUi);
		
		card.reset().type(Cards.SPACESHIP_B).zone(UiZones.P0_BATTLE).attacker();
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		
		card.reset().type(Cards.SPACESHIP_B).zone(UiZones.P1_BATTLE).attackable();
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		
		card.reset().type(Cards.SPACESHIP_A).zone(UiZones.P0_DECK);
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		engine.addEntity(card.build());
		engine.addEntity(card.build());
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
