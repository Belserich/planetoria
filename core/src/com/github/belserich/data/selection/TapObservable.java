package com.github.belserich.data.selection;

public interface TapObservable {
	
	void addTapListener(TapListener listener);
	
	boolean removeTapListener(TapListener listener);
	
	void fireLeftTap(float x, float y);
}
