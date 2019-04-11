package com.github.belserich.state;

import com.badlogic.gdx.utils.IntSet;

/**
 * A state domain can have a single set state at once.
 */
public class StateDomain implements State {
	
	/**
	 * possible states
	 */
	private final IntSet domain;
	
	/**
	 * reset state id
	 */
	private final int resetState;
	
	/**
	 * currently set state
	 */
	int state;
	
	/**
	 * Creates a new state domain with an array of possible ids.
	 *
	 * @param possibleStates valid state ids
	 */
	public StateDomain(int... possibleStates) {
		this(IntSet.with(possibleStates), possibleStates[0]);
	}
	
	/**
	 * Creates a new state domain with an array of possible ids. Also specifies a reset state that acts as fallback.
	 *
	 * @param domain valid state ids
	 * @param resetState fallback state id
	 */
	public StateDomain(IntSet domain, int resetState) {
		this.domain = domain;
		this.resetState = resetState;
		
		if (domain.size < 1) {
			throw new IllegalArgumentException("Domain must contain at least one state id!");
		}
		
		if (!domain.contains(resetState)) {
			throw new IllegalArgumentException("Domain must contain specified reset state!");
		}
		
		state = resetState;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param state state in question
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean allows(int state) {
		return domain.contains(state);
	}
	
	/**
	 * Checks whether the specified state id is a valid id in this domain. Throws an {@link IllegalArgumentException} exception if it is not.
	 *
	 * @param state the state to check
	 * @return whether the specified state id is valid in this context
	 */
	private boolean check(int state) {
		if (!allows(state)) {
			invalidIdAccessAttempt(state);
			return false;
		}
		return true;
	}
	
	private void invalidIdAccessAttempt(int state) {
		throw new IllegalArgumentException("State id " + state + " is not contained in domain " + domain.toString());
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param state state in question
	 * @return {@inheritDoc}
	 */
	@Override
	public boolean is(int state) {
		check(state);
		return this.state == state;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @param state state to set
	 */
	@Override
	public void set(int state) {
		if (check(state)) {
			this.state = state;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void reset() {
		state = resetState;
	}
	
	/**
	 * {@inheritDoc}
	 *
	 * @return {@inheritDoc}
	 */
	@Override
	public StateSnapshot snapshot() {
		return new StateSnapshot(state);
	}
}
