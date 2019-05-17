package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.LifeComponent;
import com.github.belserich.entity.component.ShieldComponent;
import com.github.belserich.entity.component.UiComponent;
import com.github.belserich.entity.core.BaseEntitySystem;

public class UiSystem extends BaseEntitySystem {
	
	public UiSystem() {
		super(Family.all(
				UiComponent.Card.class
		).one(
				ShieldComponent.Broke.class,
				LifeComponent.Changed.class
		).get(), 10);
	}
	
	@Override
	public void update(Entity entity) {
		
		UiComponent.Card uic = entity.getComponent(UiComponent.Card.class);
		
		ShieldComponent sc = entity.getComponent(ShieldComponent.class);
		LifeComponent lc = entity.getComponent(LifeComponent.class);
		
		if (sc != null) {
			uic.ui.setSp(String.valueOf(sc.pts));
		} else uic.ui.setSp(String.valueOf(0f));
		
		if (lc != null) {
			uic.ui.setLp(String.valueOf(lc.pts));
		} else uic.ui.setLp(String.valueOf(0f));
	}
}
