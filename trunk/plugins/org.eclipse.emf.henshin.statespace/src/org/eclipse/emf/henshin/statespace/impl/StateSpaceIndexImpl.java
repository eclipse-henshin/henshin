/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;

import org.eclipse.emf.henshin.statespace.Model;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.StateSpaceIndex;

/**
 * Default implementation of {@link StateSpaceIndexImpl}. 
 * @generated NOT
 * @author Christian Krause
 */
public class StateSpaceIndexImpl implements StateSpaceIndex {
	
	// Initial size of the index. We use a prime number.
	private static final int INITIAL_INDEX_SIZE = 4093;
	
	// The state space index:
	private State[][] index;
	
	// State space:
	private StateSpace stateSpace;
	
	// Some statistics:
	private int entries, collisions;
	
	/**
	 * Default constructor. This fills the state index.
	 */
	public StateSpaceIndexImpl(StateSpace stateSpace) {
		if (stateSpace==null) {
			throw new IllegalArgumentException();
		}
		this.stateSpace = stateSpace;
		
		// Initialize the index:
		resetIndex();
		for (State state : getStateSpace().getStates()) {
			addToIndex(state);
		}

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceIndex#getModel(org.eclipse.emf.henshin.statespace.State)
	 */
	public Model getModel(State state) throws StateSpaceException {
		if (state.getModel()==null) throw new StateSpaceException("State without model");
		return state.getModel();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getState(org.eclipse.emf.henshin.statespace.Model)
	 */
	public final State getState(Model model) throws StateSpaceException {
		return getState(model, hashCode(model));
	}

	/**
	 * Get the state for a given model and its hash code.
	 * @param model State model.
	 * @param hash Its hash code.
	 * @return The corresponding state if it exists.
	 */
	protected State getState(Model model, int hash) throws StateSpaceException {
		
		// Find all possibly matching states:
		int position = hash2position(hash);
		State[] matched = index[position];
		if (matched==null) return null;
		
		// Check if one of them is the correct one:
		for (int i=0; i<matched.length; i++) {
			if (matched[i]==null || matched[i].getHashCode()!=hash) continue;
			Model current = getModel(matched[i]);
			if (equals(model, current)) {
				return matched[i];
			}
		}
		
		// Not found:
		return null;
		
	}
	
	/**
	 * Add a state to the index.
	 * @param state State to be added.
	 */
	public void addToIndex(State state) {
		
		// We resize if the index 50% filled:
		if (index.length < (2 * entries)) {
			grow();
		}
		
		// Get the hash code of the state:
		int position = hash2position(state.getHashCode());
		
		// Need to create a new array?
		if (index[position]==null) {
			index[position] = new State[4];
		}
		
		// Find the first free minor index:
		int minor = index[position].length;
		boolean collision = false;
		for (int i=0; i<index[position].length; i++) {
			if (index[position][i]==null) {
				minor = i;
			} else {
				collision = true;
			}
		}
		
		// Record collision:
		if (collision) {
			collisions++;
		}
		
		// Check if the array needs to be expanded:
		if (minor>=index[position].length) {
			index[position] = Arrays.copyOf(index[position], index[position].length*2);
		}
		
		// Add the state to the index:
		index[position][minor] = state;
		entries++;
		
	}
	
	/**
	 * Remove a state from the index.
	 * @param state State to be removed.
	 */
	public void removeFromIndex(State state) {
		
		// Find all possibly matching states:
		int position = hash2position(state.getHashCode());
		State[] matched = index[position];		
		if (matched==null) return;
		
		// Check if one of them is the correct one:
		for (int i=0; i<matched.length; i++) {
			if (matched[i]==state) {
				matched[i] = null;
				entries--;
				return;
			}
		}
		
	}
	
	/**
	 * Reset this state space index.
	 */
	public void resetIndex() {
		this.index = new State[optimalSize()][];
		entries = 0;
		collisions = 0;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceIndex#getStateSpace()
	 */
	public StateSpace getStateSpace() {
		return stateSpace;
	}
	
	/**
	 * Get the number of collisions that occurred.
	 * @return Number of collisions.
	 */
	public int getCollisions() {
		return collisions;
	}

	/*
	 * Grow the index. This method is linear in the
	 * number of indexed states and does not compute any models.
	 */
	private void grow() {
		
		// Create a new array, but remember the old one:
		State[][] oldIndex = index;
		resetIndex();

		// Add all states of the old index to the new index:
		for (int i=0; i<oldIndex.length; i++) {
			if (oldIndex[i]==null) continue;
			for (int j=0; j<oldIndex[i].length; j++) {
				if (oldIndex[i][j]!=null) addToIndex(oldIndex[i][j]);
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
		// We want it to be filled to 25%.
		int size = (stateSpace.getStates().size() * 4) + 1;
		return (size<INITIAL_INDEX_SIZE) ? INITIAL_INDEX_SIZE : size;
	}
	
	/*
	 * Compute the hash code of a state model.
	 * This delegates to the state equality helper.
	 */
	protected int hashCode(Model model) {
		return stateSpace.getEqualityHelper().hashCode(model);
	}
	
	/*
	 * Check if two state models are equal.
	 * This delegates to the equality helper.
	 */
	protected boolean equals(Model model1, Model model2) {
		return stateSpace.getEqualityHelper().equals(model1, model2);
	}
	
}
