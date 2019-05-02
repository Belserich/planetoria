package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.SingleCardAttackLpEvent;
import com.github.belserich.entity.event.SingleCardSelectEvent;
import com.github.belserich.entity.event.SingleCardUiInteractEvent;
import com.github.belserich.entity.event.core.EventQueue;
import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class UiSystem extends EntityEvSystem<UiComponent> {
	
	public static final int[] CARDS_PER_ROW = new int[]{8, 7, 7, 8};
	private static final float CARD_HEIGHT_FACTOR = 1.421f;
	private static final float MIN_CARD_WIDTH = 75f * 1.5f;
	
	private Stage stage;
	private Table table;
	private BitmapFont font;
	
	private Cell[] cardCells;
	private EnumMap<UiZones, ZoneMeta> zones;
	
	private int currPlayer;
	private Optional<Entity> selectedCard;
	
	private int zoneIndex;
	
	public UiSystem(EventQueue eventBus) {
		super(eventBus, true, UiComponent.class);
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/gugi.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = 18;
		font = fontGen.generateFont(param);
		fontGen.dispose();
		
		int cardCount = 0;
		for (int i = 0; i < CARDS_PER_ROW.length; i++) {
			cardCount += CARDS_PER_ROW[i];
		}
		cardCells = new Cell[cardCount];
		zones = new EnumMap<UiZones, ZoneMeta>(UiZones.class);
		
		currPlayer = 0;
		selectedCard = Optional.absent();
		
		createZones();
		createUi();
	}
	
	private void createZones() {
		
		zones.put(UiZones.P1_YARD, new ZoneMeta(genZoneIndices(1)));
		zones.put(UiZones.P1_PLANET, new ZoneMeta(genZoneIndices(1)));
		zones.put(UiZones.P1_REPAIR, new ZoneMeta(genZoneIndices(5)));
		zones.put(UiZones.P1_MOTHER, new ZoneMeta(genZoneIndices(1)));
		zones.put(UiZones.P1_BATTLE, new ZoneMeta(genZoneIndices(7)));
		zones.put(UiZones.P0_BATTLE, new ZoneMeta(genZoneIndices(7)));
		zones.put(UiZones.P0_MOTHER, new ZoneMeta(genZoneIndices(1)));
		zones.put(UiZones.P0_REPAIR, new ZoneMeta(genZoneIndices(5)));
		zones.put(UiZones.P0_PLANET, new ZoneMeta(genZoneIndices(1)));
		zones.put(UiZones.P0_YARD, new ZoneMeta(genZoneIndices(1)));
	}
	
	private void createUi() {
		
		stage.setDebugAll(true);
		
		table = new Table();
		table.setFillParent(true);
		
		int cardCount = 0;
		for (int row = 0; row < CARDS_PER_ROW.length; row++) {
			for (int col = 0; col < CARDS_PER_ROW[row]; col++, cardCount++) {
				cardCells[cardCount] = table.add().width(MIN_CARD_WIDTH).height(MIN_CARD_WIDTH * CARD_HEIGHT_FACTOR).pad(10);
			}
			table.row();
		}
		
		stage.addActor(table);
	}
	
	private int[] genZoneIndices(int count) {
		
		int[] indices = new int[count];
		for (int i = 0; i < count; i++) {
			indices[i] = zoneIndex + i;
		}
		zoneIndex += count;
		return indices;
	}
	
	@Override
	protected void update() {
		super.update();
		
		Gdx.gl20.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		stage.draw();
	}
	
	@Override
	public synchronized void justAdded(Entity entity) {
		
		comp = mapper.get(entity);
		int index = zones.get(comp.zone).freeIndex(entity);
		if (index != -1) {
			setCardAt(index, entity);
		}
	}
	
	private void setCardAt(int cellIndex, Entity entity) {
		
		comp = mapper.get(entity);
		
		String cardString = comp.displayName + "\nLP: " + comp.lpStr + "\nAP: " + comp.apStr + "\nSP: " + comp.spStr;
		Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
		
		Label cardText = new Label(cardString, style);
		cardText.addListener(new CardClickListener(entity));
		cardText.setAlignment(Align.center);
		cardText.setWrap(true);
		
		cardCells[cellIndex].setActor(cardText);
	}
	
	@Override
	public synchronized void justRemoved(Entity entity) {
		super.justRemoved(entity);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		zones.clear();
	}
	
	private synchronized void selectAttempt(Entity entity) {
		
		comp = mapper.get(entity);
		if (comp.zone.playerNumber() == currPlayer) {
			selectedCard = Optional.of(entity);
			queueEvent(new SingleCardSelectEvent(entity));
		} else if (selectedCard.isPresent()) {
			queueEvent(new SingleCardUiInteractEvent(selectedCard.get(), entity));
		}
	}
	
	@Subscribe
	public void on(SingleCardAttackLpEvent ev) {
		
		Entity attacked = ev.destCard();
		if (mapper.has(attacked)) {
			
			comp = mapper.get(attacked);
			comp.lpStr = String.valueOf(ev.newLp());
			ZoneMeta meta = zones.get(comp.zone);
			setCardAt(meta.indexOf(attacked), attacked);
		}
	}
	
	public void resize(int width, int height) {
		// TODO
	}
	
	class ZoneMeta {
		
		private LinkedList<Integer> freeIndices;
		private LinkedList<Integer> occupiedIndices;
		
		private Map<Entity, Integer> cellIds;
		
		public ZoneMeta(int... indices) {
			freeIndices = new LinkedList<Integer>();
			occupiedIndices = new LinkedList<Integer>();
			
			cellIds = new HashMap<Entity, Integer>();
			
			for (int i = 0; i < indices.length; i++) {
				freeIndices.add(indices[i]);
			}
		}
		
		int freeIndex(Entity entity) {
			if (!freeIndices.isEmpty()) {
				int index = freeIndices.remove();
				occupiedIndices.add(index);
				cellIds.put(entity, index);
				return index;
			}
			return -1;
		}
		
		int indexOf(Entity entity) {
			return cellIds.get(entity);
		}
	}
	
	class CardClickListener extends ClickListener {
		
		Entity entity;
		
		public CardClickListener(Entity entity) {
			this.entity = entity;
		}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
			if (button == Input.Buttons.LEFT) {
				selectAttempt(entity);
			}
			return false;
		}
	}
}
