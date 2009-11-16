package org.eclipse.emf.henshin.statespace.properties;

/**
 * Property change event class.
 * @author Christian Krause
 */
public class PropertyChangeEvent {
	
	// StateSpace properties:
	public static final int STATES = 1;
	
	// State properties:
	public static final int INCOMING_TRANSITIONS = 2;
	public static final int OUTGOING_TRANSITIONS = 3;
	public static final int STATE_NAME = 4;
	public static final int STATE_LOCATION = 5;
	
	// Transition properties:
	public static final int TRANSITION_NAME = 6;
	
	
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
