package com.github.belserich.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.github.belserich.ui.core.BaseUiService;
import com.github.belserich.util.UiHelper;

import static com.github.belserich.asset.Zones.*;

public class StdUiService extends BaseUiService {
	
	private Stage stage;
	private VerticalGroup rootGroup;
	
	private VerticalGroup mainGroup;
	private VerticalGroup deckGroup;
	
	private Label deckToggle;
	private boolean toggled;
	
	public StdUiService() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
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
		rootGroup.addActor(deckToggle);
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
		
		stage.act(delta);
		stage.draw();
	}
}
