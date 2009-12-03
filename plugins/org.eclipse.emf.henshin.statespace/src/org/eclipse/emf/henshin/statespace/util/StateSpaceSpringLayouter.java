package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Spring layout algorithm for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceSpringLayouter {
	
	private static final int MIN_CHANGE = 1;
	
	// State space to be layouted:
	private StateSpace stateSpace;
	
	// Velocities:
	private int[] velocitiesX;
	private int[] velocitiesY;
	
	// Layouting parameters:
	private int repulsion = 50;
	private int attraction = 10;
	private int naturalLength = 50;
	
	private int[] center;
	
	
	/**
	 * Set the state space.
	 * @param stateSpace State space.
	 */
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}
	
	/**
	 * Update the velocities of the states.
	 * @return <code>true</code> if at least one state has a velocity that is not zero.
	 */
	public boolean updateVelocities() {
		
		// Total number of states:
		int states = stateSpace.getStates().size();
		if (states==0) return true;
		
		// Make sure there is enough space for the velocities:
		if (velocitiesX==null || states>velocitiesX.length) {
			int size = velocityArraySize(states);
			velocitiesX = new int[size];
			velocitiesY = new int[size];			
		}
		
		boolean changed = false;
		
		for (int i=0; i<states; i++) {
			
			velocitiesX[i] = 0;
			velocitiesY[i] = 0;
			
			State state = stateSpace.getStates().get(i);
			int[] location = state.getLocation();
			int[] netForce = new int[2];
						
			// State repulsion:
			for (int j=0; j<states; j++) {
				
				if (i==j) continue;            
				State otherState = stateSpace.getStates().get(j);
				
				int[] repulsion = stateRepulsion(location, otherState.getLocation());
				netForce[0] += repulsion[0];
				netForce[1] += repulsion[1];

			}

			// Transition attraction:
			int incoming = state.getIncoming().size();
			int links = incoming + state.getOutgoing().size();
			for (int j=0; j<links; j++) {
				
				// Get the state on the other side of the transition:
				State otherState = (j<incoming) ? 
						state.getIncoming().get(j).getSource() : 
						state.getOutgoing().get(j-incoming).getTarget();
				
				// Check the other state:
				if (otherState==state || otherState==null) continue;
				
				// Calculate and add transition attraction:
				int[] attraction = transitionAttraction(location, otherState.getLocation());
				netForce[0] += attraction[0];
				netForce[1] += attraction[1];
				
			}
			
			// Add the attraction to the center:
			if (center!=null) {
				int[] attraction = transitionAttraction(location, center);
				netForce[0] += (attraction[0] / 5);
				netForce[1] += (attraction[1] / 5);
			}
			
			// Update the velocities:
			velocitiesX[i] = netForce[0];
			velocitiesY[i] = netForce[1];
			
			// Check if something changed:
			changed = changed || Math.abs(velocitiesX[i])>MIN_CHANGE || Math.abs(velocitiesY[i])>MIN_CHANGE;
			
		}
		
		return changed;
		
	}

	/**
	 * Update the state locations.
	 */
	public void updateLocations() {

		// Total number of states:
		if (velocitiesX==null) return;
		int states = Math.min(stateSpace.getStates().size(), velocitiesX.length);
		if (states==0) return;
		
		// Update all states:
		for (int i=0; i<states; i++) {
			State state = stateSpace.getStates().get(i);
			int[] location = state.getLocation();
			location[0] += velocitiesX[i];
			location[1] += velocitiesY[i];
			state.setLocation(location);
		}
		
	}
	
	/*
	 * Compute the transition attraction between two states.
	 */
	private int[] transitionAttraction(int[] l1, int[] l2) {
		int[] direction = direction(l2,l1);
		double distance = length(direction);
		if (distance>1) {
			double factor = attraction * Math.log(distance / naturalLength) / distance;
			direction[0] *= factor;
			direction[1] *= factor;			
		} else {
			direction = randomShift();
		}
		return direction;
	}
	
	/*
	 * Compute the repulsion between two states.
	 */
	public int[] stateRepulsion(int[] l1, int[] l2) {
		int[] direction = direction(l1,l2);
		double distance = length(direction);
		if (distance>5) {
			double factor = (repulsion*repulsion) / (distance*distance*distance);
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction = randomShift();
		}
		return direction;
	}
	
	/*
	 * Compute the direction vector between two states.
	 */
	private int[] direction(int[] l1, int[] l2) {
		return new int[] { l1[0]-l2[0], l1[1]-l2[1] } ;
	}
	
	/*
	 * Compute the Euclidean distance between two states.
	 */
	private double length(int[] vector) {
		return Math.sqrt((vector[0] * vector[0]) + (vector[1] * vector[1]));
	}
	
	/*
	 * Compute the number of states to provide storage for.
	 */
	private int velocityArraySize(int states) {
		return (int) (1.5 * states + 2);
	}
	
	/*
	 * Create a random shift.
	 */
	private int[] randomShift() {
		int[] shift = new int[3];
		shift[0] += 5; //10*Math.random()-5;
		shift[1] += 5; //10*Math.random()-5;
		return shift;
	}

	/**
	 * Set the state repulsion force.
	 * @param repulsion State repulsion.
	 */
	public void setStateRepulsion(int repulsion) {
		this.repulsion  = repulsion;
	}
	
	/**
	 * Set the transition attraction force.
	 * @param attraction Attraction force.
	 */
	public void setTransitionAttraction(int attraction) {
		this.attraction = attraction;
	}
	
	/**
	 * Set the natural transition edge length to be used.
	 * @param naturalLength Natural transition length.
	 */
	public void setNaturalTransitionLength(int naturalLength) {
		this.naturalLength = naturalLength;
	}
	
	public void setCenter(int[] center) {
		this.center = center;
	}
	
}
