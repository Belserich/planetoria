package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.entity.core.Mappers;

public class ZoneSystem extends EventSystem {
	
	private PlayCard playSys;
	
	public ZoneSystem() {
		super(Family.all(
				ZoneId.class,
				Lp.Changed.class
				).get(),
				ZoneId.Changed.class);
		
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
		
		ZoneId zc = Mappers.zoneParent.get(entity);
		
		Zones last = Zones.values()[zc.id];
		Zones now = Zones.yardZone(Zones.values()[zc.id].playerNumber());
		
		zc.id = now.ordinal();
		
		GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
		entity.add(new ZoneId.Changed(last, 0, now, 0));
	}
	
	private class PlayCard extends EntitySystem {
		
		private Family selectionFam;
		
		public PlayCard() {
			
			super(Family.all(
					ZoneId.class,
					FieldId.class,
					Touchable.Touched.class
			).exclude(
					CardHandle.class
			).get());
			
			selectionFam = Family.all(
					ZoneId.class,
					Playable.class,
					Selectable.Selected.class
			).get();
		}
		
		@Override
		public void update(Entity field) {
			
			ImmutableArray<Entity> selection = getEngine().getEntitiesFor(selectionFam);
			if (selection.size() > 0) {
				
				Entity card = selection.first();
				Zones last = Zones.values()[card.getComponent(ZoneId.class).id];
				Zones now = Zones.values()[field.getComponent(ZoneId.class).id];
				int fieldId = field.getComponent(FieldId.class).id;
				
				if (Services.getUiService().isFieldUnoccupied(now.ordinal(), fieldId)) {
					
					GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
					card.add(new ZoneId.Changed(last, 0, now, fieldId));
					card.remove(Playable.class);
					card.remove(Selectable.Selected.class);
				} else GameClient.log(this, "* Zone change. Field already occupied!");
			}
		}
	}
}
