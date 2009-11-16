package org.eclipse.emf.henshin.statespace.properties;

/**
 * Property change event class.
 * @author Christian Krause
 */
public class PropertyChangeEvent {
	
	// The property:
	private int property;
	
	// Notifier:
	private Object notifier;
	
	// Value:
	private Object value;
	
	/**
	 * Default constructor.
	 * @param notifier Notifier.
	 * @param property Property.
	 * @param value Value.
	 */
	public PropertyChangeEvent(Object notifier, int property, Object value) {
		this.notifier = notifier;
		this.property = property;
		this.value = value;
	}
	
	/**
	 * Get the notifier.
	 * @return Notifier.
	 */
	public Object getNotifier() {
		return notifier;
	}
	
	/**
	 * Get the property.
	 * @return Property.
	 */
	public int getProperty() {
		return property;
	}
	
	/**
	 * Get the value.
	 * @return Value.
	 */
	public Object getValue() {
		return value;
	}
	
}
