package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.util.StateSpaceSpringLayouter;
import org.eclipse.swt.widgets.Display;

/**
 * Layouter job for state spaces.
 * @author Christian Krause
 */
public class StateSpaceLayouterJob extends Job {
	
	// Layouter:
	private StateSpaceSpringLayouter layouter;
	
	// Display:
	private Display display;
	
	/**
	 * Default constructor.
	 * @param stateSpace State space.
	 * @param display Display.
	 */
	public StateSpaceLayouterJob(StateSpace stateSpace, Display display) {
		
		super("Layouting state space");
		setPriority(LONG);
		this.display = display;
		
		// Create layouter:
		layouter = new StateSpaceSpringLayouter();
		layouter.setStateSpace(stateSpace);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		
		while (!monitor.isCanceled()) {
			
			// Update velocities:
			boolean changed = layouter.updateVelocities();
			
			// Update locations:
			if (changed) display.asyncExec(new Runnable() {
				public void run() {
					layouter.updateLocations();
				}
			});
			
			// Sleep:
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {}
			
		}
		
		return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
		
	}
	
	public StateSpaceSpringLayouter getLayouter() {
		return layouter;
	}
	
}
