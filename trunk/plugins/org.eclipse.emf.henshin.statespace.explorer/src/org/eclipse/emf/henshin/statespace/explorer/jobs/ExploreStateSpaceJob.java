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
package org.eclipse.emf.henshin.statespace.explorer.jobs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.commands.ExploreStatesCommand;
import org.eclipse.emf.henshin.statespace.impl.AbstractStateSpaceManagerWithStateDistance;
import org.eclipse.emf.henshin.statespace.impl.MultiThreadedStateSpaceManager;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;

/**
 * State space exploration job.
 * @author Christian Krause
 */
public class ExploreStateSpaceJob extends AbstractStateSpaceJob {
	
	/**
	 * Default number of states to be explored at once.
	 */
	public static final int DEFAULT_NUM_STATES_AT_ONCE = 40;
	
	// Edit domain.
	protected EditDomain editDomain;
	
	// Number of states to be explored at once.
	private int numStatesAtOnce = DEFAULT_NUM_STATES_AT_ONCE;

	// Clean up interval (default is 10 minutes):
	private int cleanupInterval = 600;

	// Save interval (default is 30 minutes):
	private int saveInterval = 1800;

	// Whether to log some info after the exploration is finished.
	protected boolean logInfo = true;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public ExploreStateSpaceJob(StateSpaceManager manager, EditDomain editDomain) {
		super("Exploring state space...", manager);
		this.editDomain = editDomain;
		setUser(true);
		setPriority(LONG);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		// Explore the state space...
		monitor.beginTask("Exploring state space...", IProgressMonitor.UNKNOWN);
		
		// State space manager and state space:
		StateSpaceManager manager = (StateSpaceManagerImpl) getStateSpaceManager();
		StateSpace stateSpace = manager.getStateSpace();
		
		DecimalFormat large = new DecimalFormat("#,###,###,##0");
		DecimalFormat speed = new DecimalFormat("###,##0.0");
		
		// Measure how long it takes...
		long start = System.currentTimeMillis();
		long lastSave = start;
		long lastCleanup = start;
		int initialStateCount = stateSpace.getStates().size();

		try {
			
			// List of states to be explored:
			List<State> statesToExplore = getStatesToExplore();
			
			// Run until canceled or no more open states...
			while (!statesToExplore.isEmpty() && !monitor.isCanceled()) {
				
				// Explore all open states:
				for (int index=0; index<statesToExplore.size(); index=index+numStatesAtOnce) {
					
					// Execute as explore command:
					ExploreStatesCommand command = createExploreCommand(statesToExplore, index, numStatesAtOnce);
					executeExploreCommand(command, monitor);
					
					// Update the monitor:
					int states = stateSpace.getStates().size();
					long time = System.currentTimeMillis();
					monitor.subTask(large.format(states) + " states ("
							+ large.format(stateSpace.getOpenStates().size()) + " open), " 
							+ large.format(stateSpace.getTransitionCount()) + " transitions. Exploring "
							+ speed.format((double) (1000 * (states - initialStateCount)) / (double) (time - start)) + " states/second...");

					// Should we stop?
					if (monitor.isCanceled()) break;

					// Perform a clean up?
					long current = System.currentTimeMillis();
					if (cleanupInterval>=0 && current > (lastCleanup + (cleanupInterval*1000))) {
						clearCache(monitor);
						lastCleanup = System.currentTimeMillis();
					}

					// Perform a save?
					if (saveInterval>=0 && current > (lastSave + (saveInterval*1000))) {
						monitor.subTask("Saving state space...");
						saveStateSpace();
						lastSave = System.currentTimeMillis();
					}
					
					// Stop now?
					if (monitor.isCanceled()) break;
					
				}
				
				// Update the list of states to explore:
				statesToExplore = getStatesToExplore();
				
			}
		
		} catch (Throwable e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error exploring state space", e);
		}
		
		// Measure time again:
		long end = System.currentTimeMillis();
		
		// Clear some memory before we do the saving:
		clearCache(monitor);
		
		// Save the state space:
		if (saveInterval>=0) {
			monitor.subTask("Saving state space...");
			saveStateSpace();
		}
		
		// Final message:
		if (logInfo) {
			boolean multiThreaded = (manager instanceof MultiThreadedStateSpaceManager);
			int explored = stateSpace.getStates().size() - initialStateCount;
			String statesPerSec = "";
			if (end>start) {
				statesPerSec = " (" + speed.format((double) (1000 * explored) / (double) (end-start)) + " states/second)";
			}
			StateSpaceExplorerPlugin.getInstance().logInfo(
					"Explored " + explored + " states in " + ((end-start)/1000) + " seconds" +
					statesPerSec + " in " +
					(multiThreaded ? "multi" : "single") + "-threaded mode.");
		}
		
		// Now we are done:
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
		
	}
	
	private List<State> getStatesToExplore() {
		List<State> statesToExplore = new ArrayList<State>();
		if (getStateSpaceManager() instanceof AbstractStateSpaceManagerWithStateDistance) {
			AbstractStateSpaceManagerWithStateDistance manager = 
					(AbstractStateSpaceManagerWithStateDistance) getStateSpaceManager();
			int maxStateDistance = manager.getStateSpace().getMaxStateDistance();
			for (State open : manager.getStateSpace().getOpenStates()) {
				if (maxStateDistance<0 || maxStateDistance>manager.getStateDistance(open)) {
					statesToExplore.add(open);					
				}
			}
		} else {
			statesToExplore.addAll(getStateSpaceManager().getStateSpace().getOpenStates());
		}
		return statesToExplore;
	}
	
	/*
	 * Clear state model cache.
	 */
	private void clearCache(IProgressMonitor monitor) {
		if (getStateSpaceManager() instanceof StateSpaceManagerImpl) {
			monitor.subTask("Clearing state model cache...");
			((StateSpaceManagerImpl) getStateSpaceManager()).clearStateModelCache();
		}
	}
	
	/*
	 * Execute an explore command. Subclasses can override this.
	 */
	protected void executeExploreCommand(final Command command, IProgressMonitor monitor) {
		editDomain.getCommandStack().execute(command);
	}
	
	/*
	 * Create a new explore-command.
	 */
	protected ExploreStatesCommand createExploreCommand(List<State> states, int start, int count) {
		int end = Math.min(start + count, states.size());
		ExploreStatesCommand command = new ExploreStatesCommand(getStateSpaceManager(), states.subList(start, end));
		command.setGenerateLocations(false);
		return command;
	}
	
	/**
	 * Set the number of states to be explored at once.
	 * @param num Number of states.
	 */
	public void setNumStatesAtOnce(int num) {
		this.numStatesAtOnce = num;
	}
	
	/**
	 * Set the save interval in seconds.
	 * @param seconds Save interval.
	 */
	public void setSaveInterval(int seconds) {
		this.saveInterval = seconds;
	}

	/**
	 * Set the cleanup interval in seconds.
	 * @param seconds Cleanup interval.
	 */
	public void setCleanupInterval(int seconds) {
		this.cleanupInterval = seconds;
	}

}
