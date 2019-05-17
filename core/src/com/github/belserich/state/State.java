package com.github.belserich.state;

public interface State {
	
	/**
	 * @param state state in question
	 * @return whether setting state is allowed
	 */
	boolean allows(int state);
	
	/**
	 * @param state state in question
	 * @return whether state is set
	 */
	boolean is(int state);
	
	/**
	 * @param state state to set
	 */
	void set(int state);
	
	/**
	 * Resets the whole state object
	 */
	void reset();
	
	/**
	 * Creates a snapshot of the current state
	 *
	 * @return snapshot object
	 */
	StateSnapshot snapshot();
}
