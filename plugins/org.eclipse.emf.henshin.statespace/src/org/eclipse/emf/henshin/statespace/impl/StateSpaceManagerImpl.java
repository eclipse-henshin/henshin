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
public class StateSpaceManagerImpl extends StateSpaceManagerWithIndex {
	
	// Determines how many models are kept in memory:
	private int memoryUsage = 10;
	
	/**
	 * Private constructor.
	 */
	private StateSpaceManagerImpl(StateSpace stateSpace) {
		super(stateSpace);
	}
	
	/**
	 * Create a new manager for a given state space.
	 * This builds up the index and computes temporary models.
	 * 
	 * @param stateSpace State space.
	 * @param memoryUsage Determines how many state models are kept in memory.
	 * @param monitor Progress monitor.
	 * @return The created manager.
	 */
	public static StateSpaceManager create(StateSpace stateSpace, int memoryUsage, IProgressMonitor monitor) {
		
		// Reset all derived state models:
		for (State state : stateSpace.getStates()) {
			if (!state.isInitial()) {
				state.setModel(null);
			}
		}

		// Create a new manager instance:
		StateSpaceManagerImpl manager = new StateSpaceManagerImpl(stateSpace);
		manager.setMemoryUsage(memoryUsage);
		
		// Compute state models, update the hash code and the index:
		for (State state : stateSpace.getStates()) {
			Resource model = manager.getModel(state);
			state.setHashCode(HenshinEqualityUtil.hashCode(model));
			manager.index(state);
		}
		
		return manager;
		
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getModel(org.eclipse.emf.henshin.statespace.State)
	 */
	public Resource getModel(State state) {
		
		Resource model = null;
		if (state.getModel()!=null) {
			
			// Already there?
			model = state.getModel();
			
		} else {
			
			// Otherwise derive the model:
			
		}
		
		// Reset the state model conditionally:
		if (!state.isInitial()) {
			if (memoryUsage!=0 && (getStateSpace().getStates().size() % memoryUsage) != 0) {
				state.setModel(null);
			} else {
				state.setModel(model);
			}
		}
		
		return model;
		
	}
	

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#explore(org.eclipse.emf.henshin.statespace.State)
	 */
	public List<State> explore(State state) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Set the memory usage of this manager. The higher the value,
	 * the lower the memory usage. Negative values mean that all
	 * models are kept in memory. Zero means nothing is cached.
	 * @param memoryUsage Memory usage.
	 */
	public void setMemoryUsage(int memoryUsage) {
		this.memoryUsage = memoryUsage;
	}

}
