package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.asset.UiZones;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.ZoneParent;
import com.github.belserich.entity.core.EventSystem;
import com.github.belserich.entity.core.Mappers;

public class ZoneParentSystem extends EventSystem {
	
	public ZoneParentSystem() {
		super(Family.all(
				ZoneParent.class,
				Lp.Changed.class
				).get(),
				ZoneParent.Changed.class);
	}
	
	@Override
	public void update(Entity entity) {
		
		ZoneParent zc = Mappers.zoneParent.get(entity);
		
		UiZones last = zc.zone;
		UiZones now = UiZones.yardZone(zc.zone.playerNumber());
		
		zc.zone = now;
		
		GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
		entity.add(new ZoneParent.Changed(last, now));
	}
}
