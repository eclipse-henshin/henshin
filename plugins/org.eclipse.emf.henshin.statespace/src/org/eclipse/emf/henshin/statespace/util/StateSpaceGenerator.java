package org.eclipse.emf.henshin.statespace.util;

import java.util.HashMap;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * State space generator.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceGenerator {
	
	// The state space:
	private StateSpace stateSpace;
	
	// Hash map for states: 
	private HashMap<Integer,List<State>> states;
	
	/*
	 * Private constructor.
	 */
	private StateSpaceGenerator(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
		this.states = new HashMap<Integer,List<State>>((int) (1.5 * stateSpace.getStates().size()));
	}
	
	/**
	 * Create a new state space generator for a given state space.
	 * @param stateSpace State space.
	 * @param monitor Progress monitor.
	 * @return The created state space.
	 * @throws StateSpaceValidationError On validation errors.
	 */
	public static StateSpaceGenerator create(StateSpace stateSpace, IProgressMonitor monitor) throws StateSpaceValidationError {
		StateSpaceGenerator generator = new StateSpaceGenerator(stateSpace);
		generator.refresh(monitor);
		return generator;
	}
	
	/**
	 * Refresh the state space.
	 * @param monitor Progress monitor.
	 * @throws StateSpaceValidationError On validation error.
	 */
	public void refresh(IProgressMonitor monitor) throws StateSpaceValidationError {
		// TODO
	}
	
	/**
	 * Reset the state space. This removes all derived states.
	 */
	public void reset() {
		
	}
	
	/**
	 * Get the associated state space.
	 * @return State space.
	 */
	public StateSpace getStateSpace() {
		return stateSpace;
	}
	
}
