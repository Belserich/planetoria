package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EntitySystem;
import com.github.belserich.ui.core.UiService;

public class FieldUiSystem extends EntitySystem {
	
	private Family freeFields;
	
	public FieldUiSystem() {
		
		super(Family.all(
				OwnedByZone.class,
				OwnedByField.Request.class
				).get(),
				new Creator(),
				new EnergyUpdater());
		
		freeFields = Family.all(
				FieldId.class,
				OwnedByZone.class,
				Occupiable.class
		).get();
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
	
	private static class Creator extends EntitySystem {
		
		private UiService service;
		
		public Creator() {
			super(Family.all(
					FieldId.Request.class,
					OwnedByZone.class
			).get());
			
			service = Services.getUiService();
		}
		
		@Override
		public void entityAdded(Entity entity) {
			
			OwnedByZone oc = entity.getComponent(OwnedByZone.class);
			int fieldId = service.addField(oc.id);
			
			if (fieldId != -1) {
				
				GameClient.log(this, "! Field creation. Creating field %d.", fieldId);
				entity.remove(FieldId.Request.class);
				entity.add(new FieldId(fieldId));
			}
		}
	}
	
	private static class EnergyUpdater extends EntitySystem {
		
		private UiService service;
		
		public EnergyUpdater() {
			super(Family.all(
					PlayerId.class,
					Ep.class,
					Ep.Update.class
			).get());
			
			service = Services.getUiService();
		}
		
		@Override
		public void entityAdded(Entity entity) {
			
			PlayerId pid = entity.getComponent(PlayerId.class);
			Ep ec = entity.getComponent(Ep.class);
			
			service.setPlayerEnergy(pid.val, ec.val, ec.def);
			entity.remove(Ep.Update.class);
		}
	}
}
