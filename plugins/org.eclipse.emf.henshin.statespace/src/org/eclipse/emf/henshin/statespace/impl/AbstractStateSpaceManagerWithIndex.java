package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Abstract state space manager implementation that uses an
 * hash-based index of looking up states.
 * 
 * @generated NOT
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceManagerWithIndex extends AbstractStateSpaceManagerWithCache {
	
	// Minimum size of the index:
	private static final int MIN_INDEX_SIZE = 4;
	
	// The state space index:
	private State[][] index;
	
	/**
	 * Default constructor. Does not index the states.
	 * Subclasses must do this manually using {@link #index(State)}.
	 */
	public AbstractStateSpaceManagerWithIndex(StateSpace stateSpace) {
		super(stateSpace);
		resetRegistry();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#getState(org.eclipse.emf.ecore.resource.Resource, int)
	 */
	@Override
	protected State getState(Resource model, int hash) {
		
		// Find all possibly matching states:
		int position = hash2position(hash);
		State[] matched = index[position];
		
		// Check if one of them is the correct one:
		if (matched!=null) {
			for (State state : matched) {
				Resource current = getModel(state);
				if (equals(model, current)) {
					return state;
				}
			}
		}
		
		// Not found:
		return null;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#registerState(org.eclipse.emf.henshin.statespace.State)
	 */
	@Override
	protected void registerState(State state) {
		
		// Check if the index needs to be resized:
		maintainIndex();
		
		// Get the hash code of the state:
		int position = hash2position(state.getHashCode());
		
		// Need to create a new array?
		if (index[position]==null) {
			index[position] = new State[4];
		}
		
		// Find the next free minor index:
		int minor = index[position].length;
		for (int i=0; i<index[position].length; i++) {
			if (index[i]==null) {
				minor = i;
				break;
			}
		}
		
		// Check if the array needs to be expanded:
		if (minor>=index[position].length) {
			index[position] = Arrays.copyOf(index[position], index[position].length*2);
		}

		// Add the state to the index:
		index[position][minor] = state;
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManager#resetRegistry()
	 */
	@Override
	protected void resetRegistry() {
		this.index = new State[optimalSize()][];
	}
	
	/*
	 * Maintain the index. This checks if the index is to small
	 * and expands it if required. This method is linear in the
	 * number of indexed states and does not compute any models.
	 */
	private void maintainIndex() {

		// Check if the size of the index is to small:
		if (getStateSpace().getStates().size() > index.length) {
			
			// Create a new array, but remember the old one:
			State[][] oldIndex = index;
			resetRegistry();
			
			// Add all states of the old index to the new index:
			for (int i=0; i<oldIndex.length; i++) {
				if (oldIndex[i]==null) continue;
				for (int j=0; j<oldIndex[i].length; j++) {
					if (oldIndex[i][j]!=null) registerState(oldIndex[i][j]);
				}
			}
			
		}
	}
	
	/*
	 * Convert a hash to an index position.
	 */
	private int hash2position(int hash) {
		return Math.abs(hash) % index.length;
	}
	
	/*
	 * Compute the optimal size of the index.
	 */
	private int optimalSize() {
		int size = getStateSpace().getStates().size() * 2;
		return (size<MIN_INDEX_SIZE) ? MIN_INDEX_SIZE : size;
	}
	
}
