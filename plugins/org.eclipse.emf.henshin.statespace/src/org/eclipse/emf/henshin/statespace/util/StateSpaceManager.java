package org.eclipse.emf.henshin.statespace.util;

import java.util.Arrays;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;

/**
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManager extends StateSpaceAccessor {

	// Initial minimum major size:
	private static int MAJOR_INDEX_SIZE = 1000;
	
	// Initial minor size:
	private static int MINOR_INDEX_SIZE = 4;
	
	// The actual index:
	protected State[][] index;
	
	/**
	 * Default constructor.
	 */
	public StateSpaceManager(StateSpace stateSpace) {
		super(stateSpace);
		maintainIndex();
	}	
	
	public State get(Resource model, boolean create) {
				
		// Find all possibly matching states:
		int hash = HenshinEqualityUtil.hashCode(model);
		State[] matched = index[hash % index.length];
		
		// Check if one of them is the correct one:
		if (matched!=null) {
			for (State state : matched) {
				Resource current = StateSpaceModelHelper.getModel(state);
				if (HenshinEqualityUtil.equals(model, current)) {
					return state;
				}
			}
		}
		
		// Not matching state found -- create a new one if requested:
		if (create) {
			
			// Maintain the index:
			maintainIndex();
			
			// Create a new state instance:
			State state = StateSpaceFactory.INSTANCE.createState();
			state.setModel(model);
			state.setHashCode(hash);
			
			// Need to create a new array?
			if (index[hash]==null) {
				index[hash] = new State[MINOR_INDEX_SIZE];
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
			
			// Add and return the entry:
			index[hash][minor] = state;
			return state;
		}
		
		// Not found and also not created:
		return null;

	}
	
	private void maintainIndex() {
		int newSize = Math.min(getStateSpace().getStates().size() * 2, MAJOR_INDEX_SIZE);
		this.index = new State[newSize][];
	}
	
}
