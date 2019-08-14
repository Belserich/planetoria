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
import com.github.belserich.entity.component.Visible;
import com.github.belserich.entity.core.EntityActor;

public class RectTouchHandler extends EntityActor {
	
	private Vector3 wVec;
	
	@Override
	protected Family actors() {
		return Family.all(
				Rect.class,
				Touchable.class,
				Visible.class
		).get();
	}
	
	@Override
	public void updateEntities() {
		
		Entity[] arr = getEngine().getEntitiesFor(Family.all(
				Rect.class,
				Touchable.class,
				Touchable.Released.class,
				Visible.class)
				.get()).toArray(Entity.class);
		
		int i;
		
		for (i = 0; i < arr.length; i++) {
			arr[i].remove(Touchable.Released.class);
		}
		
		if (Gdx.input.isTouched(Input.Buttons.LEFT)) {
			
			Camera cam = Services.getBoardCamera();
			wVec = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			wVec = cam.unproject(wVec);
			
			super.updateEntities();
			
		} else {
			
			arr = getEngine().getEntitiesFor(Family.all(
					Rect.class,
					Touchable.class,
					Touchable.Touched.class,
					Visible.class
			).get()).toArray(Entity.class);
			
			for (i = 0; i < arr.length; i++) {
				
				arr[i].remove(Touchable.Touched.class);
				arr[i].add(new Touchable.Released());
			}
		}
	}
	
	@Override
	public void entityUpdate(Entity actor) {
		
		Rect rc = actor.getComponent(Rect.class);
		
		if (wVec.x < rc.x || wVec.x > rc.x + rc.width || wVec.y < rc.y || wVec.y > rc.y + rc.height) {
			return;
		}
		
		actor.add(new Touchable.Touched());
	}
}
