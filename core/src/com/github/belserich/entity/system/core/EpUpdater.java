package com.github.belserich.entity.system.core;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.entity.component.Ep;
import com.github.belserich.entity.component.Name;
import com.github.belserich.entity.component.Turnable;
import com.github.belserich.entity.component.Ui;
import com.github.belserich.entity.core.EntityInteractor;

public class EpUpdater extends EntityInteractor {
	
	@Override
	protected Family actors() {
		return Family.all(
				Ui.class,
				Turnable.HasTurn.class,
				Ep.Updatable.class,
				Name.class
		).get();
	}
	
	@Override
	public Family iactors() {
		return Family.all(
				Ep.class,
				Ep.Update.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		Name nc = actor.getComponent(Name.class);
		Ep ec = selection.first().getComponent(Ep.class);
		
		nc.name = ec.val + " / " + ec.def;
	}
}
