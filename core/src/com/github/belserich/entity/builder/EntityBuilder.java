
package com.github.belserich.entity.builder;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.github.belserich.GameClient;
import com.github.belserich.asset.CardTypes;
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
		
		suppliers.add(CardId.Request::new);
		suppliers.add(Turnable::new);
		suppliers.add(() -> new OwnedByPlayer(ownerId));
		suppliers.add(OwnedByField.Request::new);
		ownedByZone(zone);
		
		suppliers.add(Touchable::new);
		
		switch (template) {
			
			case SPACESHIP_A:
				
				suppliers.add(() -> new Lp(1));
				suppliers.add(() -> new Sp(1));
				suppliers.add(() -> new Ap(1));
				suppliers.add(() -> new Attacker(2));
				suppliers.add(() -> new Name("Raumschiff A"));
				suppliers.add(() -> new CardType(CardTypes.DEFAULT));
				suppliers.add(() -> new EpConsuming(1));
				
				break;
			
			case SPACESHIP_B:
				
				suppliers.add(() -> new Lp(2));
				suppliers.add(() -> new Sp(2));
				suppliers.add(() -> new Ap(2));
				suppliers.add(() -> new Attacker(2));
				suppliers.add(() -> new Name("Raumschiff B"));
				suppliers.add(() -> new CardType(CardTypes.DEFAULT));
				suppliers.add(() -> new EpConsuming(2));
				
				break;
			
			case STRATEGY_1:
				
				suppliers.add(() -> new Name("Strategie 1"));
				suppliers.add(Modification::new);
				suppliers.add(() -> new EffectText("Erhoeht AP um 2."));
				suppliers.add(() -> new CardType(CardTypes.STRATEGY));
				suppliers.add(() -> new EpConsuming(1));
				
				break;
				
			default:
				
				GameClient.error(this, "* Entity creation. Tried to create unregistered entity: " + template + ".");
				break;
		}
		
		return this;
	}
	
	public EntityBuilder attacker() {
		
		suppliers.add(Selectable::new);
		suppliers.add(() -> new Attacker(2));
		
		return this;
	}
	
	public EntityBuilder attackable() {
		
		suppliers.add(Touchable::new);
		suppliers.add(Attackable::new);
		
		return this;
	}
	
	public EntityBuilder covered() {
		
		suppliers.add(Covered::new);
		
		return this;
	}
	
	public EntityBuilder field(Zones zone, int ownerId) {
		
		suppliers.add(Turnable::new);
		suppliers.add((() -> new OwnedByPlayer(ownerId)));
		ownedByZone(zone);
		suppliers.add(FieldId.Request::new);
		
		suppliers.add(Touchable::new);
		
		return this;
	}
	
	public EntityBuilder ownedByZone(Zones template) {
		
		suppliers.add(() -> new OwnedByZone(template));
		
		return this;
	}
	
	public EntityBuilder selectable() {
		
		suppliers.add(() -> new Selectable());
		
		return this;
	}
	
	public EntityBuilder occupiable() {
		
		suppliers.add(Occupiable::new);
		
		return this;
	}
	
	public EntityBuilder playable() {
		
		suppliers.add(Touchable::new);
		suppliers.add(Selectable::new);
		suppliers.add(Playable::new);
		
		return this;
	}
	
	public EntityBuilder player(int id) {
		
		suppliers.add(() -> new PlayerId(id));
		suppliers.add(() -> new Ep(120));
		suppliers.add(Ep.Update::new);
		suppliers.add(Turnable::new);
		
		return this;
	}
	
	public EntityBuilder turnableOn() {
		
		suppliers.add(Turnable.On::new);
		
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
