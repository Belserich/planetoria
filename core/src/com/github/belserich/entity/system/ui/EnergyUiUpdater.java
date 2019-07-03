package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.core.EntityActorSystem;
import com.github.belserich.ui.core.UiService;

public class EnergyUiUpdater extends EntityActorSystem {
	
	private static final UiService service = Services.getUiService();
	
	public EnergyUiUpdater(int handleBits) {
		super(handleBits);
	}
	
	@Override
	public Family actors() {
		return Family.all(
				PlayerId.class,
				Ep.class,
				Ep.Update.class
		).get();
	}
	
	@Override
	public void act(Entity actor) {
		
		PlayerId pid = actor.getComponent(PlayerId.class);
		Ep ec = actor.getComponent(Ep.class);
		
		service.setPlayerEnergy(pid.val, ec.val, ec.def);
		actor.remove(Ep.Update.class);
	}
}
