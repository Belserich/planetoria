package com.github.belserich.entity.core;

import com.badlogic.ashley.core.ComponentMapper;
import com.github.belserich.entity.component.Ui;
import com.github.belserich.entity.component.ZoneParent;

public class Mappers {
	
	public static final ComponentMapper<ZoneParent> zoneParent = ComponentMapper.getFor(ZoneParent.class);
	public static final ComponentMapper<ZoneParent.Changed> zoneParentChanged = ComponentMapper.getFor(ZoneParent.Changed.class);
	public static final ComponentMapper<Ui.Card> uiCard = ComponentMapper.getFor(Ui.Card.class);
}
