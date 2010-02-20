package org.eclipse.emf.henshin.statespace.explorer.jobs;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.ExploreStatesCommand;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;

/**
 * Explore open states job with display support.
 * @author Christian Krause
 */
public class ExploreStateSpaceJobWithDisplay extends ExploreStateSpaceJob {

	// Execution flag:
	private boolean executing;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 * @param editDomain Edit domain.
	 */
	public ExploreStateSpaceJobWithDisplay(StateSpaceManager manager, EditDomain editDomain) {
		super(manager, editDomain);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.jobs.ExploreStateSpaceJob#createExploreCommand(java.util.List, int, int)
	 */
	@Override
	protected ExploreStatesCommand createExploreCommand(List<State> states, int start, int count) {
		ExploreStatesCommand command = super.createExploreCommand(states, start, count);
		command.setGenerateLocations(true);
		return command;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.statespace.explorer.jobs.ExploreOpenStatesJob#executeExploreCommand(org.eclipse.gef.commands.Command, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected void executeExploreCommand(final Command command, IProgressMonitor monitor) {

		// Execute the command in the display-thread.
		executing = true;
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				editDomain.getCommandStack().execute(command);
				executing = false;
			}
		});
		
		// Sleep until done:
		do {
			for (int j=0; j<15; j++) {
				try { Thread.sleep(50); } 
				catch (InterruptedException e) {}
				if (monitor.isCanceled()) break;
			}
		} while (executing);

	}

}
