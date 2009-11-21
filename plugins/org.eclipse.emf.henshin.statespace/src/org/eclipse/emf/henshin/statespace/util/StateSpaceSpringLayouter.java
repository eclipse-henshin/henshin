package org.eclipse.emf.henshin.statespace.util;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.impl.StateAttributes;

/**
 * Spring layout algorithm for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceSpringLayouter {

	private StateSpace stateSpace;
	
	private int[] velocitiesX;
	private int[] velocitiesY;
	
	private int repulsion = 50;
	private int attraction = 10;
	private int naturalLength = 50;
	
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}

	
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
			int[] netForce = new int[2];
			
			// State repulsion:
			for (int j=0; j<states; j++) {
				
				if (i==j) continue;            
				State otherState = stateSpace.getStates().get(j);
				
				int[] repulsion = coloumbRepulsion(state,otherState);
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
				int[] attraction = transitionAttraction(state, otherState);
				netForce[0] += attraction[0];
				netForce[1] += attraction[1];
				
			}
			
			// Update the velocities:
			velocitiesX[i] = netForce[0];
			velocitiesY[i] = netForce[1];
			
			// Check if something changed:
			if (!changed) {
				changed = velocitiesX[i]!=0 || velocitiesY[i]!=0;
			}
			
		}
		
		return changed;
		
	}

	
	public void updateLocations() {

		// Total number of states:
		if (velocitiesX==null) return;
		int states = Math.min(stateSpace.getStates().size(), velocitiesX.length);
		if (states==0) return;
		
		// Update all states:
		for (int i=0; i<states; i++) {
			State state = stateSpace.getStates().get(i);
			StateAttributes.move(state, velocitiesX[i], velocitiesY[i]);
		}
		
	}
	
	
	private int[] transitionAttraction(State s1, State s2) {
		int[] direction = direction(s2,s1);
		double distance = length(direction);
		if (distance>1) {
			double factor = attraction * Math.log(distance / naturalLength) / distance;
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction = random();
		}
		return direction;
	}


	public int[] coloumbRepulsion(State s1, State s2) {
		int[] direction = direction(s1,s2);
		double distance = length(direction);
		if (distance>1) {
			double factor = (repulsion*repulsion) / (distance*distance*distance);
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction = random();
		}
		return direction;
	}

	private int[] direction(State s1, State s2) {
		int[] l1 = StateAttributes.getLocation(s1);
		int[] l2 = StateAttributes.getLocation(s2);
		l1[0] -= l2[0];
		l1[1] -= l2[1];
		return l1;
	}

	private double length(int[] vector) {
		return Math.sqrt((vector[0] * vector[0]) + (vector[1] * vector[1]));
	}

	private int velocityArraySize(int states) {
		return (int) (1.5 * states + 2);
	}
	
	private int[] random() {
		int[] random = new int[2];
		random[0] += 10*Math.random()-5;
		random[1] += 10*Math.random()-5;
		return random;
	}


	public void setStateRepulsion(int repulsion) {
		this.repulsion  = repulsion;
	}

	public void setTransitionAttraction(int attraction) {
		this.attraction = attraction;
	}

	public void setNaturalTransitionLength(int naturalLength) {
		this.naturalLength = naturalLength;
	}

}
