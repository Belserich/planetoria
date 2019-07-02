package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EIS;

import java.util.Iterator;

public class CardPlaySystem extends EIS {
	
	public CardPlaySystem() {
		
		super(Family.all(
				FieldId.class,
				Occupiable.class,
				Touchable.Touched.class
		).get(), Family.all(
				CardId.class,
				Playable.class,
				Selectable.Selected.class
		).get());
	}
	
	@Override
	public void entityAdded(Entity field, Iterator<Entity> selection) {
		
		Entity card = selection.next();
		
		FieldId fid = field.getComponent(FieldId.class);
		CardId cid = card.getComponent(CardId.class);
		
		OwnedByField fc = card.getComponent(OwnedByField.class);
		
		GameClient.log(this, "! Card play. Playing card %d on field %d", cid.val, fid.id);
		
		fc.id = fid.id;
		
		card.remove(Playable.class);
		card.add(new Playable.Just());
		
		field.remove(Touchable.Touched.class);
		field.remove(Occupiable.class);
		field.add(new Occupiable.Just());
	}
}
