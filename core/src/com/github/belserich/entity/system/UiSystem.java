package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.Sp;
import com.github.belserich.entity.component.Ui;
import com.github.belserich.entity.core.BaseEntitySystem;

public class UiSystem extends BaseEntitySystem {
	
	public UiSystem() {
		super(Family.all(
				Ui.Card.class
		).one(
				Sp.Broke.class,
				Lp.Changed.class
		).get(), 10);
	}
	
	@Override
	public void update(Entity entity) {
		
		Ui.Card uic = entity.getComponent(Ui.Card.class);
		
		Sp sc = entity.getComponent(Sp.class);
		Lp lc = entity.getComponent(Lp.class);
		
		if (sc != null) {
			uic.ui.setSp(String.valueOf(sc.pts));
		} else uic.ui.setSp(String.valueOf(0f));
		
		if (lc != null) {
			uic.ui.setLp(String.valueOf(lc.pts));
		} else uic.ui.setLp(String.valueOf(0f));
	}
}
