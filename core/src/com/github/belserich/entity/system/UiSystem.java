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
import com.github.belserich.GameClient;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.CardDestroyLpEvent;
import com.github.belserich.entity.event.base.CardAttackBaseEvent;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.event.interact.CardInteractEvent;
import com.github.belserich.entity.event.select.CardSelectEvent;
import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;

import java.util.*;

public class UiSystem extends EntityEvSystem<UiComponent> {
	
	public static final int[] CARDS_PER_ROW = new int[]{8, 7, 7, 8};
	
	private static final float CARD_HEIGHT_FACTOR = 1.421f;
	private static final float MIN_CARD_WIDTH = 75f * 1.5f;
	
	private Stage stage;
	private Table table;
	private Cell[] cardCells;
	private BitmapFont font;
	
	private EnumMap<UiZones, ZoneMeta> zones;
	
	private int currPlayer;
	private Optional<Entity> primaryCard;
	private Set<Entity> secondaryCards;
	
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
		primaryCard = Optional.absent();
		secondaryCards = new HashSet<Entity>();
		
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
	
	private static String cardLogString(Entity entity) {
		UiComponent uic = entity.getComponent(UiComponent.class);
		return uic.displayName + ";" + uic.lpStr + ";" + uic.apStr + ";" + uic.spStr;
	}
	
	@Override
	public synchronized void justAdded(Entity entity) {
		
		comp = mapper.get(entity);
		updateCard(entity);
	}
	
	private synchronized void updateCard(Entity entity) {
		
		UiComponent uic = entity.getComponent(UiComponent.class); // create central Mapper-collection-class when this call gets too slow
		
		LifeComponent lc = entity.getComponent(LifeComponent.class);
		AttackComponent ac = entity.getComponent(AttackComponent.class);
		ShieldComponent sc = entity.getComponent(ShieldComponent.class);
		
		uic.lpStr = lc != null ? String.valueOf(Math.max(0f, lc.pts)) : "?";
		uic.apStr = lc != null ? String.valueOf(Math.max(0f, ac.pts)) : "?";
		uic.spStr = lc != null ? String.valueOf(Math.max(0f, sc.pts)) : "?";
		
		int cellIndex = zones.get(comp.zone).indexOf(entity);
		if (cellIndex == -1) {
			cellIndex = zones.get(comp.zone).freeIndex(entity);
			if (cellIndex == -1) {
				throw new RuntimeException("No free cell-ids in zone " + comp.zone);
			}
		}
		
		String cardString = uic.displayName + "\nLP: " + uic.lpStr + "\nAP: " + uic.apStr + "\nSP: " + uic.spStr;
		
		Label.LabelStyle style = new Label.LabelStyle(font, Color.BLACK);
		Label cardText = new Label(cardString, style);
		cardText.addListener(new CardClickListener(entity));
		cardText.setAlignment(Align.center);
		cardText.setWrap(true);
		
		cardCells[cellIndex].setActor(cardText);
	}
	
	private synchronized void click(CardClickListener listener, Entity entity) {
		
		comp = mapper.get(entity);
		if (comp.zone.playerNumber() == currPlayer) { // click came from active player
			
			listener.selected = !listener.selected; // toggle selected state of card
			
			if (listener.selected) { // card been selected
				
				if (primaryCard.isPresent() && primaryCard.get() != entity) { // selected card is not previous primary card
					secondaryCards.add(primaryCard.get()); // add old primary card to set of secondary cards
				}
				
				primaryCard = Optional.of(entity); // set new primary card as selected card
				
				queueEvent(new CardSelectEvent(entity));
				GameClient.log(this, "Card " + cardLogString(entity) + " selected.");
				
			} else { // card been unselected
				
				// remove card from selection set
				secondaryCards.remove(entity);
				if (primaryCard.isPresent() && primaryCard.get() == entity) {
					primaryCard = Optional.absent();
				}
				
				GameClient.log(this, "Card " + cardLogString(entity) + " unselected.");
			}
			
			logSelected();
			
		} else if (primaryCard.isPresent()) {
			
			queueEvent(new CardInteractEvent(primaryCard.get(), entity, secondaryCards.toArray(new Entity[0])));
		}
	}
	
	@Subscribe // TODO synchronized loswerden (Zugriff auf comp und mapper durch parallele Threads verhindern)
	public synchronized void on(CardAttackBaseEvent ev) {
		
		Entity attacked = ev.destCard();
		if (mapper.has(attacked)) {
			
			updateCard(attacked);
			primaryCard = Optional.absent();
			secondaryCards.clear();
			unselectAll();
		}
	}
	
	@Subscribe
	public synchronized void on(CardDestroyLpEvent ev) {
		
		Entity entity = ev.attacked();
		if (mapper.has(entity)) {
			
			comp = mapper.get(entity);
			int cellIndex = zones.get(comp.zone).indexOf(entity);
			
			cardCells[cellIndex].clearActor();
			comp.zone = UiZones.yardZone((currPlayer + 1) % 2);
			updateCard(entity);
		}
	}
	
	private void unselectAll() {
		
		for (Cell cell : cardCells) {
			if (cell.getActor() != null) {
				((CardClickListener) cell.getActor().getListeners().get(0)).selected = false;
			}
		}
	}
	
	private void logSelected() {
		
		String logString = "Selected cards: ";
		logString += primaryCard.isPresent() ? cardLogString(primaryCard.get()) + " " : "";
		for (Entity otherCard : secondaryCards) {
			logString += cardLogString(otherCard) + " ";
		}
		GameClient.log(this, logString);
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
	public synchronized void dispose() {
		super.dispose();
		
		cardCells = null;
		table = null;
		stage = null;
		
		font = null;
		
		zones.clear();
		zones = null;
		
		currPlayer = 0;
		
		primaryCard = null;
		secondaryCards.clear();
		secondaryCards = null;
		
		zoneIndex = 0;
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
			Integer retVal = cellIds.get(entity);
			return retVal == null ? -1 : retVal;
		}
	}
	
	class CardClickListener extends ClickListener {
		
		Entity entity;
		boolean selected;
		
		public CardClickListener(Entity entity) {
			this.entity = entity;
		}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			
			if (button == Input.Buttons.LEFT) {
				click(this, entity);
			}
			return false;
		}
	}
}
