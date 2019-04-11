package com.github.belserich.state;

import com.google.common.primitives.ImmutableIntArray;

import java.util.Arrays;
import java.util.List;

public class MultiStateDomain implements State {
	
	private final List<State> states;
	
	public MultiStateDomain(State... states) {
		this.states = Arrays.asList(states);
	}
	
	@Override
	public boolean allows(int state) {
		for (State domain : states) {
			if (domain.allows(state)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean is(int state) {
		for (State domain : states) {
			if (domain.allows(state)) {
				if (domain.is(state)) {
					return true;
				} else break;
			}
		}
		return false;
	}
	
	@Override
	public void set(int state) {
		for (State domain : states) {
			if (domain.allows(state)) {
				domain.set(state);
			}
		}
	}
	
	@Override
	public void reset() {
		for (State domain : states) {
			domain.reset();
		}
	}
	
	@Override
	public StateSnapshot snapshot() {
		
		ImmutableIntArray.Builder builder = ImmutableIntArray.builder();
		for (State domain : states) {
			
			if (domain instanceof StateDomain) {
				builder.add(((StateDomain) domain).state);
			} else builder.addAll(domain.snapshot().states().toArray());
		}
		return new StateSnapshot(builder.build());
	}
}
