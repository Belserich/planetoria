
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
	
	public EntityBuilder card(Cards template, Zones zone) {
		
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
				
			default:
				
				GameClient.error(this, "* Entity creation. Tried to create unregistered entity: " + template + ".");
				break;
		}
		
		suppliers.add(Card::new);
		zone(zone);
		return this;
	}
	
	public EntityBuilder zone(Zones template) {
		
		suppliers.add(() -> new Zone(template));
		return this;
	}
	
	public EntityBuilder field(int index) {
		
		suppliers.add(() -> new Field(index));
		return this;
	}
	
	public EntityBuilder attacker() {
		
		suppliers.add(Selectable::new);
		suppliers.add(Attacker::new);
		return this;
	}
	
	public EntityBuilder attackable() {
		
		suppliers.add(Touchable::new);
		suppliers.add(Attackable::new);
		return this;
	}
	
	public EntityBuilder playable() {
		
		suppliers.add(Playable::new);
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
