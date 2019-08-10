package com.github.belserich.entity.system.ui;

import com.badlogic.ashley.core.Family;
import com.github.belserich.Services;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.Name;
import com.github.belserich.entity.component.PlayerId;
import com.github.belserich.entity.component.Ui;
import com.github.belserich.entity.core.EntityInteractor;
import com.github.belserich.ui.core.UiService;

public class EnergyUiUpdater extends EntityInteractor {
	
	private static final UiService service = Services.getUiService();
	
	@Override
	protected Family actors() {
		return Family.all(
				Ui.class,
				Ep.class,
				Name.class
		).get();
	}
	
	@Override
	protected Family iactors() {
		return Family.all(
				PlayerId.class,
				Ep.class,
				Ep.Update.class
		).get();
	}

//	@Override
//	public void entityAdded(Entity actor) {
//
//		PlayerId pid = actor.getComponent(PlayerId.class);
//		Ep ec = actor.getComponent(Ep.class);
//
//		service.setPlayerEnergy(pid.val, ec.val, ec.def);
//		actor.remove(Ep.Update.class);
//	}
}
