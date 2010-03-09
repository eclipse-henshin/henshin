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
package org.eclipse.emf.henshin.statespace.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;
import org.eclipse.emf.henshin.statespace.Transition;

/**
 * Multi-threaded version of the basic state space manager.
 * @author Christian Krause
 * @generated NOT
 */
public class MultiThreadedStateSpaceManager extends StateSpaceManagerImpl {

	// Number of threads to be used:
	private int numThreads;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public MultiThreadedStateSpaceManager(StateSpace stateSpace) {
		this(stateSpace,2); // use 2 threads per default
	}

	/**
	 * Alternative constructor.
	 * @param stateSpace State space.
	 * @param numThreads Number of threads to be used.
	 */
	public MultiThreadedStateSpaceManager(StateSpace stateSpace, int numThreads) {
		super(stateSpace);
		this.numThreads = numThreads;
	}
	
	/**
	 * Explore states concurrently.
	 * @param states States to be explored.
	 * @param generatedLocations Whether to generate locations.
	 * @return Newly created transitions.
	 * @throws StateSpaceException On state space errors.
	 */
	public synchronized List<Transition> exploreStates(List<State> states, boolean generatedLocations) throws StateSpaceException {
		
		// Make sure the number of threads is positive.
		if (numThreads<=0) {
			throw new IllegalArgumentException("Number of threads to be used must be greater than 0 (is " + numThreads + ")");
		}
		
		// Thread-safe lists for the states and the result:
		final List<State> syncStates = Collections.synchronizedList(states);
		final List<Transition> syncResult = Collections.synchronizedList(new ArrayList<Transition>());
		
		// Initialize and start threads immediately:
		Worker[] workers = new Worker[numThreads];
		Thread[] threads = new Thread[numThreads];
		for (int i=0; i<numThreads; i++) {
			workers[i] = new Worker(syncStates, syncResult, generatedLocations);
			threads[i] = new Thread(workers[i]);
			threads[i].start();
		}
		
		// Wait for them to finish:
		for (int i=0; i<numThreads; i++) {
			while (threads[i].isAlive()) {
				try {
					threads[i].join();
				} catch (InterruptedException e) {}
			}
		}
		
		// Check if there was an exception:
		for (int i=0; i<numThreads; i++) {
			if (workers[i].getStateSpaceException()!=null) {
				throw workers[i].getStateSpaceException();
			}
		}
		
		// Done:
		return syncResult;
		
	}
	
	/*
	 * Private explorer worker class.
	 */
	private class Worker implements Runnable {
		
		private List<State> states;
		private List<Transition> result;
		private boolean generateLocations;
		private StateSpaceException exception;
		
		Worker(List<State> states, List<Transition> result, boolean generateLocations) {
			this.states = states;
			this.result = result;
			this.generateLocations = generateLocations;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			try {
				while (true) {
					result.addAll(exploreState(states.remove(0),generateLocations));
				}
			} catch (IndexOutOfBoundsException e) {
				return; // we are done
			} catch (StateSpaceException e) {
				this.exception = e;
			}
		}
		
		public StateSpaceException getStateSpaceException() {
			return exception;
		}
	}
	
}
