/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.util;

import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;

/**
 * Spring layout algorithm for state spaces.
 * @author Christian Krause
 * @generated NOT
 */
public class StateSpaceSpringLayouter {
	
	// States to be layouted:
	private List<State> states;
	private int numStates;
	
	// Positions (never null):
	private double[] positionsX = new double[0];
	private double[] positionsY = new double[0];

	// Center of the display:
	private double[] center;

	// Layouting parameters:
	private double repulsion = 50;
	private double attraction = 10;
	private double naturalLength = 30;
		
	/**
	 * Set the state space.
	 * @param stateSpace State space.
	 */
	public void setStateSpace(StateSpace stateSpace) {
		this.states = stateSpace.getStates();
	}
	
	/**
	 * Calculate the new locations of the states.
	 * @return <code>true</code> if at least one state has a velocity that is not zero.
	 */
	public void update() {
		
		// Update the number of states:
		numStates = states.size();
		if (numStates==0) return;
		
		// Make sure there is enough space for the velocities:
		if (numStates>positionsX.length) {
			int size = arraySize(numStates);
			positionsX = new double[size];
			positionsY = new double[size];			
		}
		
		// Update the position of all states:
		for (int i=0; i<numStates; i++) {
			
			State state = states.get(i);
			int[] location = state.getLocation();
			
			if (positionsX[i]>location[0]+1 || positionsX[i]<location[0]-1) {
				positionsX[i] = location[0];
			}
			if (positionsY[i]>location[1]+1 || positionsY[i]<location[1]-1) {
				positionsY[i] = location[1];
			}

		}
		
		// Compute the new positions:
		for (int i=0; i<numStates; i++) {
			
			State state = states.get(i);
			double forceX = 0;
			double forceY = 0;
			
			// State repulsion:
			for (int j=0; j<numStates; j++) {
				if (i==j) continue;
				double[] repulsion = stateRepulsion(i,j);
				forceX += repulsion[0];
				forceY += repulsion[1];
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
				double[] attraction = transitionAttraction(i, otherState.getIndex());
				forceX += attraction[0];
				forceY += attraction[1];
			}
			
			// Update the positions:
			positionsX[i] += forceX;
			positionsY[i] += forceY;
			
		}
		
	}
	
	/**
	 * Commit the new  state locations.
	 */
	public void commit() {
		
		// Update the total number of states:
		numStates = Math.min(states.size(), positionsX.length);
		
		// Update all states:
		for (int i=0; i<numStates; i++) {
				State state = states.get(i);
				int[] location = state.getLocation();
				location[0] = (int) positionsX[i];
				location[1] = (int) positionsY[i];
				state.setLocation(location);
		}
		
	}
	
	/*
	 * Compute the transition attraction between two states.
	 */
	private double[] transitionAttraction(int i1, int i2) {
		double[] direction = direction(i2,i1 );
		double distance = length(direction);
		if (distance>1) {
			double factor = attraction * Math.log(distance / naturalLength) / distance;
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction[0] = 1;
			direction[1] = 1;
		}
		return direction;
	}
	
	/*
	 * Compute the repulsion between two states.
	 */
	public double[] stateRepulsion(int i1, int i2) {
		double[] direction = direction(i1,i2);
		double distance = length(direction);
		if (distance>1) {
			double factor = (repulsion*repulsion) / (distance*distance*distance);
			direction[0] *= factor;
			direction[1] *= factor;
		} else {
			direction[0] = -1;
			direction[1] = -1;
		}
		return direction;
	}
	
	/*
	 * Compute the direction vector between two states.
	 */
	private double[] direction(int i1, int i2) {
		return new double[] { positionsX[i1]-positionsX[i2], positionsY[i1]-positionsY[i2] } ;
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
	private int arraySize(int states) {
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

	public void setCenter(double[] center) {
		this.center = center;
	}
	
}
