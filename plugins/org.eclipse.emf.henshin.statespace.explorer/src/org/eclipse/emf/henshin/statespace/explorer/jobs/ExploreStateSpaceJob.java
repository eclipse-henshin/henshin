package org.eclipse.emf.henshin.statespace.explorer.jobs;

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
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;

/**
 * State space exploration job.
 * @author Christian Krause
 */
public class ExploreStateSpaceJob extends AbstractStateSpaceJob {
	
	// Edit domain.
	protected EditDomain editDomain;
	
	// Number of states to be explored at once.
	private int numStatesAtOnce = 5;
	
	// Save interval (default is 5 minutes):
	private int saveInterval = 300; 

	// Clean up interval (default is 15 minutes):
	private int cleanupInterval = 900; 

	
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
		StateSpace stateSpace = getStateSpaceManager().getStateSpace();
		
		try {
			
			// Measure how long it takes...
			long lastSave = System.currentTimeMillis();
			long lastCleanup = System.currentTimeMillis();
			
			// Run until canceled or no more open states...
			while (!stateSpace.getOpenStates().isEmpty() && !monitor.isCanceled()) {
				
				// Currently open states:
				List<State> open = new ArrayList<State>(stateSpace.getOpenStates()); 
				
				// Explore all open states:
				for (int index=0; index<open.size(); index=index+numStatesAtOnce) {
					
					// Update the monitor:
					monitor.subTask("State space has " + stateSpace.getStates().size() + " states ("
							+ stateSpace.getOpenStates().size() + " open) and " + stateSpace.getTransitionCount() + " transitions");
					
					// Execute as explore command:
					Command command = createExploreCommand(open, index, numStatesAtOnce);
					executeExploreCommand(command, monitor);
					
					// Should we stop?
					if (monitor.isCanceled()) break;
					
					// Perform a save?
					if (saveInterval>=0 && System.currentTimeMillis() > (lastSave + (saveInterval*1000))) {
						monitor.subTask("Saving state space...");
						saveStateSpace();
						lastSave = System.currentTimeMillis();
					}
					
					// Perform a clean up?
					if (cleanupInterval>=0 && System.currentTimeMillis() > (lastCleanup + (cleanupInterval*1000))) {
						monitor.subTask("Clearing cache...");
						clearCache();
						lastCleanup = System.currentTimeMillis();
					}

					// Stop now?
					if (monitor.isCanceled()) break;
					
				}
				
			}
		
		} catch (Throwable e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error exploring state space", e);
		}
		
		// Save the state space and clean up:
		if (saveInterval>=0) {
			monitor.subTask("Saving state space...");
			saveStateSpace();
		}
		
		// Now we are done:
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
		
	}
	
	/*
	 * Clear the state space cache. Resets all state models to null.
	 */
	private void clearCache() {
		for (State state : getStateSpaceManager().getStateSpace().getStates()) {
			if (!state.isInitial()) {
				state.setModel(null);
			}
		}
		System.gc();
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
		int end = Math.min(start + count + 1, states.size());
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
