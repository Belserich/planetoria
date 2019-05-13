package com.github.belserich.ui;

import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.github.belserich.GameClient;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ZoneUi extends HorizontalGroup {
	
	private List<FieldUi> fields;
	
	public ZoneUi(int cap) {
		super.space(10);
		fields = new LinkedList<FieldUi>();
		for (int i = 0; i < cap; i++) {
			FieldUi fui = new FieldUi();
			super.addActor(fui);
			fields.add(fui);
		}
	}
	
	public boolean tryAddCardUi(CardUi cardUi) {
		
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
	
	public FieldUi getFieldUi(int index) {
		if (index > 0 && index < fieldCount()) {
			return fields.get(index);
		} else GameClient.error(this, "Field index " + index + " out of bounds (max: " + fieldCount() + ")! Returning first field.");
		return fields.get(0);
	}
	
	public int fieldCount() {
		return fields.size();
	}
}
