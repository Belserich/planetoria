package com.github.belserich.data.selection;

public interface SelectionObservable<T> {
	
	void addSelectionListener(SelectionListener<T> listener);
	
	boolean removeSelectionListener(SelectionListener<T> listener);
	
	void fireSelected(T item);
	
	void fireUnselected(T item);
	
	void firePrimaryChanged(T last, T curr);
}
