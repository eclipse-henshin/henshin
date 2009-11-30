package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.util.HenshinEqualityUtil;

/**
 * Abstract state space manager implementation that uses an
 * hash-based index of looking up states.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public abstract class StateSpaceManagerWithIndex extends StateSpaceAccessorImpl implements StateSpaceManager {
	
	// The state space index:
	private State[][] index;
	
	/**
	 * Default constructor. Does not index the states.
	 * Subclasses must do this manually using {@link #index(State)}.
	 */
	public StateSpaceManagerWithIndex(StateSpace stateSpace) {
		super(stateSpace);
		this.index = new State[optimalSize()][];
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceIndex#getState(org.eclipse.emf.ecore.resource.Resource, boolean)
	 */
	public State getState(Resource model, boolean create) {
		
		// Find all possibly matching states:
		int hash = HenshinEqualityUtil.hashCode(model);
		State[] matched = index[hash % index.length];
		
		// Check if one of them is the correct one:
		if (matched!=null) {
			for (State state : matched) {
				Resource current = getModel(state);
				if (HenshinEqualityUtil.equals(model, current)) {
					return state;
				}
			}
		}
		
		// Not matching state found -- create a new one if requested:
		if (create) {

			// Maintain the index:
			maintain();
			
			// Create a new state instance:
			State state = StateSpaceFactory.INSTANCE.createState();
			state.setName("s" + getStateSpace().getStates().size());
			state.setHashCode(hash);
			state.setModel(model);				
			
			// Add the state to the state space:
			access();
			getStateSpace().getStates().add(state);
			release();
			
			// Add the state to the index as well:
			index(state);
			return state;

		}
		
		// Not found and also not created:
		return null;
		
	}
	
	/*
	 * Add a state to the index. This does not check if this state 
	 * (or an equivalent one) exists already in the index. Note that the 
	 * hashCode attribute of the state must be set correctly. The method 
	 * does not read or change the model attribute of the state.
	 */
	protected void index(State state) {

		// Get the hash code of the state:
		int hash = state.getHashCode();

		// Need to create a new array?
		if (index[hash]==null) {
			index[hash] = new State[4];
		}

		// Find the next free minor index:
		int minor = index[hash].length;
		for (int i=0; i<index[hash].length; i++) {
			if (index[i]==null) {
				minor = i;
				break;
			}
		}

		// Check if the array needs to be expanded:
		if (minor>=index[hash].length) {
			index[hash] = Arrays.copyOf(index[hash], index[hash].length*2);
		}

		// Add the state to the index:
		index[hash][minor] = state;

	}

	/*
	 * Maintain the index. This checks if the index is to small
	 * and expands it if required. This method is linear in the
	 * number of indexed states and does not compute any models.
	 */
	private void maintain() {

		// Check if the size of the index is to small:
		if (getStateSpace().getStates().size() > index.length) {
			
			// Create a new array, but remember the old one:
			State[][] oldIndex = index;
			index = new State[optimalSize()][];

			// Add all states of the old index to the new index:
			for (int i=0; i<oldIndex.length; i++) {
				if (oldIndex[i]==null) continue;
				for (int j=0; j<oldIndex[i].length; j++) {
					if (oldIndex[i][j]!=null) index(oldIndex[i][j]);
				}
			}
			
		}
	}
		
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.StateSpaceAccessorImpl#getStateSpace()
	 */
	public StateSpace getStateSpace() {
		return super.getStateSpace();
	}
	
	private int optimalSize() {
		return Math.min(getStateSpace().getStates().size() * 2, 512);
	}

}
