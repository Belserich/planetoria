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
import com.github.belserich.entity.system.core.input.UiHandler;
import com.github.belserich.entity.system.field.FieldOwnerSetter;
import com.github.belserich.entity.system.gfx.CardRenderer;
import com.github.belserich.entity.system.gfx.FieldRenderer;
import com.github.belserich.entity.system.gfx.UiRenderer;
import com.github.belserich.entity.system.ui.*;

public class GameClient extends ApplicationAdapter {
	
	public static final int CARDS_PER_ROW = 12;
	
	private Engine engine;
	
	private OrthographicCamera boardCam;
	private OrthographicCamera textCam;
	private Batch batch;
	
	@Override
	public void create () {
		
		engine = new Engine();
		
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		boardCam = new OrthographicCamera(CARDS_PER_ROW, (171f / 243f) * CARDS_PER_ROW * (height / width));
		boardCam.translate(boardCam.viewportWidth / 2f, boardCam.viewportHeight / 2f);
		Services.setBoardCamera(boardCam);
		
		textCam = new OrthographicCamera(width, height);
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
		engine.addSystem(new UiRenderer());
		engine.addSystem(new RectTouchHandler());
		engine.addSystem(new UiHandler());
	}
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		EntityBuilder builder = new EntityBuilder();
		
		Entity thisPlayer = builder.reset().player(0).turnableOn().build();
		Entity opponentPlayer = builder.reset().player(1).build();
		
		float offY = 1f;
		float offX;
		
		// FIELDS
		
		builder.reset().occupiable().field(Zones.P0_BATTLE, 0).scene(0);
		for (int i = 0; i < 7; i++) {
			builder.touchableRect(1.25f * i + 1.75f, 1.25f * 1 + offY);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_REPAIR, 0).scene(0);
		for (int i = 0; i < 5; i++) {
			builder.touchableRect(1.25f * (i + 1f) + 1.1f, 1.25f * 0 + offY);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_DECK, 0).occupiable().scene(1);
		for (int i = 0; i < 8; i++) {
			builder.touchableRect(1.25f * i + 1.1f, 1.25f * 3 + offY);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_MOTHER, 0).touchableRect(1.25f * 6f + 1.1f, 1.25f * 0 + offY).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_PLANET, 0).touchableRect(1.25f * 7f + 1.1f, 1.25f * 0 + offY).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_YARD, 0).touchableRect(1.25f * 0f + 1.1f, 1.25f * 0 + offY).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().occupiable().field(Zones.P1_BATTLE, 1).scene(0);
		for (int i = 0; i < 7; i++) {
			builder.touchableRect(1.25f * i + 1.75f, 1.25f * 2 + offY);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_REPAIR, 1).scene(0);
		for (int i = 0; i < 5; i++) {
			builder.touchableRect(1.25f * (i + 2f) + 1.1f, 1.25f * 3 + offY);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_DECK, 1).occupiable().scene(1);
		for (int i = 0; i < 8; i++) {
			builder.touchableRect(1.25f * i + 1.1f, 1.25f * 2 + offY);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_MOTHER, 1).touchableRect(1.25f * 1f + 1.1f, 1.25f * 3 + offY).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_PLANET, 1).touchableRect(1.25f * 0f + 1.1f, 1.25f * 3 + offY).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_YARD, 1).touchableRect(1.25f * 7f + 1.1f, 1.25f * 3 + offY).scene(0);
		engine.addEntity(builder.build());
		
		// CARDS
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P0_BATTLE, 0).attacker().scene(0);
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_B, Zones.P1_BATTLE, 1).covered().attackable().scene(0);
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.SPACESHIP_A, Zones.P0_DECK, 0).attacker().playable().scene(1);
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		builder.reset().card(Cards.STRATEGY_1, Zones.P0_DECK, 0).playable().scene(1);
		for (int i = 0; i < 4; i++) {
			engine.addEntity(builder.build());
		}
		
		engine.addEntity(thisPlayer);
		engine.addEntity(opponentPlayer);
		
		// UI
		
		offX = 4.2f;
		
		engine.addEntity(builder.reset().scene(0).switc(1).ui("Hand", offX, 0.333f, 1, 0.25f).build());
		engine.addEntity(builder.reset().scene(1).switc(0).ui("Back", offX, 0.333f, 1, 0.25f).build());
		
		engine.addEntity(builder.reset().scene(0).ui("0 / 0", offX + 1.25f, 0.333f, 1, 0.25f).build());
		engine.addEntity(builder.reset().scene(0).ui("0 / 0", offX + 1.25f, 1.25f * 4f + offY, 1, 0.25f).build());
		engine.addEntity(builder.scene(1).build());
		
		engine.addEntity(builder.reset().scene(0).ui("Turn", offX + 2.5f, 0.333f, 1, 0.25f).build());
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
	
	@Override
	public void resize(int width, int height) {
		textCam.setToOrtho(false, width, height);
		Services.getUiService().resize(width, height);
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
