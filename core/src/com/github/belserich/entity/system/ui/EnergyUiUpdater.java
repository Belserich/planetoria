package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.core.EntityActor;
import com.github.belserich.ui.core.UiService;

public class EnergyUiUpdater extends EntityActor {
	
	private static final UiService service = Services.getUiService();
	
	@Override
	public Family actors() {
		return Family.all(
				PlayerId.class,
				Ep.class,
				Ep.Update.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		PlayerId pid = actor.getComponent(PlayerId.class);
		Ep ec = actor.getComponent(Ep.class);
		
		service.setPlayerEnergy(pid.val, ec.val, ec.def);
		actor.remove(Ep.Update.class);
	}
}
