package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.UiZones;

public class ZoneParent implements Component {
	
	public UiZones zone;
	
	public ZoneParent(UiZones zone) {
		this.zone = zone;
	}
	
	public static class Changed implements Component {
		
		public UiZones last;
		public UiZones now;
		
		public Changed(UiZones last, UiZones now) {
			this.last = last;
			this.now = now;
		}
	}
}
