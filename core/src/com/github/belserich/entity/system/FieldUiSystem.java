package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Occupiable;
import com.github.belserich.entity.component.OwnedByField;
import com.github.belserich.entity.component.OwnedByZone;
import com.github.belserich.entity.core.EntitySystem;
import com.github.belserich.ui.core.UiService;

public class FieldUiSystem extends EntitySystem {
	
	private UiService service;
	
	private Family freeFields;
	
	private Creator creatorSys;
	
	public FieldUiSystem() {
		
		super(Family.all(
				OwnedByField.Request.class,
				OwnedByZone.class
		).get());
		
		service = Services.getUiService();
		
		freeFields = Family.all(
				FieldId.class,
				OwnedByZone.class,
				Occupiable.class
		).get();
		
		creatorSys = new Creator();
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		super.addedToEngine(engine);
		engine.addSystem(creatorSys);
	}
	
	@Override
	public void removedFromEngine(Engine engine) {
		super.removedFromEngine(engine);
		engine.removeSystem(creatorSys);
	}
	
	@Override
	public void update(Entity card) {
		
		ImmutableArray<Entity> freeFieldList = super.getEngine().getEntitiesFor(freeFields);
		if (freeFieldList.size() > 0) {
			
			OwnedByZone soc = card.getComponent(OwnedByZone.class);
			for (Entity field : freeFieldList) {
				
				OwnedByZone oc = field.getComponent(OwnedByZone.class);
				if (freeFields.matches(field) && oc.id == soc.id) {
					
					FieldId fc = field.getComponent(FieldId.class);
					
					field.remove(Occupiable.class);
					field.add(new Occupiable.Just());
					
					card.remove(OwnedByField.Request.class);
					card.add(new OwnedByField(fc.id));
					
					break;
				}
			}
		}
	}
	
	private class Creator extends EntitySystem {
		
		public Creator() {
			super(Family.all(
					OwnedByZone.class,
					FieldId.Request.class
			).get());
		}
		
		@Override
		public void update(Entity entity) {
			
			OwnedByZone oc = entity.getComponent(OwnedByZone.class);
			int fieldId = service.addField(oc.id);
			
			if (fieldId != -1) {
				
				GameClient.log(this, "! Field creation. Creating field %d.", fieldId);
				entity.remove(FieldId.Request.class);
				entity.add(new FieldId(fieldId));
			}
		}
	}
}
