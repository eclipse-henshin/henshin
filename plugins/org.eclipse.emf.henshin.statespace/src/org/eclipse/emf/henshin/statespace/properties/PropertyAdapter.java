package org.eclipse.emf.henshin.statespace.properties;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public abstract class PropertyAdapter {

	private Object listeners;	
	
	/**
	 * Add a property change listener.
	 * @param listener Property change listener.
	 */
	@SuppressWarnings("unchecked")
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener!=null) {
			if (listeners==listener) return;
			if (listeners==null) {
				listeners = listener;
				return;
			}
			if (listeners instanceof PropertyChangeListener) {
				List<PropertyChangeListener> list = new ArrayList<PropertyChangeListener>();
				list.add((PropertyChangeListener) listeners);
				listeners = list;
			}
			
			// Add the new listener to the list:
			List<PropertyChangeListener> list = (List<PropertyChangeListener>) listeners;
			if (!list.contains(listener)) list.add(listener);
		}
	}

	
	/**
	 * Remove a property change listener.
	 * @param listener Property change listener.
	 */
	@SuppressWarnings("unchecked")
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener!=null) {
			if (listeners==listener) {
				listeners = null;
			}
			else if (listeners instanceof List) {
				// Remove the new listener from the list:
				List<PropertyChangeListener> list = (List<PropertyChangeListener>) listeners;
				list.remove(listener);
			}
		}
	}

	/**
	 * Fire a property change event. This notifies all attached listeners.
	 * @param property Property.
	 */
	@SuppressWarnings("unchecked")
	protected void firePropertyChange(int property, Object value) {
		
		// Construct a new change event:
		PropertyChangeEvent event = new PropertyChangeEvent(this, property, value);
		
		// Notify listeners:
		if (listeners instanceof PropertyChangeListener) {
			((PropertyChangeListener) listeners).propertyChanged(event);
		}
		else if (listeners instanceof List) {
			List<PropertyChangeListener> list = (List<PropertyChangeListener>) listeners;
			for (PropertyChangeListener listener : list) {
				listener.propertyChanged(event);
			}
		}
		
	}

	/**
	 * @deprecated
	 */
	private void readObject(ObjectInputStream in) 
	throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

}
