package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.*;
import com.github.belserich.entity.core.EAS;
import com.github.belserich.entity.core.EIS;
import com.github.belserich.ui.core.UiService;

import java.util.Iterator;

public class FieldUiSystem extends EIS {
	
	private static final UiService service = Services.getUiService();
	
	public FieldUiSystem() {
		
		super(Family.all(
				OwnedByZone.class,
				OwnedByField.Request.class
				).get(), Family.all(
				FieldId.class,
				OwnedByZone.class,
				Occupiable.class
				).get(),
				new Creator(),
				new EnergyUpdater());
	}
	
	@Override
	public void update(Entity card, Iterator<Entity> selection) {
		
		OwnedByZone soc = card.getComponent(OwnedByZone.class);
		
		while (selection.hasNext()) {
			
			Entity field = selection.next();
			OwnedByZone oc = field.getComponent(OwnedByZone.class);
			
			if (soc.id == oc.id) {
				
				FieldId fc = field.getComponent(FieldId.class);
				
				field.remove(Occupiable.class);
				field.add(new Occupiable.Just());
				
				card.remove(OwnedByField.Request.class);
				card.add(new OwnedByField(fc.id));
				
				selection.remove();
				break;
			}
		}
	}
	
	private static class Creator extends EAS {
		
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
	
	private static class EnergyUpdater extends EAS {
		
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
