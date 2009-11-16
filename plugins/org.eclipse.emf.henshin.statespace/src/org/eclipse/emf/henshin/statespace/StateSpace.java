package org.eclipse.emf.henshin.statespace;

import java.util.List;

import org.eclipse.emf.henshin.statespace.properties.NotifyingList;
import org.eclipse.emf.henshin.statespace.properties.PropertyAdapter;

/**
 * Light-weight model of a state space.
 * @author Christian Krause
 */
public class StateSpace extends PropertyAdapter {

	// StateSpace properties:
	public static final int STATES = 1;

	// List of states.
	private List<State> states;

	/**
	 * Default constructor.
	 */
	public StateSpace() {
		this.states = new NotifyingList<State>(this, STATES);
	}
	
	/**
	 * Get the list of states in this state space.
	 * @return List of states.
	 */
	public List<State> getStates() {
		return states;
	}
	
}
