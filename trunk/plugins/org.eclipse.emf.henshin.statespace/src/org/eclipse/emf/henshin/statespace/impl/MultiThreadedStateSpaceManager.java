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
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
	
	/**
	 * Cached number of available processors.
	 */
	public static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	
	// Number of threads to be used:
	private int numThreads;
	
	// Executor service.
	private ExecutorService executor;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public MultiThreadedStateSpaceManager(StateSpace stateSpace) {
		this(stateSpace, CPU_COUNT);
	}

	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 * @param numThreads Number of threads to be used.
	 */
	public MultiThreadedStateSpaceManager(StateSpace stateSpace, int numThreads) {
		super(stateSpace);
		this.numThreads = Math.max(numThreads, 1);
		this.executor = Executors.newFixedThreadPool(this.numThreads);			
	}

	/**
	 * Explore states concurrently.
	 * @param states States to be explored.
	 * @param generateLocations Whether to generate locations.
	 * @return Newly created transitions.
	 * @throws StateSpaceException On state space errors.
	 */
	public synchronized List<Transition> exploreStates(List<State> states, boolean generateLocations) throws StateSpaceException {
		
		// We use a new list for the states:
		List<State> queue = new ArrayList<State>(states);
		List<Transition> result = new ArrayList<Transition>();
		
		// Create the workers:
		List<Worker> workers = new ArrayList<Worker>(numThreads);
		for (int i=0; i<numThreads; i++) {
			workers.add(new Worker(queue, result, generateLocations));
		}
		
		// Execute the workers:
		try {
			List<Future<StateSpaceException>> futures = executor.invokeAll(workers);
			for (Future<StateSpaceException> future : futures) {
				StateSpaceException exception = future.get();
				if (exception!=null) throw exception;
			}
		} catch (Throwable t) {
			throw new StateSpaceException(t);
		}
		
		// Done:
		return result;
		
	}
	
	/*
	 * Private explorer worker class. Delegates to exploreState().
	 */
	private class Worker implements Callable<StateSpaceException> {
		
		// States to be explored:
		private List<State> states;
		
		// Result list:
		private List<Transition> result;
		
		// Whether to generate locations:
		private boolean generateLocations;
		
		/**
		 * Default constructor.
		 * @param states States to be explored.
		 * @param result Result list.
		 * @param generateLocations Whether to generate locations.
		 */
		Worker(List<State> states, List<Transition> result, boolean generateLocations) {
			this.states = states;
			this.result = result;
			this.generateLocations = generateLocations;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public StateSpaceException call() throws Exception {
			
			while (true) {

				// Get the next state to be explored:
				State next;
				try {
					synchronized (states) {
						next = states.remove(0);
					}
				} catch (IndexOutOfBoundsException e) {
					return null; // We are done.
				}

				// Now explore it:
				try {
					List<Transition> transitions = exploreState(next, generateLocations);
					synchronized (result) {
						result.addAll(transitions);							
					}
				} catch (Throwable t) {
					return (t instanceof StateSpaceException) ? 
							(StateSpaceException) t : new StateSpaceException(t);
				}
			}
			
		}
	}
	
}
