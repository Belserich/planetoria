package com.github.belserich.state;

public interface State {
	
	boolean allows(int state);
	
	boolean is(int state);
	
	void set(int state);
	
	void reset();
	
	StateSnapshot snapshot();
}
