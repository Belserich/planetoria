package com.github.belserich.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.belserich.asset.Zones;
import com.github.belserich.ui.core.BaseUiService;
import com.github.belserich.util.UiHelper;

import static com.github.belserich.asset.Zones.*;

public class StdUiService extends BaseUiService {
	
	private Stage stage;
	private Viewport view;
	private Camera cam;
	
	private VerticalGroup rootGroup;
	
	private VerticalGroup mainGroup;
	private VerticalGroup deckGroup;
	
	private HorizontalGroup optionGroup;
	
	private boolean toggled;
	
	private Label player0Energy, player1Energy;
	
	public StdUiService() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		view = new FitViewport(1200, 900);
		stage.setViewport(view);
		
		cam = new OrthographicCamera();
		cam.translate(600, 450, 0);
		view.setCamera(cam);
		
		createZones();
		createBoardUi();
	}
	
	@Override
	public void setPlayerEnergy(int playerId, int pts, int def) {
		
		String text = pts + " / " + def;
		if (playerId == 0) {
			player0Energy.setText(text);
		} else if (playerId == 1) {
			player1Energy.setText(text);
		}
	}
	
	private void createZones() {
		
		addZone(Zones.P0_BATTLE);
		addZone(Zones.P1_BATTLE);
		addZone(Zones.P0_REPAIR);
		addZone(Zones.P1_REPAIR);
		
		addZone(Zones.P0_YARD);
		addZone(Zones.P1_YARD);
		addZone(Zones.P0_PLANET);
		addZone(Zones.P1_PLANET);
		addZone(Zones.P0_MOTHER);
		addZone(Zones.P1_MOTHER);
		
		addZone(Zones.P0_DECK);
		addZone(Zones.P1_DECK);
	}
	
	private void createBoardUi() {
		
		rootGroup = new VerticalGroup();
		rootGroup.setFillParent(true);
		stage.addActor(rootGroup);
		
		rootGroup.padTop(30);
		player1Energy = new Label("0 / 0", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
		rootGroup.addActor(player1Energy);
		
		{
			mainGroup = new VerticalGroup();
			mainGroup.pad(30);
			mainGroup.space(10);
			
			mainGroup.addActor(UiHelper.horizontalGroup(10, getZone(P1_YARD), getZone(P1_PLANET), getZone(P1_REPAIR), getZone(P1_MOTHER)));
			mainGroup.addActor(UiHelper.horizontalGroup(10, getZone(P1_BATTLE)));
			mainGroup.addActor(UiHelper.horizontalGroup(10, getZone(P0_BATTLE)));
			mainGroup.addActor(UiHelper.horizontalGroup(10, getZone(P0_MOTHER), getZone(P0_REPAIR), getZone(P0_PLANET), getZone(P0_YARD)));
			
			rootGroup.addActor(mainGroup);
		}
		
		{
			deckGroup = new VerticalGroup();
			deckGroup.pad(70);
			deckGroup.space(100);
			
			deckGroup.addActor(UiHelper.horizontalGroup(10, getZone(P0_DECK)));
			deckGroup.addActor(UiHelper.horizontalGroup(10, getZone(P1_DECK)));
		}
		
		// ---
		
		{
			optionGroup = new HorizontalGroup();
			optionGroup.space(300);
			
			final Label deckToggle = new Label("Hand", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
			deckToggle.addListener(new ClickListener() {
				
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					toggleDeck();
				}
			});
			optionGroup.addActor(deckToggle);
			
			final Label turnLabel = new Label("Turn", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
			turnLabel.addListener(new ClickListener() {
				
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					fireTurnCallbacks();
				}
			});
			optionGroup.addActor(turnLabel);
			
			rootGroup.addActor(optionGroup);
		}
		
		player0Energy = new Label("0 / 0", new Label.LabelStyle(UiHelper.largeFont, Color.BLACK));
		rootGroup.addActor(player0Energy);
	}
	
	private void toggleDeck() {
		
		toggled = !toggled;
		if (toggled) {
			rootGroup.removeActor(mainGroup);
			rootGroup.addActorBefore(optionGroup, deckGroup);
		} else {
			rootGroup.removeActor(deckGroup);
			rootGroup.addActorBefore(optionGroup, mainGroup);
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
}
