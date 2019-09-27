package com.github.belserich.entity.system.gfx;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.belserich.entity.component.Bounds;
import com.github.belserich.entity.component.Draw;
import com.github.belserich.entity.component.FieldId;
import com.github.belserich.entity.component.Visible;
import com.github.belserich.entity.core.EntityActor;

public class FieldRenderer extends EntityActor {
	
	private final Sprite fieldSprite;
	
	public FieldRenderer() {
		super();
		
		fieldSprite = new Sprite(new Texture(Gdx.files.internal("ui_field.png")));
	}
	
	@Override
	protected Family actors() {
		return Family.all(
				FieldId.class,
				Bounds.class,
				Visible.class
		).get();
	}
	
	@Override
	public void entityAdded(Entity actor) {
		
		Bounds bc = actor.getComponent(Bounds.class);
		
		Sprite sprite = new Sprite(fieldSprite);
		
		sprite.setSize(bc.width, bc.height);
		sprite.setCenter(bc.x, bc.y);
		
		actor.add(new Draw(new Sprite[]{sprite}));
	}
}
