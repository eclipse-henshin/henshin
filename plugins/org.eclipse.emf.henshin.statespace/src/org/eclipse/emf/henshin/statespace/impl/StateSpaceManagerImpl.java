package org.eclipse.emf.henshin.statespace.impl;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.util.HenshinEqualityUtil;

/**
 * Default state space manager implementation. 
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManagerImpl extends StateSpaceIndexImpl {
	
	/**
	 * Private constructor.
	 */
	private StateSpaceManagerImpl(StateSpace stateSpace, int inMemory) {
		super(stateSpace, inMemory);
	}
	
	/**
	 * Create a new manager for a given state space.
	 * This builds up the index and computes temporary models.
	 * 
	 * @param stateSpace State space.
	 * @param inMemory Determines how many state models are kept in memory.
	 * @param monitor Progress monitor.
	 * @return The created manager.
	 */
	public static StateSpaceManager create(StateSpace stateSpace, int inMemory, IProgressMonitor monitor) {
		
		// Create a new manager instance:
		StateSpaceManagerImpl manager = new StateSpaceManagerImpl(stateSpace, inMemory);
		
		// Reset all derived state models and update the hash codes:
		for (State state : stateSpace.getStates()) {
			if (state.isInitial()) {
				state.setHashCode(HenshinEqualityUtil.hashCode(state.getModel()));
			} else {
				state.setModel(null);
				state.setHashCode(0);
			}
		}
		
		
		
		return manager;
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getModel(org.eclipse.emf.henshin.statespace.State)
	 */
	public Resource getModel(State state) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#explore(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<State> explore(State state) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
