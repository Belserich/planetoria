package com.github.belserich.ui;

import com.badlogic.gdx.utils.IntMap;
import com.github.belserich.asset.Zones;
import com.github.belserich.ui.core.ZoneActor;
import com.github.belserich.ui.core.ZoneStrategy;

public class StdZoneStrategy implements ZoneStrategy {
	
	private IntMap<ZoneActor> zones;
	
	public StdZoneStrategy() {
		zones = new IntMap<>();
		init();
	}
	
	private void init() {
		
		zones.put(Zones.P0_BATTLE.ordinal(), new ZoneActor(Zones.P0_BATTLE.ordinal(), 7));
		zones.put(Zones.P1_BATTLE.ordinal(), new ZoneActor(Zones.P1_BATTLE.ordinal(), 7));
		zones.put(Zones.P0_REPAIR.ordinal(), new ZoneActor(Zones.P0_REPAIR.ordinal(), 5));
		zones.put(Zones.P1_REPAIR.ordinal(), new ZoneActor(Zones.P1_REPAIR.ordinal(), 5));
		
		zones.put(Zones.P0_YARD.ordinal(), new ZoneActor(Zones.P0_YARD.ordinal(), 1));
		zones.put(Zones.P1_YARD.ordinal(), new ZoneActor(Zones.P1_YARD.ordinal(), 1));
		zones.put(Zones.P0_PLANET.ordinal(), new ZoneActor(Zones.P0_PLANET.ordinal(), 1));
		zones.put(Zones.P1_PLANET.ordinal(), new ZoneActor(Zones.P1_PLANET.ordinal(), 1));
		zones.put(Zones.P0_MOTHER.ordinal(), new ZoneActor(Zones.P0_MOTHER.ordinal(), 1));
		zones.put(Zones.P1_MOTHER.ordinal(), new ZoneActor(Zones.P1_MOTHER.ordinal(), 1));
		
		zones.put(Zones.P0_DECK.ordinal(), new ZoneActor(Zones.P0_DECK.ordinal(), 8));
		zones.put(Zones.P1_DECK.ordinal(), new ZoneActor(Zones.P1_DECK.ordinal(), 8));
	}
	
	@Override
	public ZoneActor get(int zoneId) {
		return zones.get(zoneId);
	}
}
