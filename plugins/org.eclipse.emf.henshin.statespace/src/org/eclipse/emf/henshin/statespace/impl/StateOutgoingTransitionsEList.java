package org.eclipse.emf.henshin.statespace.impl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * Custom EList implementation for outgoing transitions. 
 * @author Christian Krause
 * @generated NOT
 */
class StateOutgoingTransitionsEList extends EObjectContainmentWithInverseEList<Transition> {
	
	// Default serial ID:
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor.
	 * @param state State.
	 */
	public StateOutgoingTransitionsEList(StateImpl state) {
		super(Transition.class, state, StateSpacePackageImpl.STATE__OUTGOING, StateSpacePackageImpl.TRANSITION__SOURCE);
	}
	
	@Override
	protected void didAdd(int index, Transition transition) {
		updateTransitionCount(1);
	}

	@Override
	protected void didRemove(int index, Transition transition) {
		updateTransitionCount(1);
	}

	@Override
	protected void didClear(int size, Object [] oldObjects) {
		updateTransitionCount(-1 * oldObjects.length);
	}
	
	/*
	 * Update the transition count in the state space.
	 */
	private void updateTransitionCount(int delta) {
		State state = (State) owner;
		StateSpaceImpl stateSpace = (StateSpaceImpl) state.getStateSpace();
		if (stateSpace!=null) {
			stateSpace.internalSetTransitionCount(stateSpace.getTransitionCount() + delta);
		}
	}
	
}
