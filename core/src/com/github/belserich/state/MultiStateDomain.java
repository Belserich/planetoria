package com.github.belserich.state;

import com.google.common.primitives.ImmutableIntArray;

import java.util.Arrays;
import java.util.List;

/**
 * A multi-state domain allows multiple set states at once.
 */
public class MultiStateDomain implements State {
	
	/**
	 * State objects making up this domain
	 */
	private final List<State> states;
	
	/**
	 * Creates a domain from existing state objects.
	 *
	 * @param states state objects making up this domain
	 */
	public MultiStateDomain(State... states) {
		this.states = Arrays.asList(states);
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param state state in question
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean allows(int state) {
		for (State domain : states) {
			if (domain.allows(state)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param state state in question
	 * @return {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 *
	 * @param state state to set
	 */
	@Override
	public void set(int state) {
		for (State domain : states) {
			if (domain.allows(state)) {
				domain.set(state);
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		for (State domain : states) {
			domain.reset();
		}
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
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
