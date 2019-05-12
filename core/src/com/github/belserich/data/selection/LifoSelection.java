package com.github.belserich.data.selection;

import com.google.common.base.Optional;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.Queue;

public class LifoSelection<T> implements Selection<T> {
	
	private final Class<T> clazz;
	private final LinkedList<T> selection;
	
	private final Queue<SelectionListener<T>> listeners;
	
	public LifoSelection(Class<T> clazz) {
		this.clazz = clazz;
		selection = new LinkedList<T>();
		listeners = new LinkedList<SelectionListener<T>>();
	}
	
	@Override
	public void addSelectionListener(SelectionListener<T> listener) {
		listeners.add(listener);
	}
	
	@Override
	public boolean removeSelectionListener(SelectionListener<T> listener) {
		return listeners.remove(listener);
	}
	
	@Override
	public void fireSelected(T item) {
		for (SelectionListener<T> listener : listeners) {
			listener.selected(item);
		}
	}
	
	@Override
	public void fireUnselected(T by) {
		for (SelectionListener<T> listener : listeners) {
			listener.unselected(by);
		}
	}
	
	@Override
	public void firePrimaryChanged(T last, T curr) {
		for (SelectionListener<T> listener : listeners) {
			listener.primaryChanged(last, curr);
		}
	}
	
	@Override
	public boolean contains(T item) {
		return selection.contains(item);
	}
	
	@Override
	public void set(T... items) {
		removeAll();
		addAll(items);
	}
	
	@Override
	public void add(T item) {
		
		T last = selection.isEmpty() ? null : selection.element();
		if (last != item) { // ensures item is not already primary
			selection.remove(item);
			selection.add(0, item);
			fireSelected(item);
			firePrimaryChanged(last, item);
		}
	}
	
	@Override
	public void addAll(T... items) {
		for (T item : items) {
			add(item);
		}
	}
	
	@Override
	public boolean remove(T item) {
		
		int index = selection.indexOf(item);
		remove(index);
		return index != -1;
	}
	
	@Override
	public Optional<T> primary() {
		if (!selection.isEmpty()) {
			return Optional.of(selection.get(0));
		} else return Optional.absent();
	}
	
	@Override
	public boolean hasPrimary() {
		return !selection.isEmpty();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T[] secondary() {
		T[] arr = (T[]) Array.newInstance(clazz, Math.max(0, selection.size() - 1));
		if (selection.size() > 1) {
			for (int i = 1; i < selection.size(); i++) {
				arr[i - 1] = selection.get(i);
			}
		}
		return arr;
	}
	
	@Override
	public boolean hasSecondary() {
		return selection.size() > 1;
	}
	
	@Override
	public boolean hasSecondary(int threshold) {
		return selection.size() > threshold;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public T[] all() {
		T[] arr = (T[]) Array.newInstance(clazz, Math.max(0, selection.size()));
		for (int i = 0; i < selection.size(); i++) {
			arr[i] = selection.get(i);
		}
		return arr;
	}
	
	@Override
	public void removeAll() {
		while (!selection.isEmpty()) {
			remove(selection.size() - 1);
		}
	}
	
	@Override
	public int count() {
		return selection.size();
	}
	
	private void remove(int index) {
		
		if (index != -1) {
			
			T item = selection.remove(index);
			fireUnselected(item);
			
			if (index == 0) {
				// removed item was primary
				T curr = selection.isEmpty() ? null : selection.element();
				firePrimaryChanged(item, curr);
			}
		}
	}
}
