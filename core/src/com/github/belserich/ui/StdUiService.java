package com.github.belserich.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.belserich.ui.core.BaseUiService;
import com.github.belserich.util.UiHelper;

import java.util.Locale;

import static com.github.belserich.asset.Zones.*;

public class StdUiService extends BaseUiService {
	
	private Stage stage;
	private InputMultiplexer mult;
	private Viewport view;
	private Camera cam;
	
	private VerticalGroup rootGroup;
	
	private VerticalGroup mainGroup;
	private VerticalGroup deckGroup;
	
	private Label deckToggle;
	private boolean toggled;
	
	private Label turnLabel;
	
	public StdUiService() {
		
		stage = new Stage();
		
		mult = new InputMultiplexer(stage);
		Gdx.input.setInputProcessor(mult);
		
		view = new FitViewport(1200, 900);
		stage.setViewport(view);
		
		cam = new OrthographicCamera();
		cam.translate(600, 450, 0);
		view.setCamera(cam);
		
		createBoardUi();
	}
	
	private void createBoardUi() {
		
		rootGroup = new VerticalGroup();
		rootGroup.setFillParent(true);
		stage.addActor(rootGroup);
		
		{
			mainGroup = new VerticalGroup();
			mainGroup.pad(70);
			mainGroup.space(10);
			
			mainGroup.addActor(UiHelper.horizontalGroup(10, zoneStrat.get(P1_YARD.ordinal()), zoneStrat.get(P1_PLANET.ordinal()), zoneStrat.get(P1_REPAIR.ordinal()),
					zoneStrat.get(P1_MOTHER.ordinal())));
			mainGroup.addActor(UiHelper.horizontalGroup(10, zoneStrat.get(P1_BATTLE.ordinal())));
			mainGroup.addActor(UiHelper.horizontalGroup(10, zoneStrat.get(P0_BATTLE.ordinal())));
			mainGroup.addActor(UiHelper.horizontalGroup(10, zoneStrat.get(P0_MOTHER.ordinal()), zoneStrat.get(P0_REPAIR.ordinal()), zoneStrat.get(P0_PLANET.ordinal()),
					zoneStrat.get(P0_YARD.ordinal())));
			
			rootGroup.addActor(mainGroup);
		}
		
		{
			deckGroup = new VerticalGroup();
			deckGroup.pad(70);
			deckGroup.space(100);
			
			deckGroup.addActor(UiHelper.horizontalGroup(10, zoneStrat.get(P0_DECK.ordinal())));
			deckGroup.addActor(UiHelper.horizontalGroup(10, zoneStrat.get(P1_DECK.ordinal())));
		}
		
		// ---
		
		deckToggle = new Label("Hand", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
		deckToggle.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				toggleDeck();
			}
		});
		
		turnLabel = new Label("Turn", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
		turnLabel.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				fireTurnCallbacks();
			}
		});
		
		rootGroup.addActor(UiHelper.horizontalGroup(300, deckToggle, turnLabel));
	}
	
	private void toggleDeck() {
		
		toggled = !toggled;
		if (toggled) {
			rootGroup.removeActor(mainGroup);
			rootGroup.addActorBefore(deckToggle, deckGroup);
		} else {
			rootGroup.removeActor(deckGroup);
			rootGroup.addActorBefore(deckToggle, mainGroup);
		}
	}
	
	@Override
	public void update(float delta) {
		Gdx.gl20.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		cam.update();
		
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		view.update(width, height);
		cam.update();
	}
	
	private static void debug(String fstr, Object... args) {
		System.err.println(String.format(Locale.getDefault(), fstr, args));
	}
}
