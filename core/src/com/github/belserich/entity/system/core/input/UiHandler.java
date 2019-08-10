package com.github.belserich.entity.system.core.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.belserich.GameClient;
import com.github.belserich.entity.component.Scene;
import com.github.belserich.entity.component.Switch;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.component.Visible;
import com.github.belserich.entity.core.EntityInteractor;

public class UiHandler extends EntityInteractor {
	
	@Override
	protected Family actors() {
		return Family.all(
				Switch.class,
				Touchable.Released.class
		).get();
	}
	
	@Override
	protected Family iactors() {
		return Family.all(
				Scene.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor, ImmutableArray<Entity> selection) {
		
		Switch uc = actor.getComponent(Switch.class);
		
		for (Entity other : selection) {
			
			Scene sc = other.getComponent(Scene.class);
			if (uc.toId == sc.id) {
				other.add(new Visible());
			} else other.remove(Visible.class);
		}
		
		GameClient.log(this, "! Ui click. Switching to scene %d.", uc.toId);
	}
}
