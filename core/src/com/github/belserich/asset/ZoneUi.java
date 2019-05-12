package com.github.belserich.asset;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ZoneUi extends HorizontalGroup {
	
	private static final float CARD_HEIGHT_FACTOR = 1.421f;
	private static final float MIN_CARD_WIDTH = 75f * 1.5f;
	private static final float MIN_CARD_HEIGHT = CARD_HEIGHT_FACTOR * MIN_CARD_WIDTH;
	
	private List<Container<Label>> fields;
	
	public ZoneUi(int cap) {
		super.space(10);
		fields = new LinkedList<Container<Label>>();
		for (int i = 0; i < cap; i++) {
			Container<Label> fieldUi = createFieldUi();
			super.addActor(fieldUi);
			fields.add(fieldUi);
		}
	}
	
	private Container<Label> createFieldUi() {
		
		Container<Label> cont = new Container<Label>();
		cont.minSize(MIN_CARD_WIDTH, MIN_CARD_HEIGHT);
		cont.setTouchable(Touchable.enabled);
		cont.setDebug(true);
		return cont;
	}
	
	public Container<Label> nextFreeFieldUi() {
		
		Iterator<Container<Label>> it = fields.iterator();
		while (it.hasNext()) {
			Container<Label> next = it.next();
			if (!next.hasChildren()) {
				return next;
			}
		}
		return null;
	}
}
