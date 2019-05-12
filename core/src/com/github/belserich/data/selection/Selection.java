package com.github.belserich.data.selection;

import com.google.common.base.Optional;

public interface Selection<T> extends SelectionObservable<T> {
	
	boolean contains(T item);
	
	void set(T... items);
	
	void add(T item);
	
	void addAll(T... items);
	
	boolean remove(T item);
	
	Optional<T> primary();
	
	boolean hasPrimary();
	
	T[] secondary();
	
	boolean hasSecondary();
	
	boolean hasSecondary(int threshold);
	
	T[] all();
	
	void removeAll();
	
	int count();
}
