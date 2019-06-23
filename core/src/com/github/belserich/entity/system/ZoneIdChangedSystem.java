package com.github.belserich.entity.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.Lp;
import com.github.belserich.entity.component.OwnedByZone;
import com.github.belserich.entity.core.EventSystem;

public class ZoneIdChangedSystem extends EventSystem {
	
	public ZoneIdChangedSystem() {
		super(Family.all(
				OwnedByZone.class,
				Lp.Changed.class
		).get(), OwnedByZone.Changed.class);
	}
	
	@Override
	public void update(Entity entity) {
		
		OwnedByZone zc = entity.getComponent(OwnedByZone.class);
		
		Zones last = Zones.values()[zc.id];
		Zones now = Zones.yardZone(Zones.values()[zc.id].playerNumber());
		
		zc.id = now.ordinal();
		
		GameClient.log(this, "! Zone change. Old: " + last + " New: " + now);
		entity.add(new OwnedByZone.Changed(last, 0, now, 0));
	}
}
