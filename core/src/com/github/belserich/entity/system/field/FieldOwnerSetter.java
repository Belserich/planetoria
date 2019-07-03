package com.github.belserich.entity.system.field;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Occupiable;
import com.github.belserich.entity.component.OwnedByField;
import com.github.belserich.entity.component.OwnedByZone;
import com.github.belserich.entity.core.EntityInteractorSystem;

import java.util.Iterator;

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
	public void interact(Entity actor, Iterator<Entity> selection) {
		
		OwnedByZone soc = actor.getComponent(OwnedByZone.class);
		
		while (selection.hasNext()) {
			
			Entity field = selection.next();
			OwnedByZone oc = field.getComponent(OwnedByZone.class);
			
			if (soc.id == oc.id) {
				
				FieldId fc = field.getComponent(FieldId.class);
				
				field.remove(Occupiable.class);
				field.add(new Occupiable.Just());
				
				actor.remove(OwnedByField.Request.class);
				actor.add(new OwnedByField(fc.id));
				
				break;
			}
		}
	}
}
