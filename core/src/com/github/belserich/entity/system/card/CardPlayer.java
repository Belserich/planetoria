package com.github.belserich.entity.system.card;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityInteractor;

public class CardPlayer extends EntityInteractor {
	
	@Override
	public Family actors() {
		return Family.all(
				FieldId.class,
				Occupiable.class,
				Touchable.Touched.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				CardId.class,
				Playable.class,
				Selectable.Selected.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		if (selection.size() <= 0) {
			return;
		}
		
		Entity card = selection.first();
		
		FieldId fid = actor.getComponent(FieldId.class);
		CardId cid = card.getComponent(CardId.class);
		
		OwnedByField fc = card.getComponent(OwnedByField.class);
		
		GameClient.log(this, "! Card play. Playing card %d on field %d", cid.val, fid.id);
		
		fc.id = fid.id;
		
		card.remove(Playable.class);
		card.add(new Playable.Just());
		
		actor.remove(Touchable.Touched.class);
		actor.remove(Occupiable.class);
		actor.add(new Occupiable.Just());
	}
}
