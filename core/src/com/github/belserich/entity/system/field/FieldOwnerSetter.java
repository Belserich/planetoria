package com.github.belserich.entity.system.field;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntityInteractor;
import java8.util.Comparators;
import java8.util.J8Arrays;
import java8.util.Optional;

public class FieldOwnerSetter extends EntityInteractor {
	
	@Override
	public Family actors() {
		return Family.all(
				OwnedByZone.class,
				OwnedByField.Request.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				FieldId.class,
				OwnedByZone.class,
				Occupiable.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		OwnedByZone soc = actor.getComponent(OwnedByZone.class);
		
		Optional<Entity> fieldOpt = J8Arrays.stream(selection.toArray())
				.filter(f -> f.getComponent(OwnedByZone.class).id == soc.id)
				.sorted(Comparators.comparingInt(e -> e.getComponent(FieldId.class).id))
				.findFirst();
		
		if (fieldOpt.isPresent()) {
			
			Entity field = fieldOpt.get();
			
			field.remove(Occupiable.class);
			field.add(new Occupiable.Just());
			
			actor.remove(OwnedByField.Request.class);
			actor.add(new OwnedByField(field.getComponent(FieldId.class).id));
			
			if (ComponentMapper.getFor(Bounds.class).has(field)) {
				
				Bounds bc = field.getComponent(Bounds.class);
				actor.add(new Bounds(bc.x, bc.y, bc.width, bc.height));
			}
			
			if (ComponentMapper.getFor(Rect.class).has(field)) {
				
				Rect rc = field.getComponent(Rect.class);
				actor.add(new Rect(rc.x, rc.y, rc.width, rc.height));
			}
			
		} else {
			GameClient.error(this, "Couldn't find field owner for zone " + Zones.values()[soc.id] + "!");
		}
	}
}
