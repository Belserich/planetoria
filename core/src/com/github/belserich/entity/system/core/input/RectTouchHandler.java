package com.github.belserich.entity.system.core.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.github.belserich.Services;
import com.github.belserich.entity.component.Rect;
import com.github.belserich.entity.component.Touchable;
import com.github.belserich.entity.core.EntityActor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RectTouchHandler extends EntityActor {
	
	private final Set<Entity> processed;
	
	private Vector3 wVec;
	
	public RectTouchHandler() {
		processed = Collections.synchronizedSet(new HashSet<>());
	}
	
	@Override
	protected Family actors() {
		return Family.all(
				Rect.class,
				Touchable.class
		).get();
	}
	
	@Override
	public void updateEntities() {
		
		if (Gdx.input.isTouched(Input.Buttons.LEFT)) {
			
			Camera cam = Services.getBoardCamera();
			wVec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			wVec = cam.unproject(wVec);
			
			super.updateEntities();
		} else if (!processed.isEmpty()) {
			
			Iterator<Entity> it = processed.iterator();
			while (it.hasNext()) {
				it.next().remove(Touchable.Touched.class);
				it.remove();
			}
		}
	}
	
	@Override
	public void entityUpdate(Entity actor) {
		
		Rect rc = actor.getComponent(Rect.class);
		
		if (wVec.x < rc.x || wVec.x > rc.x + rc.width || wVec.y < rc.y || wVec.y > rc.y + rc.height) {
			
			if (processed.contains(actor)) {
				actor.remove(Touchable.Touched.class);
				processed.remove(actor);
			}
			return;
		}
		
		if (!processed.contains(actor)) {
			
			actor.add(new Touchable.Touched());
			processed.add(actor);
		}
	}
}
