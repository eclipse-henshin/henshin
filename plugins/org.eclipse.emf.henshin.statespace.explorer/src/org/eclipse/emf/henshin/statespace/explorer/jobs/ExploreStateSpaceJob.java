package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.commands.ExploreStatesCommand;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.swt.widgets.Display;

/**
 * State space exploration job.
 * @author Christian Krause
 */
public class ExploreStateSpaceJob extends Job {
	
	// State space manager.
	private StateSpaceManager manager;
	
	// Edit domain.
	private EditDomain editDomain;
	
	// Delay in milliseconds.
	private int delay = 1500;
	
	// Execution flag:
	private boolean executing;
	
	
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
			
			final Display display = Display.getDefault();
			final CommandStack stack = editDomain.getCommandStack();
			
			while (!monitor.isCanceled()) {
				
				// Create a command and check if it can be executed:
				final Command command = new ExploreStatesCommand(manager);
				if (!command.canExecute()) break;
				
				// Execute the command in the display-thread.
				executing = true;
				display.asyncExec(new Runnable() {
					public void run() {
						stack.execute(command);
						executing = false;
					}
				});

				// Sleep until done:
				while (executing) sleep(20);
				
				// Sleep more.
				sleep(delay);
				
			}
			
		} catch (Throwable e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error exploring state space", e);
		}
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
	}
	
	/*
	 * Sleep for some time.
	 */
	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
	
	/**
	 * Get the loaded state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
	/**
	 * Set the delay.
	 * @param delay Delay.
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
}
