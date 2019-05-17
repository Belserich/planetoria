package com.github.belserich.state;

import com.google.common.base.Objects;
import com.google.common.primitives.ImmutableIntArray;

/**
 * A state snapshot is the image of a state at a specific point in time.
 */
public class StateSnapshot {
	
	/**
	 * contains all set states
	 */
	private final ImmutableIntArray states;
	
	/**
	 * Creates a new snapshot with at least one set state.
	 *
	 * @param first first set state
	 * @param rest  other set states
	 */
	public StateSnapshot(int first, int... rest) {
		this(ImmutableIntArray.of(first, rest));
	}
	
	/**
	 * Creates a new snapshot from an array of set states.
	 *
	 * @param states
	 */
	public StateSnapshot(ImmutableIntArray states) {
		if (states.isEmpty()) {
			throw new IllegalArgumentException("Specified state array is empty!");
		}
		this.states = states;
	}
	
	/**
	 * @param state the state in question
	 * @return whether state is set
	 */
	public boolean is(int state) {
		return states.contains(state);
	}
	
	/**
	 * @return array of set states
	 */
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
