package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.builder.EntityBuilder;
import com.github.belserich.entity.system.card.CardPlayer;
import com.github.belserich.entity.system.core.*;
import com.github.belserich.entity.system.core.input.RectTouchHandler;
import com.github.belserich.entity.system.core.input.SelectHandler;
import com.github.belserich.entity.system.core.input.TurnHandler;
import com.github.belserich.entity.system.field.FieldOwnerSetter;
import com.github.belserich.entity.system.gfx.CardRenderer;
import com.github.belserich.entity.system.gfx.FieldRenderer;
import com.github.belserich.entity.system.ui.*;

public class GameClient extends ApplicationAdapter {
	
	public static final int CARDS_PER_ROW = 12;
	
	private Engine engine;
	private Batch batch;
	
	@Override
	public void create () {
		
		engine = new Engine();
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		OrthographicCamera boardCam = new OrthographicCamera(CARDS_PER_ROW, (171f / 243f) * CARDS_PER_ROW * (height / width));
		boardCam.translate(boardCam.viewportWidth / 2f, boardCam.viewportHeight / 2f);
		Services.setBoardCamera(boardCam);
		
		OrthographicCamera textCam = new OrthographicCamera(width, height);
		textCam.translate(textCam.viewportWidth / 2f, textCam.viewportHeight / 2f);
		Services.setTextCamera(textCam);
		
		batch = new SpriteBatch();
		Services.setBatch(batch);
		
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gugi.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.color = Color.FOREST;
		param.size = 20;
		BitmapFont font = fontGen.generateFont(param);
		fontGen.dispose();
		
		Services.setFont(font);
		
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
		
		engine.addSystem(new LpAttacker());
		engine.addSystem(new AttackValidator());
		engine.addSystem(new AttackerTurnHandler());
		engine.addSystem(new SpAttacker());
		
		engine.addSystem(new TurnHandler());
		engine.addSystem(new TurnGiver());
		
		engine.addSystem(new FieldRenderer());
		engine.addSystem(new CardRenderer());
		engine.addSystem(new RectTouchHandler());
	}
	
	@Override
	public void resize(int width, int height) {
		Services.getUiService().resize(width, height);
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
		
		Entity thisPlayer = builder.reset().player(0).turnableOn().build();
		Entity opponentPlayer = builder.reset().player(1).build();
		
		// FIELDS
		
		builder.reset().occupiable().field(Zones.P0_BATTLE, 0);
		for (int i = 0; i < 7; i++) {
			builder.touchableRect(1.25f * i + 1.75f, 1.25f * 1 + .5f);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_REPAIR, 0);
		for (int i = 0; i < 5; i++) {
			builder.touchableRect(1.25f * (i + 1f) + 1.1f, 1.25f * 0 + .5f);
			engine.addEntity(builder.build());
		}

//		builder.reset().occupiable();
//		for (int i = 0; i < 8; i++) {
//			builder.field(Zones.P0_BATTLE, 0, 1.25f * i + 1.75f, 1.25f * 1 + .5f);
//			engine.addEntity(builder.build());
//		}
		
		builder.reset().field(Zones.P0_MOTHER, 0).touchableRect(1.25f * 6f + 1.1f, 1.25f * 0 + .5f);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_PLANET, 0).touchableRect(1.25f * 7f + 1.1f, 1.25f * 0 + .5f);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_YARD, 0).touchableRect(1.25f * 0f + 1.1f, 1.25f * 0 + .5f);
		engine.addEntity(builder.build());
		
		builder.reset().occupiable().field(Zones.P1_BATTLE, 1);
		for (int i = 0; i < 7; i++) {
			builder.touchableRect(1.25f * i + 1.75f, 1.25f * 2 + .5f);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_REPAIR, 1);
		for (int i = 0; i < 5; i++) {
			builder.touchableRect(1.25f * (i + 2f) + 1.1f, 1.25f * 3 + .5f);
			engine.addEntity(builder.build());
		}

//		builder.reset().occupiable();
//		for (int i = 0; i < 8; i++) {
//			builder.field(Zones.P1_BATTLE, 1, 1.25f * i + 1.75f, 1.25f * 1 + .5f);
//			engine.addEntity(builder.build());
//		}
		
		builder.reset().field(Zones.P1_MOTHER, 1).touchableRect(1.25f * 1f + 1.1f, 1.25f * 3 + .5f);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_PLANET, 1).touchableRect(1.25f * 0f + 1.1f, 1.25f * 3 + .5f);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_YARD, 1).touchableRect(1.25f * 7f + 1.1f, 1.25f * 3 + .5f);
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

//		builder.reset().card(Cards.SPACESHIP_A, Zones.P0_DECK, 0).attacker().playable();
//		for (int i = 0; i < 4; i++) {
//			engine.addEntity(builder.build());
//		}
//
//		builder.reset().card(Cards.STRATEGY_1, Zones.P0_DECK, 0).playable();
//		for (int i = 0; i < 4; i++) {
//			engine.addEntity(builder.build());
//		}
		
		engine.addEntity(thisPlayer);
		engine.addEntity(opponentPlayer);
	}
	
	@Override
	public void dispose () {
		
		log(this, "Disposing game entities.");
		
		engine.removeAllEntities();
		batch.dispose();
	}
	
	public static void log(Object obj, String message, Object... args) {
		Gdx.app.log(obj.getClass().getSimpleName(), String.format(message, args));
	}
	
	public void update(float delta) {
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		engine.update(delta);
//		Services.getUiService().update(delta);
	}
}
