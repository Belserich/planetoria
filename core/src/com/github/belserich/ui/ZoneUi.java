package com.github.belserich.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ZoneUi extends HorizontalGroup {
	
	private List<FieldUi> fields;
	
	public ZoneUi(int cap) {
		super.space(10);
		fields = new LinkedList<>();
		for (int i = 0; i < cap; i++) {
			FieldUi fui = new FieldUi();
			super.addActor(fui);
			fields.add(fui);
		}
	}
	
	public boolean addCardActor(CardUi cardUi) {
		
		Iterator<FieldUi> it = fields.iterator();
		while (it.hasNext()) {
			FieldUi next = it.next();
			if (!next.hasChildren()) {
				next.setActor(cardUi);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Attempts adding a card ui to the field at the specified index.
	 *
	 * @param index  field ui index
	 * @param cardUi the card ui to add
	 */
	public void addCardActor(int index, CardUi cardUi) {
		
		FieldUi field = getFieldActor(index);
		if (field != null) {
			if (!field.hasChildren()) {
				field.setActor(cardUi);
			} else throw new RuntimeException("Field is already occupied!");
		} else throw new RuntimeException("Invalid index " + index + "!");
	}
	
	/**
	 * Tries returning the field ui instance at a specified index.
	 *
	 * @param index the index
	 * @return field ui at {@code index} or field ui at 0 if index out of bounds
	 */
	public FieldUi getFieldActor(int index) {
		if (index >= 0 && index < fieldCount()) {
			return fields.get(index);
		}
		System.err.println("Invalid field index: " + index + ". Returning field at index 0!");
		return fields.get(0);
	}
	
	public boolean removeCardActor(Actor actor) {
		
		for (FieldUi fieldUi : fields) {
			if (fieldUi.getActor() == actor) {
				fieldUi.removeActor(actor);
				return true;
			}
		}
		return false;
	}
	
	public int fieldCount() {
		return fields.size();
	}
}
