package com.github.belserich.state;

import com.google.common.base.Objects;
import com.google.common.primitives.ImmutableIntArray;

public class StateSnapshot {
	
	private final ImmutableIntArray states;
	
	public StateSnapshot(int first, int... rest) {
		this(ImmutableIntArray.of(first, rest));
	}
	
	public StateSnapshot(ImmutableIntArray states) {
		this.states = states;
	}
	
	public boolean is(int state) {
		return states.contains(state);
	}
	
	public ImmutableIntArray states() {
		return states;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(states); // error prone
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (!(obj instanceof StateSnapshot)) {
			return false;
		}
		
		ImmutableIntArray otherStates = ((StateSnapshot) obj).states;
		if (otherStates.length() != states.length()) {
			return false;
		}
		
		for (int i = 0; i < states.length(); i++) {
			if (states.get(i) != otherStates.get(i)) {
				return false;
			}
		}
		
		return true;
	}
}
