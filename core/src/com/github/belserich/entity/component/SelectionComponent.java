package com.github.belserich.entity.component;

import com.badlogic.ashley.core.Component;
import com.github.belserich.asset.UiZones;

public class SelectionComponent implements Component {
	
	public UiZones type;
	
	public SelectionComponent(UiZones type) {
		this.type = type;
	}
}
