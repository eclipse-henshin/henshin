package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.util.concurrent.ExecutionException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;

/**
 * Job for refresh a state space manager.
 * @generated NOT
 * @author Christian Krause
 */
public class StateSpaceManagerRefreshJob extends Job {
	
	// State space manager.
	private StateSpaceManager manager;
	
	// Optional: tools menu.
	private StateSpaceToolsMenu menu;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public StateSpaceManagerRefreshJob(StateSpaceManager manager) {
		super("Refresh state space");
		this.manager = manager;
		setPriority(LONG);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		try {
			
			// Refresh the manager:
			manager.refresh(monitor);
			
			// Refresh the tools menu:
			if (menu!=null && !menu.isDisposed()) {
				menu.getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						menu.setStateSpaceManager(manager);
					}
				});
			}
			
		} catch (ExecutionException e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Error refreshing state space", e);
		}
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
	}
	
	/**
	 * Set the tools menu to be refreshed as well.
	 * @param menu Tools menu.
	 */
	public void setToolsMenu(StateSpaceToolsMenu menu) {
		this.menu = menu;
	}
	
}
