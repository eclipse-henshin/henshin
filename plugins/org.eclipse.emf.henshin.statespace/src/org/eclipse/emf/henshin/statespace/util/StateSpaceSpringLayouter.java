package org.eclipse.emf.henshin.statespace.util;

import java.util.Arrays;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.impl.StateAttributes;

/**
 * Spring layout algorithm for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceSpringLayouter {

	private double damping = 0.99;

	private StateSpace stateSpace;

	private int[] velocitiesX;
	private int[] velocitiesY;


	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
		if (stateSpace!=null) {
			int size = velocityArraySize(stateSpace.getStates().size());
			velocitiesX = new int[size];
			velocitiesY = new int[size];
		} else {
			velocitiesX = null;
			velocitiesY = null;
		}
	}

	public void update() {

		// Total number of states:
		int states = stateSpace.getStates().size();
		if (states==0) return;

		// Make sure there is enough space for the velocities:
		if (states>velocitiesX.length) {
			velocitiesX = Arrays.copyOf(velocitiesX, velocityArraySize(states));
			velocitiesY = Arrays.copyOf(velocitiesY, velocityArraySize(states));
		}

		for (int i=0; i<states; i++) {

			State state = stateSpace.getStates().get(i);
			int[] netForce = new int[2];

			// Coloumb repulsion:
			for (int j=0; j<states; j++) {

				if (i==j) continue;            
				State otherState = stateSpace.getStates().get(j);

				int[] repulsion = coloumbRepulsion(state,otherState);
				//System.out.println("Repulsion: " + Arrays.toString(repulsion));
				netForce[0] += repulsion[0];
				netForce[1] += repulsion[1];

			}

			// Hook attraction:
			int incoming = state.getIncoming().size();
			int links = incoming + state.getOutgoing().size();
			for (int j=0; j<links; j++) {

				State otherState = (j<incoming) ? 
						state.getIncoming().get(j).getSource() : 
						state.getOutgoing().get(j-incoming).getTarget();

				if (state==otherState) continue;

				int[] attraction = hookeAttraction(state, otherState);
				//System.out.println("Attraction: " + Arrays.toString(attraction));
				netForce[0] += attraction[0];
				netForce[1] += attraction[1];

			}

			velocitiesX[i] = (int) ((velocitiesX[i] + netForce[0]) * damping);
			velocitiesY[i] = (int) ((velocitiesY[i] + netForce[1]) * damping);

			StateAttributes.move(state, velocitiesX[i], velocitiesY[i]);
			
		}
		
	}

	private int[] hookeAttraction(State s1, State s2) {
		int[] direction = direction(s2,s1);
		double distance = length(direction);
		System.out.println(distance);
		if (distance>5) {
			double factor = 0.1 * Math.log(distance / 50);
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
		if (distance>5) {
			double factor = 10000 / (distance*distance*distance);
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
}
