package org.eclipse.emf.henshin.statespace.impl;

import java.util.Arrays;

import org.eclipse.emf.henshin.statespace.State;

/**
 * @author Christian Krause
 * @generated NOT
 */
public class StateAttributes {
	
	public static final int COORDINATES = 2;
	
	public static final int X_INDEX = 0;
	public static final int Y_INDEX = 1;
	public static final int EXPLORED_INDEX = 2;
	
	public static boolean isExplored(State state) {
		return (get(state,EXPLORED_INDEX)!=0);
	}
	
	public static void setExplored(State state, boolean newExplored) {
		
		// Remember old value and set the new one:
		boolean oldExplored = isExplored(state);
		set(state, EXPLORED_INDEX, newExplored ? 1 : 0);
		
		// Update statistics:
		StateSpaceImpl stateSpace = (StateSpaceImpl) state.getStateSpace();
		if (stateSpace!=null) {
			if (!oldExplored && newExplored) {
				stateSpace.internalSetExploredCount(stateSpace.getExploredCount()+1);
			} else if (oldExplored && !newExplored) {
				stateSpace.internalSetExploredCount(stateSpace.getExploredCount()-1);			
			}
		}
		
	}
	
	public static int getX(State state) {
		return get(state,X_INDEX);
	}

	public static int getY(State state) {
		return get(state,Y_INDEX);
	}

	public static int[] getLocation(State state) {
		int[] attributes = state.getAttributes();
		int[] location = new int[COORDINATES];
		int count = Math.min(attributes.length-X_INDEX, COORDINATES);
		for (int i=0; i<count; i++) {
			location[i] = attributes[i+X_INDEX];
		}
		return location;
	}
	
	public static int[] getMovedLocation(State state, int... delta) {
		int[] location = getLocation(state);
		for (int i=0; i<Math.max(location.length,delta.length); i++) {
			location[i] += delta[i];
		}
		return location;
	}
	
	public static void setLocation(State state, int... location) {
		int size = Math.max(state.getAttributes().length, X_INDEX + COORDINATES);
		int[] attributes = Arrays.copyOf(state.getAttributes(), size);
		for (int i=0; i<Math.max(COORDINATES,location.length); i++) attributes[i+X_INDEX] = location[i];
		state.setAttributes(attributes);
	}
	
	public static void move(State state, int... delta) {
		setLocation(state, getMovedLocation(state, delta));
	}
	
	private static int get(State state, int index) {
		int[] attributes = state.getAttributes();
		return (attributes.length>index) ? attributes[index] : 0;
	}

	public static void set(State state, int index, int value) {
		if (value!=0 || state.getAttributes().length>index) {
			int[] attributes = Arrays.copyOf(state.getAttributes(), Math.max(state.getAttributes().length, index+1));
			attributes[index] = value;
			state.setAttributes(attributes);
		}
	}

}
