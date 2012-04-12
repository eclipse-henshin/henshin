/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.impl;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceException;

/**
 * Multi-threaded version of the basic state space manager.
 * @author Christian Krause
 * @generated NOT
 */
public class MultiThreadedStateSpaceManager extends StateSpaceManagerImpl {
	
	// Number of threads to be used.
	private int numWorkers;
	
	// Executor service.
	private ExecutorService executor;
	
	// Future objects.
	private Future<StateSpaceException>[] futures;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 * @param numThreads Number of threads to be used.
	 */
	@SuppressWarnings("unchecked")
	public MultiThreadedStateSpaceManager(StateSpace stateSpace, int numThreads) {
		super(stateSpace);
		this.numWorkers = Math.max(numThreads, 1);
		this.executor = Executors.newFixedThreadPool(numWorkers);
		this.futures = new Future[numWorkers];
	}

	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 */
	public MultiThreadedStateSpaceManager(StateSpace stateSpace) {
		this(stateSpace, Runtime.getRuntime().availableProcessors());
	}

	/**
	 * Explore states concurrently.
	 * @param states States to be explored.
	 * @param generateLocations Whether to generate locations.
	 * @return Newly created successor states.
	 * @throws StateSpaceException On state space errors.
	 */
	@Override
	public synchronized List<State> exploreStates(List<State> states, boolean generateLocations) throws StateSpaceException {

		// We use a new list for the states:
		List<State> queue = new Vector<State>(states);
		List<State> result = new Vector<State>();
		
		try {
			// Launch the workers:
			for (int i=0; i<numWorkers; i++) {
				futures[i] = executor.submit(new ExplorationWorker(queue, result, generateLocations));
			}
			// Evaluate the results:
			for (int i=0; i<numWorkers; i++) {
				if (futures[i].get()!=null) {
					throw futures[i].get();
				}
			}
		} catch (Throwable t) {
			throw wrapException(t);
		}
		
		// Done:
		return result;
		
	}
	
	/*
	 * Wrap an exception.
	 */
	private StateSpaceException wrapException(Throwable t) {
		return (t instanceof StateSpaceException) ? (StateSpaceException) t : new StateSpaceException(t);
	}
	
	/*
	 * Private explorer worker class. Delegates to exploreState().
	 */
	private class ExplorationWorker implements Callable<StateSpaceException> {
		
		// States to be explored:
		private List<State> states;
		
		// Result list:
		private List<State> result;
		
		// Whether to generate locations:
		private boolean generateLocations;
		
		/*
		 * Default constructor.
		 */
		ExplorationWorker(List<State> states, List<State> result, boolean generateLocations) {
			this.states = states;
			this.result = result;
			this.generateLocations = generateLocations;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public StateSpaceException call() {
			
			while (true) {

				// Get the next state to be explored:
				State next;
				try {
					next = states.remove(0);
				}
				catch (IndexOutOfBoundsException e) {
					return null; // We are done.
				}

				// Now explore it:
				try {
					result.addAll(exploreState(next, generateLocations));
				}
				catch (Throwable t) {
					return wrapException(t);
				}
			}
			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.StateSpaceManager#getNumThreads()
	 */
	@Override
	public int getNumThreads() {
		return numWorkers;
	}

}
