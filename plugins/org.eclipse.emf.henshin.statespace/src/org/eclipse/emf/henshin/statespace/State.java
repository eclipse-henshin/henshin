package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.henshin.statespace.properties.NotifyingList;
import org.eclipse.emf.henshin.statespace.properties.PropertyAdapter;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent;

/**
 * @author Christian Krause
 */
public class State extends PropertyAdapter {
	
	// Location coordinates:
	private int x=-1, y=-1;
	
	// Name of this state:
	private String name;
	
	// Incoming and outgoing transitions:
	private List<Transition> incoming, outgoing;
	
	/**
	 * Default constructor.
	 */
	public State() {
		// Initialize list of incoming / outgoing transitions:
		outgoing = new NotifyingList<Transition>(this, PropertyChangeEvent.OUTGOING_TRANSITIONS);
		incoming = new NotifyingList<Transition>(this, PropertyChangeEvent.INCOMING_TRANSITIONS);
	}

	/**
	 * Convenience constructor.
	 * @param name Name of the state.
	 */
	public State(String name) {
		this();
		this.name = name;
	}
	
	/**
	 * Get the list of outgoing transitions.
	 * @return Outgoing transitions.
	 */
	public List<Transition> getOutgoing() {
		return outgoing;
	}
	
	/**
	 * Get the list of incoming transitions.
	 * @return Incoming transitions.
	 */
	public List<Transition> getIncoming() {
		return incoming;
	}
	
	/**
	 * Get the name of this state.
	 * @return State name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name of this state.
	 * @param name State name.
	 */
	public void setName(String name) {
		this.name = name;
		firePropertyChange(PropertyChangeEvent.STATE_NAME, name);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		setXY(x,y);
	}

	public void setY(int y) {
		setXY(x,y);
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
		firePropertyChange(PropertyChangeEvent.STATE_LOCATION, null);
	}
	
}