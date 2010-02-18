package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Spring layout algorithm for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceSpringLayouter {
	
	// State space to be layouted:
	private StateSpace stateSpace;
	
	// Velocities:
	private double[] velocitiesX;
	private double[] velocitiesY;
	
	// Layouting parameters:
	private double repulsion = 50;
	private double attraction = 10;
	private double naturalLength = 50;
	private double damping = 1;
	
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
			velocitiesX = new double[size];
			velocitiesY = new double[size];			
		}
		
		boolean changed = false;
		
		for (int i=0; i<states; i++) {
			
			velocitiesX[i] = 0;
			velocitiesY[i] = 0;
			
			State state = stateSpace.getStates().get(i);
			int[] location = state.getLocation();
			double[] netForce = new double[2];
			
			// State repulsion:
			for (int j=0; j<states; j++) {
				
				if (i==j) continue;            
				State otherState = stateSpace.getStates().get(j);
				
				double[] repulsion = stateRepulsion(location, otherState.getLocation());
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
				double[] attraction = transitionAttraction(location, otherState.getLocation());
				netForce[0] += attraction[0];
				netForce[1] += attraction[1];
				
			}
			
			// Add the attraction to the center:
			if (center!=null) {
				double[] attraction = transitionAttraction(location, center);
				netForce[0] += attraction[0];
				netForce[1] += attraction[1];
			}
			
			// Update the velocities:
			velocitiesX[i] = netForce[0] * damping;
			velocitiesY[i] = netForce[1] * damping;
			
			// Check if something changed:
			changed = changed || Math.abs(velocitiesX[i])>0 || Math.abs(velocitiesY[i])>0;
			
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
	private double[] transitionAttraction(int[] l1, int[] l2) {
		double[] direction = direction(l2,l1);
		double distance = length(direction);
		if (distance>naturalLength) {
			double factor = attraction * Math.log(distance / naturalLength) / distance;
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction[0] = 1;
			direction[1] = 0;
		}
		return direction;
	}
	
	/*
	 * Compute the repulsion between two states.
	 */
	public double[] stateRepulsion(int[] l1, int[] l2) {
		double[] direction = direction(l1,l2);
		double distance = length(direction);
		if (distance>10) {
			double factor = (repulsion*repulsion) / (distance*distance*distance);
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction[0] = -1;
			direction[1] = 0;
		}
		return direction;
	}
	
	/*
	 * Compute the direction vector between two states.
	 */
	private double[] direction(int[] l1, int[] l2) {
		return new double[] { l1[0]-l2[0], l1[1]-l2[1] } ;
	}
	
	/*
	 * Compute the Euclidean distance between two states.
	 */
	private double length(double[] vector) {
		return Math.sqrt((vector[0] * vector[0]) + (vector[1] * vector[1]));
	}
	
	/*
	 * Compute the number of states to provide storage for.
	 */
	private int velocityArraySize(int states) {
		return (int) (1.5 * states + 2);
	}
	
	/**
	 * Set the state repulsion force.
	 * @param repulsion State repulsion.
	 */
	public void setStateRepulsion(double repulsion) {
		this.repulsion  = repulsion;
	}
	
	/**
	 * Set the transition attraction force.
	 * @param attraction Attraction force.
	 */
	public void setTransitionAttraction(double attraction) {
		this.attraction = attraction;
	}
	
	/**
	 * Set the natural transition edge length to be used.
	 * @param naturalLength Natural transition length.
	 */
	public void setNaturalTransitionLength(int naturalLength) {
		this.naturalLength = naturalLength;
	}
	
	public void setDamping(double damping) {
		this.damping = damping;
	}
	
	public void setCenter(int[] center) {
		this.center = center;
	}
	
}
