
package com.github.belserich.entity.builder;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.asset.Cards;
import com.github.belserich.asset.Zones;
import com.github.belserich.entity.component.*;
import com.google.common.base.Supplier;

import java.util.LinkedList;
import java.util.List;

public class EntityBuilder {
	
	private List<Supplier<Component>> suppliers;
	
	public EntityBuilder() {
		suppliers = new LinkedList<>();
	}
	
	public Entity build() {
		
		Entity entity = new Entity();
		for (Supplier<Component> supp : suppliers) {
			entity.add(supp.get());
		}
		return entity;
	}
	
	public EntityBuilder card(Cards template, Zones zone, int ownerId) {
		
		switch (template) {
			
			case SPACESHIP_A:
				
				suppliers.add(() -> new Lp(1));
				suppliers.add(() -> new Sp(1));
				suppliers.add(() -> new Ap(1, 2));
				suppliers.add(() -> new Name("Raumschiff A"));
				
				break;
			
			case SPACESHIP_B:
				
				suppliers.add(() -> new Lp(2));
				suppliers.add(() -> new Sp(2));
				suppliers.add(() -> new Ap(2, 2));
				suppliers.add(() -> new Name("Raumschiff B"));
				
				break;
			
			case STRATEGY_1:
				
				suppliers.add(() -> new Name("Strategie 1"));
				
			default:
				
				GameClient.error(this, "* Entity creation. Tried to create unregistered entity: " + template + ".");
				break;
		}
		
		suppliers.add(CardHandle::new);
		zone(zone);
		field(-1);
		
		suppliers.add(Touchable::new);
		suppliers.add(() -> new PlayerOwned(ownerId));
		
		suppliers.add(Covered::new);
		
		return this;
	}
	
	public EntityBuilder zone(Zones template) {
		
		suppliers.add(() -> new ZoneId(template));
		return this;
	}
	
	public EntityBuilder field(int index) {
		
		suppliers.add(Touchable::new);
		suppliers.add(() -> new FieldId(index));
		return this;
	}
	
	public EntityBuilder playable() {
		
		suppliers.add(Touchable::new);
		suppliers.add(Selectable::new);
		suppliers.add(Playable::new);
		return this;
	}
	
	public EntityBuilder player(int playerId) {
		
		suppliers.add(() -> new PlayerId(playerId));
		return this;
	}
	
	public EntityBuilder turn() {
		
		suppliers.add(Turn::new);
		return this;
	}
	
	public EntityBuilder reset() {
		suppliers.clear();
		return this;
	}
	
	@Override
	public void finalize() {
		reset();
	}
}
