package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.commands.ExploreStatesCommand;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.CommandStack;

/**
 * State space exploration job.
 * @author Christian Krause
 */
public class ExploreStateSpaceJob  extends Job {
	
	// State space manager.
	private StateSpaceManager manager;
	
	// Edit domain.
	private EditDomain editDomain;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public ExploreStateSpaceJob(StateSpaceManager manager, EditDomain editDomain) {
		super("Explore state space");
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
			
			CommandStack stack = editDomain.getCommandStack();
			
			while (!monitor.isCanceled()) {
				
				// Explore currently open states.
				stack.execute(new ExploreStatesCommand(manager));
				
				// Sleep for a while.
				try {
				Thread.sleep(500);
				} catch (InterruptedException e) {}
			}
			
		} catch (Throwable e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error exploring state space", e);
		}
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
	}
	
	/**
	 * Get the loaded state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
}
