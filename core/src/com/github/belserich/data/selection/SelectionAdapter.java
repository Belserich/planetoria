package com.github.belserich.data.selection;

public class SelectionAdapter<T> implements SelectionListener<T> {
	
	@Override
	public void selected(T item) {
		// nothing
	}
	
	@Override
	public void unselected(T item) {
		// nothing
	}
	
	@Override
	public void primaryChanged(T last, T curr) {
		// nothing
	}
}
