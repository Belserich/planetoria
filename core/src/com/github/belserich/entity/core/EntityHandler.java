package com.github.belserich.entity.core;

public interface EntityHandler {
	
	int NONE = 0;
	int ALL = ~NONE;
	
	int ENTITY_ADDED = 1;
	int ENTITY_REMOVED = 2;
	int ENTITY_UPDATE = 4;
}
