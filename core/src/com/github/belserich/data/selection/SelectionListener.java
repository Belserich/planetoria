package com.github.belserich.data.selection;

public interface SelectionListener<T> {
	
	void selected(T item);
	
	void unselected(T item);
	
	void primaryChanged(T last, T curr);
}
