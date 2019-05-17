package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.component.Ui;
import com.github.belserich.entity.component.ZoneParent;
import com.github.belserich.entity.core.BaseEntitySystem;
import com.github.belserich.entity.core.Mappers;
import com.github.belserich.ui.GameUi;
import com.github.belserich.ui.ZoneUi;

public class UiSystem extends BaseEntitySystem {
	
	private GameUi gameUi;
	private ZoneParentChangeSystem zoneParentChangeSys;
	
	public UiSystem(GameUi gameUi) {
		super(Family.all(
				Ui.Card.class
		).one(
				Sp.Broke.class,
				Lp.Changed.class
		).get(), 10);
		this.gameUi = gameUi;
		
		zoneParentChangeSys = new ZoneParentChangeSystem();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addSystem(zoneParentChangeSys);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeSystem(zoneParentChangeSys);
	}
	
	@Override
	public void update(float delta) {
		zoneParentChangeSys.update(delta);
		super.update(delta);
	}
	
	@Override
	public void update(Entity entity) {
		
		Ui.Card uic = entity.getComponent(Ui.Card.class);
		
		Sp sc = entity.getComponent(Sp.class);
		Lp lc = entity.getComponent(Lp.class);
		
		if (sc != null) {
			uic.ui.setSp(String.valueOf(sc.pts));
		} else uic.ui.setSp(String.valueOf(0f));
		
		if (lc != null) {
			uic.ui.setLp(String.valueOf(lc.pts));
		} else uic.ui.setLp(String.valueOf(0f));
	}
	
	private class ZoneParentChangeSystem extends BaseEntitySystem {
		
		public ZoneParentChangeSystem() {
			super(Family.all(
					Ui.Card.class,
					ZoneParent.Changed.class
			).get());
		}
		
		@Override
		public void update(Entity entity) {
			
			Ui.Card uic = Mappers.uiCard.get(entity);
			ZoneParent.Changed zpc = Mappers.zoneParentChanged.get(entity);
			
			ZoneUi lastUi = gameUi.getZoneUi(zpc.last);
			ZoneUi nowUi = gameUi.getZoneUi(zpc.now);
			
			if (lastUi != null) {
				lastUi.removeCardUi(uic.ui);
			}
			
			if (nowUi != null) {
				nowUi.addCardUi(uic.ui);
			}
		}
	}
}
