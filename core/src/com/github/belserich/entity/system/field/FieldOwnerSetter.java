package com.github.belserich.entity.system.field;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Occupiable;
import com.github.belserich.entity.component.OwnedByField;
import com.github.belserich.entity.component.OwnedByZone;
import com.github.belserich.entity.core.EntityInteractorSystem;
import java8.util.Comparators;
import java8.util.J8Arrays;
import java8.util.Optional;

public class FieldOwnerSetter extends EntityInteractorSystem {
	
	public FieldOwnerSetter(int handleBits) {
		super(handleBits);
	}
	
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
	public void interact(Entity actor, ImmutableArray<Entity> selection) {
		
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
		} else {
			GameClient.error(this, "Couldn't set field owner!");
		}
	}
}
