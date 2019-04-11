package com.github.belserich.state;

import com.badlogic.gdx.utils.IntSet;

public class StateDomain implements State {
	
	private final IntSet domain;
	private final int resetState;
	
	int state;
	
	public StateDomain(int... possibleStates) {
		this(IntSet.with(possibleStates), possibleStates[0]);
	}
	
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
	
	@Override
	public boolean is(int state) {
		check(state);
		return this.state == state;
	}
	
	@Override
	public void set(int state) {
		if (check(state)) {
			this.state = state;
		}
	}
	
	@Override
	public void reset() {
		state = resetState;
	}
	
	@Override
	public StateSnapshot snapshot() {
		return new StateSnapshot(state);
	}
}
