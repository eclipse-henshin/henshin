package org.eclipse.emf.henshin.statespace.explorer.jobs;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
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
public class ExploreOpenStatesJob extends Job {
	
	public static final int STATES_AT_ONCE = 5;
	
	// State space manager.
	protected StateSpaceManager manager;
	
	// Edit domain.
	protected EditDomain editDomain;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public ExploreOpenStatesJob(StateSpaceManager manager, EditDomain editDomain) {
		super("Exploring states");
		this.manager = manager;
		this.editDomain = editDomain;
		setPriority(LONG);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			StateSpace stateSpace = manager.getStateSpace();
			do {
				for (int i=0; i<stateSpace.getOpenStates().size(); i=i+STATES_AT_ONCE) {
					
					// Execute as command:
					Command command = createExploreCommand(i,STATES_AT_ONCE);
					executeExploreCommand(command, monitor);
					
					// Update / check monitor:
					if (monitor.isCanceled()) break;
					monitor.subTask(stateSpace.getStates().size() + " states, " + stateSpace.getOpenStates().size() + " open");
				}
			} while (!stateSpace.getOpenStates().isEmpty() && !monitor.isCanceled());
		} catch (Throwable e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error exploring state space", e);
		}
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
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
	protected Command createExploreCommand(int start, int count) {
		List<State> states = manager.getStateSpace().getOpenStates();
		ExploreStatesCommand command = new ExploreStatesCommand(manager);
		command.getStatesToExplore().clear();
		for (int i=0; i<count; i++) {
			if (start+i>=states.size()) break;
			command.getStatesToExplore().add(states.get(start+i));
		}
		return command;
	}
	
	/**
	 * Get the loaded state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
}
