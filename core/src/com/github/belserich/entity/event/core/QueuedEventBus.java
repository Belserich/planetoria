package com.github.belserich.entity.event.core;

import com.badlogic.gdx.utils.Array;
import com.google.common.eventbus.EventBus;

public class QueuedEventBus extends EventBus {
	
	private Array<EntityEvent> queue;
	
	public QueuedEventBus() {
		queue = new Array<EntityEvent>();
	}
	
	public void queue(EntityEvent ev) {
		queue.add(ev);
	}
	
	public void dispatch() {
		EntityEvent[] events = queue.toArray(EntityEvent.class);
		for (EntityEvent ev : events) {
			post(ev);
			queue.removeIndex(0);
		}
	}
	
	public void clear() {
		queue.clear();
	}
}
