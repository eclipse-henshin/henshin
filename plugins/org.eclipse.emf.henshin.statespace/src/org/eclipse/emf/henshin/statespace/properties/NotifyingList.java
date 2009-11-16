package org.eclipse.emf.henshin.statespace.properties;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link List} with additional
 * notification support.
 * @author Christian Krause
 * @param <T> Type of list.
 */
public class NotifyingList<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;
	
	private PropertyAdapter adapter;
	private int property;
	
	public NotifyingList(PropertyAdapter adapter, int property) {
		this.adapter = adapter;
		this.property = property;
	}
	
	@Override
	public boolean add(T value) {
		if (super.add(value)) {
			adapter.firePropertyChange(property, value);
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean remove(Object value) {
		if (super.remove(value)) {
			adapter.firePropertyChange(property, value);
			return true;
		} else {
			return false;
		}
	}
	
}
