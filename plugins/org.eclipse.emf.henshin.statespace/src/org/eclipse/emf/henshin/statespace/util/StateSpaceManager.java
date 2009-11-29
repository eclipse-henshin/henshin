package org.eclipse.emf.henshin.statespace.util;

import java.util.Arrays;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;

/**
 * State space manager implementation. A state space manager is used 
 * to manage the states in a state space. That is, to efficiently find 
 * states corresponding to given models and to create new states for
 * models that do not exist yet in the state space. It does not handle
 * transitions and in particular does not compute models of derived states.
 * For that the class {@link StateSpaceModelHelper} is used.
 * <p>
 * State space managers index state spaces efficiently. Only a subset
 * of the state models is kept in memory. If a state model is not kept in
 * memory, {@link StateSpaceModelHelper} can be used to derive it from other
 * state models.
 * </p>
 * 
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceManager extends StateSpaceAccessor {
	
	// Initial minimum major size:
	private static int MAJOR_INDEX_SIZE = 1000;
	
	// Initial minor size:
	private static int MINOR_INDEX_SIZE = 4;
	
	// The state space index:
	private State[][] index;
	
	// Determines how many models are kept in memory:
	private int inMemory;
	
	/**
	 * Private constructor.
	 */
	private StateSpaceManager(StateSpace stateSpace, int inMemory) {
		super(stateSpace);
		this.inMemory = inMemory;
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
		StateSpaceManager manager = new StateSpaceManager(stateSpace, inMemory);
		
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
	
	/**
	 * Get the state in the state space that corresponds to a
	 * given model. If no corresponding state was found and the 
	 * create-flag is set to <code>true</code>, a new state is
	 * automatically added and returned.
	 * 
	 * @param model State model.
	 * @param create create-flag.
	 * @return The corresponding state or <code>null</code>.
	 */
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
			state.setHashCode(hash);
			state.setModel(model);				
			
			// Reset the state model conditionally:
			if (!state.isInitial() && inMemory>0 && 
				(getStateSpace().getStates().size() % inMemory) != 0) {
				state.setModel(null);
			}
			
			// Add and return the entry:
			add(state);
			return state;
			
		}
		
		// Not found and also not created:
		return null;

	}
	
	/**
	 * Add a state to the state space and the index. 
	 * This does not check if this state (or an equivalent one) 
	 * exists already. Note that the hashCode attribute of the 
	 * state must be set correctly. The method does not read or 
	 * change the model attribute of the state.
	 * 
	 * @param state State to be added.
	 */
	protected void add(State state) {
		
		// Get the hash code of the state:
		int hash = state.getHashCode();
		
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
		
		// Add the state to the state space:
		access();
		getStateSpace().getStates().add(state);
		release();
		
		// Add the state to the index:
		index[hash][minor] = state;

	}
	
	/**
	 * Maintain the index. This checks if the index is to small
	 * and expands it if required. This method is linear in the
	 * number of indexed states and does not compute any models.
	 */
	protected void maintainIndex() {
		
		// Check if the size of the index is to small:
		if (getStateSpace().getStates().size() * 1.5 > index.length) {
			
			// The new size of the index:
			int newSize = Math.min(getStateSpace().getStates().size() * 3, MAJOR_INDEX_SIZE);
			
			// Create a new array, but remember the old one:
			State[][] oldIndex = index;
			index = new State[newSize][];
			
			// Add all states of the old index to the new index:
			for (int i=0; i<oldIndex.length; i++) {
				if (oldIndex[i]==null) continue;
				for (int j=0; j<oldIndex[i].length; j++) {
					if (oldIndex[i][j]!=null) add(oldIndex[i][j]);
				}
			}
			
		}
	}
	
}
