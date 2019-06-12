package com.github.belserich.ui;

import com.badlogic.ashley.core.Entity;

public interface UiService {
	
	void addCard(Entity card);
	
	void removeCard(Entity card);
	
	void changeZone(Entity card);
	
	void updateCard(Entity entity);
	
	void addTouchNotifier(Entity entity);
	
	void removeTouchNotifier(Entity entity);
	
	Entity[] touchedEntities();
	
	void update(float delta);
}
