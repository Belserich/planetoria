package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.ZoneId;
import com.github.belserich.entity.core.EventSystem;

public class ZoneIdChangedSystem extends EventSystem {
	
	public ZoneIdChangedSystem() {
		super(Family.all(
				ZoneId.class,
				Lp.Changed.class
		).get(), ZoneId.Changed.class);
	}
	
	@Override
	public void update(Entity entity) {
		
		ZoneId zc = entity.getComponent(ZoneId.class);
		
		Zones last = Zones.values()[zc.id];
		Zones now = Zones.yardZone(Zones.values()[zc.id].playerNumber());
		
		zc.id = now.ordinal();
		
		GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
		entity.add(new ZoneId.Changed(last, 0, now, 0));
	}
}
