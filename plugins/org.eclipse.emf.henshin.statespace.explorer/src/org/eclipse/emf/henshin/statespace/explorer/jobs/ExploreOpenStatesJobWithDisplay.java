package org.eclipse.emf.henshin.statespace.explorer.jobs;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Display;

/**
 * Explore open states job with display support.
 * @author Christian Krause
 */
public class ExploreOpenStatesJobWithDisplay extends ExploreOpenStatesJob {

	// Execution flag:
	private boolean executing;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 * @param editDomain Edit domain.
	 */
	public ExploreOpenStatesJobWithDisplay(StateSpaceManager manager, EditDomain editDomain) {
		super(manager, editDomain);
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
		while (executing) {
			try {
				for (int j=0; j<10; j++) {
					Thread.sleep(50);
					if (monitor.isCanceled()) break;
				}
			} catch (InterruptedException e) {}
		}

	}

}
