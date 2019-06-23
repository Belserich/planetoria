package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;

public class CardPlaySystem extends EntitySystem {
	
	private Family selectedCards;
	
	public CardPlaySystem() {
		
		super(Family.all(
				FieldId.class,
				Occupiable.class,
				Touchable.Touched.class
		).exclude(
				CardId.class
		).get());
		
		selectedCards = Family.all(
				OwnedByField.class,
				CardId.class,
				Playable.class,
				Selectable.Selected.class
		).get();
	}
	
	@Override
	public void update(Entity field) {
		
		ImmutableArray<Entity> selectedCardList = super.getEngine().getEntitiesFor(selectedCards);
		if (selectedCardList.size() > 0) {
			
			Entity card = selectedCardList.first();
			
			FieldId fid = field.getComponent(FieldId.class);
			OwnedByField fc = card.getComponent(OwnedByField.class);
			fc.id = fid.id;
			
			card.remove(Playable.class);
			card.add(new Playable.Just());
			
			field.remove(Occupiable.class);
			field.add(new Occupiable.Just());
		}
	}
}
