package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;

public class CardPlaySystem extends EntitySystem {
	
	private Family selectedCards;
	
	public CardPlaySystem() {
		
		super(Family.all(
				OwnedByPlayer.class,
				FieldId.class,
				Occupiable.class,
				Touchable.Touched.class
		).get());
		
		selectedCards = Family.all(
				OwnedByPlayer.class,
				OwnedByField.class,
				CardId.class,
				Playable.class,
				Selectable.Selected.class
		).get();
	}
	
	@Override
	public void update(Entity field) {
		
		field.remove(Touchable.Touched.class);
		
		ImmutableArray<Entity> selectedCardList = super.getEngine().getEntitiesFor(selectedCards);
		if (selectedCardList.size() > 0) {
			
			Entity card = selectedCardList.first();
			
			OwnedByPlayer opc1 = field.getComponent(OwnedByPlayer.class);
			OwnedByPlayer opc2 = card.getComponent(OwnedByPlayer.class);
			
			if (opc1.id == opc2.id) {
				
				FieldId fid = field.getComponent(FieldId.class);
				CardId cid = card.getComponent(CardId.class);
				
				GameClient.log(this, "! Card play. Playing card %d on field %d", cid.val, fid.id);
				
				OwnedByField fc = card.getComponent(OwnedByField.class);
				fc.id = fid.id;
				
				card.remove(Playable.class);
				card.add(new Playable.Just());
				
				field.remove(Occupiable.class);
				field.add(new Occupiable.Just());
			}
		}
	}
}
