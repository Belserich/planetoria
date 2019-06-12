package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.Zones;

public class Zone implements Component {
	
	public Zones zone;
	
	public Zone(Zones zone) {
		this.zone = zone;
	}
	
	public static class Changed implements Component {
		
		public Zones last, now;
		public int lastField, nowField;
		
		public Changed(Zones last, int lastField, Zones now, int nowField) {
			this.last = last;
			this.lastField = lastField;
			this.now = now;
			this.nowField = nowField;
		}
	}
}
