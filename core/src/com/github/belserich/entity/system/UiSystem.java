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
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.github.belserich.GameClient;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.AttackComponent;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.EntityEvSystem;
import com.github.belserich.entity.event.attack.DestroyLp;
import com.github.belserich.entity.event.base.AttackBase;
import com.github.belserich.entity.event.core.EventQueue;
import com.github.belserich.entity.event.interact.Interact;
import com.github.belserich.entity.event.select.Select;
import com.google.common.base.Optional;
import com.google.common.eventbus.Subscribe;

import java.util.*;

public class UiSystem extends EntityEvSystem<UiComponent> {
	
	public static final int[] CARDS_PER_ROW = new int[]{8, 7, 7, 8};
	
	private static final float CARD_HEIGHT_FACTOR = 1.421f;
	private static final float MIN_CARD_WIDTH = 75f * 1.5f;
	
	private static final int DECK_CARD_MAX = 8;
	
	private Stage stage;
	private VerticalGroup group;
	
	private Table mainTable;
	private Label deckToggle;
	private Cell[] mainCells;
	
	private Table deckTable;
	private Cell[] deckCells;
	
	private BitmapFont font1;
	private BitmapFont font2;
	
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
		font1 = fontGen.generateFont(param);
		
		param.size = 30;
		font2 = fontGen.generateFont(param);
		
		fontGen.dispose();
		
		int cardCount = 0;
		for (int i = 0; i < CARDS_PER_ROW.length; i++) {
			cardCount += CARDS_PER_ROW[i];
		}
		
		mainCells = new Cell[cardCount];
		deckCells = new Cell[DECK_CARD_MAX];
		
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
		
		zones.put(UiZones.P1_DECK, new ZoneMeta(genZoneIndices(DECK_CARD_MAX)));
		zones.put(UiZones.P0_DECK, new ZoneMeta(genZoneIndices(DECK_CARD_MAX)));
	}
	
	private void createUi() {
		
		float cardWidth = MIN_CARD_WIDTH;
		float cardHeight = MIN_CARD_WIDTH * CARD_HEIGHT_FACTOR;
		
		stage.setDebugAll(true);
		
		group = new VerticalGroup();
		group.setFillParent(true);
		stage.addActor(group);
		
		mainTable = new Table();
		mainTable.padTop(70);
		mainTable.padBottom(20);
		
		int cardCount = 0;
		for (int row = 0; row < CARDS_PER_ROW.length; row++) {
			for (int col = 0; col < CARDS_PER_ROW[row]; col++, cardCount++) {
				mainCells[cardCount] = mainTable.add().width(cardWidth).height(cardHeight).pad(10);
			}
			mainTable.row();
		}
		
		deckToggle = new Label("Hand", new Label.LabelStyle(font2, Color.BLACK));
		deckToggle.addListener(new ClickListener() {
			
			private boolean toggled;
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				toggled = !toggled;
				if (toggled) {
					setDeckActivity();
				} else setMainActivity();
			}
		});
		
		deckTable = new Table();
		deckTable.pad(70);
		
		for (int index = 0; index < DECK_CARD_MAX; index++) {
			deckCells[index] = deckTable.add().width(cardWidth).height(cardHeight).pad(10);
			if (index % 8 == 0 && index != 0) {
				deckTable.row();
			}
		}
		
		setMainActivity();
	}
	
	private void setDeckActivity() {
		
		group.clearChildren();
		group.addActor(deckTable);
		group.addActor(deckToggle);
	}
	
	private void setMainActivity() {
		
		group.clearChildren();
		group.addActor(mainTable);
		group.addActor(deckToggle);
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
	
	private synchronized void click(MainCardClickListener listener, Entity entity) {
		
		comp = mapper.get(entity);
		if (comp.zone.playerNumber() == currPlayer) { // click came from active player
			
			listener.selected = !listener.selected; // toggle selected state of card
			
			if (listener.selected) { // card been selected
				
				if (primaryCard.isPresent() && primaryCard.get() != entity) { // selected card is not previous primary card
					secondaryCards.add(primaryCard.get()); // add old primary card to set of secondary cards
				}
				
				primaryCard = Optional.of(entity); // set new primary card as selected card
				
				queueEvent(new Select(entity));
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
			
			queueEvent(new Interact(primaryCard.get(), entity, secondaryCards.toArray(new Entity[0])));
		}
	}
	
	@Subscribe
	public synchronized void on(DestroyLp ev) {
		
		Entity entity = ev.attacked();
		if (mapper.has(entity)) {
			
			comp = mapper.get(entity);
			int cellIndex = zones.get(comp.zone).indexOf(entity);
			
			mainCells[cellIndex].clearActor();
			comp.zone = UiZones.yardZone((currPlayer + 1) % 2);
			updateCard(entity);
		}
	}
	
	@Subscribe // TODO synchronized loswerden (Zugriff auf comp und mapper durch parallele Threads verhindern)
	public synchronized void on(AttackBase ev) {
		
		Entity attacked = ev.destCard();
		if (mapper.has(attacked)) {
			
			updateCard(attacked);
			primaryCard = Optional.absent();
			secondaryCards.clear();
			unselectAll();
		}
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
		
		Label cardText = new Label(cardString, new Label.LabelStyle(font1, Color.BLACK));
		cardText.addListener(new MainCardClickListener(entity));
		cardText.setAlignment(Align.center);
		cardText.setWrap(true);
		
		mainCells[cellIndex].setActor(cardText);
	}
	
	private void unselectAll() {
		
		for (Cell cell : mainCells) {
			if (cell.getActor() != null) {
				((MainCardClickListener) cell.getActor().getListeners().get(0)).selected = false;
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
		
		mainCells = null;
		mainTable = null;
		stage = null;
		
		font1 = null;
		
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
	
	class MainCardClickListener extends ClickListener {
		
		Entity entity;
		boolean selected;
		
		public MainCardClickListener(Entity entity) {
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
