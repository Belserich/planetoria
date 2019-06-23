package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.Zones;

public class OwnedByZone implements Component {
	
	public int id;
	
	public OwnedByZone(int id) {
		this.id = id;
	}
	
	public OwnedByZone(Zones zone) {
		this.id = zone.ordinal();
	}
}
