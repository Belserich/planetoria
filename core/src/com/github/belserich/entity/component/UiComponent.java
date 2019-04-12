package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.UiZones;

public class UiComponent implements Component {
	
	public UiZones zone;
	public String displayName;
	public float lp, ap, sp;
	
	public UiComponent(UiZones zone, String displayName, float lp, float ap, float sp) {
		this.zone = zone;
		this.displayName = displayName;
		this.lp = lp;
		this.ap = ap;
		this.sp = sp;
	}
}
