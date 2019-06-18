package com.github.belserich.ui.core;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

import java.util.LinkedList;
import java.util.List;

public class ZoneActor extends HorizontalGroup {
	
	private final int id, cap;
	private List<FieldActor> fields;
	
	/**
	 * Creates a logical zone containing and maintaining a cap of field actors and their cards.
	 *
	 * @param cap cap (exact count of field actors in this zone)
	 */
	public ZoneActor(int id, int cap) {
		
		if (cap < 1) {
			throw new IllegalArgumentException("Invalid zone cap (" + cap + ")");
		}
		
		this.id = id;
		this.cap = cap;
		
		super.space(10);
		
		fields = new LinkedList<>();
		for (int i = 0; i < cap; i++) {
			FieldActor actor = new FieldActor();
			super.addActor(actor);
			fields.add(actor);
		}
	}
	
	/**
	 * Tries adding the specified card actor to this zone. Fails if the zone is fully occupied.
	 *
	 * @param card card actor
	 * @return whether the operation was successful
	 */
	public boolean addCardActor(CardActor card) {
		for (FieldActor fa : fields) {
			if (fa.setCardIfEmpty(card)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Attempts adding a card actor to the field at the specified index.
	 *
	 * @param index field index
	 * @param card  specified ui actor
	 * @return whether the operation was successful
	 */
	public boolean addCardActor(int index, CardActor card) {
		
		FieldActor field = getFieldActor(index);
		if (field != null && !field.hasCard()) {
			field.setCard(card);
			return true;
		}
		return false;
	}
	
	/**
	 * Tries returning the field ui instance at a specified index.
	 *
	 * @param fieldId the index
	 * @return field ui at {@code index} or null if index out of bounds
	 */
	public FieldActor getFieldActor(int fieldId) {
		if (fieldId >= 0 && fieldId < cap()) {
			return fields.get(fieldId);
		}
		return null;
	}
	
	/**
	 * @return the zone's set cap
	 */
	public int cap() {
		return cap;
	}
	
	/**
	 * Tries removing the specified card actor from this zone.
	 *
	 * @param card card actor
	 * @return whether the operation was successful
	 */
	public boolean removeCardActor(CardActor card) {
		for (FieldActor fa : fields) {
			if (fa.getCard() == card) {
				fa.removeActor(card);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Tries finding an unoccupied field.
	 *
	 * @return the id of the first found field or -1 if all fields are occupied
	 */
	public int nextFreeFieldId() {
		for (int i = 0; i < cap; i++) {
			if (!fields.get(i).hasCard()) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @return the actual zone's field count
	 */
	public int fieldCount() {
		return fields.size();
	}
	
	/**
	 * @return the zone's id
	 */
	public int id() {
		return id;
	}
}
