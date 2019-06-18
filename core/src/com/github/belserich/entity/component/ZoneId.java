package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.Zones;

public class ZoneId implements Component {
	
	public int id;
	
	public ZoneId(int id) {
		this.id = id;
	}
	
	public ZoneId(Zones zone) {
		this.id = zone.ordinal();
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
