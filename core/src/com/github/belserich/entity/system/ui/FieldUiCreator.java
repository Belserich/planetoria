package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.Services;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.OwnedByZone;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.UiService;

public class FieldUiCreator extends EntityActor {
	
	private static final UiService service = Services.getUiService();
	
	@Override
	public Family actors() {
		return Family.all(
				FieldId.Request.class,
				OwnedByZone.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		OwnedByZone oc = actor.getComponent(OwnedByZone.class);
		int fieldId = service.addField(oc.id);
		
		if (fieldId != -1) {
			
			GameClient.log(this, "! Field creation. Creating field %d.", fieldId);
			actor.remove(FieldId.Request.class);
			actor.add(new FieldId(fieldId));
		}
	}
}
