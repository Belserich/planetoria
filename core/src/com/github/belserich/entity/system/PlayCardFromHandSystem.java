package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;

public class PlayCardFromHandSystem extends EntitySystem {
	
	private Family selectionFam;
	
	public PlayCardFromHandSystem() {
		
		super(Family.all(
				OwnedByZone.class,
				FieldId.class,
				Touchable.Touched.class
		).exclude(
				CardId.class
		).get());
		
		selectionFam = Family.all(
				OwnedByZone.class,
				Playable.class,
				Selectable.Selected.class
		).get();
	}
	
	@Override
	public void update(Entity field) {

//		ImmutableArray<Entity> selection = getEngine().getEntitiesFor(selectionFam);
//		if (selection.size() > 0) {
//
//			Entity card = selection.first();
//			Zones last = Zones.values()[card.getComponent(OwnedByZone.class).id];
//			Zones now = Zones.values()[field.getComponent(OwnedByZone.class).id];
//			int fieldId = field.getComponent(FieldId.class).id;
//
//			if (Services.getUiService().isFieldUnoccupied(now.ordinal(), fieldId)) {
//
//				GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
//				card.add(new OwnedByZone.Changed(last, 0, now, fieldId));
//				card.remove(Playable.class);
//				card.remove(Selectable.Selected.class);
//			} else GameClient.log(this, "* Zone change. Field already occupied!");
//		}
	}
}
