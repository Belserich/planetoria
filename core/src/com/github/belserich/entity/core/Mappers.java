package com.github.belserich.entity.core;

import com.badlogic.ashley.core.ComponentMapper;
import com.github.belserich.entity.component.Zone;

public class Mappers {
	
	public static final ComponentMapper<Zone> zoneParent = ComponentMapper.getFor(Zone.class);
}
