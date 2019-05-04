package com.github.belserich.asset;

public enum UiZones {
	
	P0_BATTLE(0), P0_REPAIR(0), P0_PLANET(0), P0_MOTHER(0), P0_DECK(0), P0_YARD(0),
	P1_BATTLE(1), P1_REPAIR(1), P1_PLANET(1), P1_MOTHER(1), P1_DECK(1), P1_YARD(1);
	
	private int playerNumber;
	
	UiZones(int playerNumber) {
		this.playerNumber = playerNumber;
	}
	
	public int playerNumber() {
		return playerNumber;
	}
	
	public boolean isDeckZone() {
		return name().equals(P0_DECK.toString()) || name().equals(P1_DECK.toString());
	}
	
	public static UiZones battleZone(int playerNumber) {
		switch (playerNumber) {
			case 0:
				return P0_BATTLE;
			case 1:
				return P1_BATTLE;
			default:
				return null;
		}
	}
	
	public static UiZones yardZone(int playerNumber) {
		switch (playerNumber) {
			case 0:
				return P0_YARD;
			case 1:
				return P1_YARD;
			default:
				return null;
		}
	}
}
