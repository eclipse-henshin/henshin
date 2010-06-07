package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.henshin.statespace.explorer.parts.ExportAsAUTWizard;
import org.eclipse.jface.action.IAction;

/**
 * Action for exporting state space as AUT files.
 * @author Christian Krause
 */
public class ExportAsAUTAction extends AbstractStateSpaceAction {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		ExportAsAUTWizard wizard = new ExportAsAUTWizard();
		// TODO
	}

}
