package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.henshin.statespace.State;

/**
 * Custom EList implementation for the states in a state space.
 * @author Christian Krause
 * @generated NOT
 */
class StateSpaceStatesEList extends EObjectContainmentWithInverseEList<State> {

	// Default serial ID:
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	StateSpaceStatesEList(StateSpaceImpl stateSpace) {
		super(State.class, stateSpace, StateSpacePackageImpl.STATE_SPACE__STATES, StateSpacePackageImpl.STATE__STATE_SPACE);
	}
	
	@Override
	public State remove(int index) {
		if (index!=size-1) throw new UnsupportedOperationException();
		else return super.remove(index);
	}

	@Override
	public void add(int index, State state) {
		if (index!=size) throw new UnsupportedOperationException();
		else super.add(index, state);
	}
	
	@Override
	public State move(int from, int to) {
		if (from!=to) throw new UnsupportedOperationException();
		else return super.move(from, to);
	}

}
