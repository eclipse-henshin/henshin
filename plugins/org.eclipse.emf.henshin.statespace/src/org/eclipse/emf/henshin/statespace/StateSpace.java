package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.henshin.statespace.properties.NotifyingList;
import org.eclipse.emf.henshin.statespace.properties.PropertyAdapter;
import org.eclipse.emf.henshin.statespace.properties.PropertyChangeEvent;

/**
 * Light-weight model of a state space.
 * @author Christian Krause
 */
public class StateSpace extends PropertyAdapter {
	
	// List of states.
	private List<State> states;

	/**
	 * Default constructor.
	 */
	public StateSpace() {
		this.states = new NotifyingList<State>(this, PropertyChangeEvent.STATES);
	}
	
	/**
	 * Get the list of states in this state space.
	 * @return List of states.
	 */
	public List<State> getStates() {
		return states;
	}
	
}
