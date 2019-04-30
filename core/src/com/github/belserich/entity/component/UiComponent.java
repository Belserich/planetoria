package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.UiZones;

public class UiComponent implements Component {
	
	public UiZones zone;
	public String displayName;
	public String lpStr, apStr, spStr;
	
	public UiComponent(UiZones zone, String displayName, String lpStr, String apStr, String spStr) {
		this.zone = zone;
		this.displayName = displayName;
		this.lpStr = lpStr;
		this.apStr = apStr;
		this.spStr = spStr;
	}
}
