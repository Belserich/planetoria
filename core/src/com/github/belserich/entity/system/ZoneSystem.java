package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.entity.core.Mappers;

public class ZoneSystem extends EventSystem {
	
	private PlayCard playSys;
	
	public ZoneSystem() {
		super(Family.all(
				Zone.class,
				Lp.Changed.class
				).get(),
				Zone.Changed.class);
		
		playSys = new PlayCard();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addSystem(playSys);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeSystem(playSys);
	}
	
	@Override
	public void update(Entity entity) {
		
		Zone zc = Mappers.zoneParent.get(entity);
		
		Zones last = zc.zone;
		Zones now = Zones.yardZone(zc.zone.playerNumber());
		
		zc.zone = now;
		
		GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
		entity.add(new Zone.Changed(last, 0, now, 0));
	}
	
	private class PlayCard extends EntitySystem {
		
		private Family selectionFam;
		
		public PlayCard() {
			
			super(Family.all(
					Field.class,
					Zone.class,
					Touchable.Touched.class
			).get());
			
			selectionFam = Family.all(
					Zone.class,
					Playable.class,
					Selectable.Selected.class
			).get();
		}
		
		@Override
		public void update(Entity field) {
			
			ImmutableArray<Entity> selection = getEngine().getEntitiesFor(selectionFam);
			if (selection.size() > 0) {
				
				Entity card = selection.first();
				
				Zones last = card.getComponent(Zone.class).zone;
				
				Zones now = field.getComponent(Zone.class).zone;
				int nowField = field.getComponent(Field.class).id;
				
				GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
				card.add(new Zone.Changed(last, 0, now, nowField));
				card.remove(Playable.class);
				card.remove(Selectable.Selected.class);
			}
		}
	}
}
