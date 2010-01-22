package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.TaintedStateSpaceException;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl;

/**
 * Job for loading a state space manager.
 * @author Christian Krause
 */
public class LoadStateSpaceManagerJob extends Job {
	
	// State space.
	private StateSpace stateSpace;
	
	// State space manager.
	private StateSpaceManager manager;
	
	// Optional: tools menu.
	private StateSpaceToolsMenu menu;
	
	/**
	 * Default constructor.
	 * @param manager State space manager.
	 */
	public LoadStateSpaceManagerJob(StateSpace stateSpace) {
		super("Load state space manager");
		this.stateSpace = stateSpace;
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
			manager = StateSpaceManagerImpl.load(stateSpace, monitor);
			
			// Refresh the tools menu:
			if (menu!=null && !menu.isDisposed()) {
				menu.getShell().getDisplay().asyncExec(new Runnable() {
					public void run() {
						menu.setStateSpaceManager(manager);
					}
				});
			}
			
		} catch (TaintedStateSpaceException e) {
			return new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, "Tainted state space", e);
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
	
	/**
	 * Get the loaded state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}
	
}
