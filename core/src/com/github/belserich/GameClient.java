package com.github.belserich;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.builder.EntityBuilder;
import com.github.belserich.entity.system.card.CardPlayer;
import com.github.belserich.entity.system.core.*;
import com.github.belserich.entity.system.core.input.RectTouchHandler;
import com.github.belserich.entity.system.core.input.SceneSwitcher;
import com.github.belserich.entity.system.core.input.SelectHandler;
import com.github.belserich.entity.system.core.input.TurnSwitcher;
import com.github.belserich.entity.system.field.FieldOwnerSetter;
import com.github.belserich.entity.system.gfx.CardPreviewRenderer;
import com.github.belserich.entity.system.gfx.FieldRenderer;
import com.github.belserich.entity.system.gfx.UiRenderer;
import com.github.belserich.entity.system.ui.*;

public class GameClient extends ApplicationAdapter {
	
	private Engine engine;
	private Renderer renderer;
	
	@Override
	public void create () {
		
		engine = new Engine();
		
		renderer = new Renderer(engine);
		Services.setRenderer(renderer);
		
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
		engine.addSystem(new EpUpdater());
		
		engine.addSystem(new SelectHandler());
		
		engine.addSystem(new LpAttacker());
		engine.addSystem(new AttackValidator());
		engine.addSystem(new AttackerTurnHandler());
		engine.addSystem(new SpAttacker());
		
		engine.addSystem(new TurnSwitcher());
		engine.addSystem(new TurnPreparer());
		
		engine.addSystem(new UiRenderer());
		engine.addSystem(new FieldRenderer());
		engine.addSystem(new CardPreviewRenderer());
		
		engine.addSystem(new RectTouchHandler());
		engine.addSystem(new SceneSwitcher());
	}
	
	private void createEntities() {
		
		log(this, "Creating game entities.");
		
		EntityBuilder builder = new EntityBuilder();
		
		final int maxFieldsOnX = 8;
		final int maxFieldsOnY = 4;
		
		final int cols = 9;
		
		final float relW = 4;
		final float relH = 3;
		
		final float cellW = Renderer.WIDTH_UNITS / cols;
		final float cellH = cellW * (relH / relW);
		
		final float excessW = Renderer.WIDTH_UNITS - (cellW * maxFieldsOnX);
		final float excessH = Renderer.HEIGHT_UNITS - (cellH * maxFieldsOnY);
		
		final float gapX = excessW / (maxFieldsOnX + 1); // maxFieldsOnX for gap to the right of every field plus left gap of leftmost field
		final float gapY = excessH / (maxFieldsOnY + 1); // same as above
		
		final float row0X = Renderer.WIDTH_UNITS / -2f + gapX + cellW / 2f;
		final float row1X = Renderer.WIDTH_UNITS / -2f + gapX + cellW;
		final float row2X = row1X;
		final float row3X = row0X;
		
		final float spaceW = cellW + gapX;
		final float row0Y = Renderer.HEIGHT_UNITS / -2f + gapY + cellH / 2f;
		final float row1Y = row0Y + (cellH + gapY);
		final float row2Y = row1Y + (cellH + gapY);
		final float row3Y = row2Y + (cellH + gapY);
		
		final float rightPanelX = (maxFieldsOnX * (cellW + gapX) + gapX) / 2f;
		final float rightPanelY = (maxFieldsOnY * (cellH + gapY) - gapY) / -2f;
		
		final float btnW = cellW;
		final float btnH = 0.15f;
		
		final float rightBtnPnlX = rightPanelX + 0.1f + cellW / 2f;
		final float rightBtnPnlY = rightPanelY + btnH / 2f;
		final float rightButtonAdvY = 0.25f;
		
		// FIELDS
		
		builder.reset().occupiable().field(Zones.P0_BATTLE, 0).scene(0);
		for (int i = 0; i < 7; i++) {
			builder.bounds(row1X + i * spaceW, row1Y, cellW, cellH);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_REPAIR, 0).scene(0);
		for (int i = 0; i < 5; i++) {
			builder.bounds(row0X + (i + 1) * spaceW, row0Y, cellW, cellH);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_DECK, 0).occupiable().scene(1);
		for (int i = 0; i < 8; i++) {
			builder.bounds(gapX + i * (cellW + gapX), gapY + 0 * (cellH + gapY), cellW, cellH);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P0_MOTHER, 0).bounds(row0X + 0 * spaceW, row0Y, cellW, cellH).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_PLANET, 0).bounds(row0X + 6 * (cellW + gapX), row0Y, cellW, cellH).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P0_YARD, 0).bounds(row0X + 7 * (cellW + gapX), row0Y, cellW, cellH).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().occupiable().field(Zones.P1_BATTLE, 1).scene(0);
		for (int i = 0; i < 7; i++) {
			builder.bounds(row2X + i * spaceW, row2Y, cellW, cellH);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_REPAIR, 1).scene(0);
		for (int i = 0; i < 5; i++) {
			builder.bounds(row3X + (i + 2) * spaceW, row3Y, cellW, cellH);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_DECK, 1).occupiable().scene(1);
		for (int i = 0; i < 8; i++) {
			builder.bounds(gapX + i * (cellW + gapX), gapY + 1 * (cellH + gapY), cellW, cellH);
			engine.addEntity(builder.build());
		}
		
		builder.reset().field(Zones.P1_MOTHER, 1).bounds(row3X + 7 * spaceW, row3Y, cellW, cellH).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_PLANET, 1).bounds(row3X + 1 * spaceW, row3Y, cellW, cellH).scene(0);
		engine.addEntity(builder.build());
		
		builder.reset().field(Zones.P1_YARD, 1).bounds(row3X + 0 * spaceW, row3Y, cellW, cellH).scene(0);
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
		
		// UI
		
		engine.addEntity(builder.reset().scene(0).switc(1).ui("Hand", rightBtnPnlX, rightBtnPnlY + rightButtonAdvY, btnW, btnH).build());
		engine.addEntity(builder.reset().scene(1).switc(0).ui("Back", rightBtnPnlX, rightBtnPnlY + rightButtonAdvY, btnW, btnH).build());
		
		engine.addEntity(builder.reset().scene(0).ui("-", rightBtnPnlX, rightBtnPnlY, btnW, btnH).ownedByPlayer(0).turnable().epUpdatable().build());
		engine.addEntity(builder.reset().scene(0).ui("-", rightBtnPnlX, rightBtnPnlY, btnW, btnH).ownedByPlayer(1).turnable().epUpdatable().build());
		engine.addEntity(builder.scene(1).build());
		
		// PLAYERS
		
		engine.addEntity(builder.reset().scene(0).ui("Turn", rightBtnPnlX, rightBtnPnlY + 2 * rightButtonAdvY, btnW, btnH).ownedByPlayer(0).turnable().build());
		
		engine.addEntity(builder.reset().player(0).build());
		engine.addEntity(builder.reset().player(1).build());
	}
	
	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}
	
	@Override
	public void render() {
		
		float delta = Gdx.graphics.getDeltaTime();
		
		engine.update(delta);
		renderer.render();
		
		sleep(2);
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void dispose () {
		
		log(this, "Disposing game entities.");
		
		engine.removeAllEntities();
		renderer.dispose();
	}
	
	public static void log(Object obj, String message, Object... args) {
		Gdx.app.log(obj.getClass().getSimpleName(), String.format(message, args));
	}
}
