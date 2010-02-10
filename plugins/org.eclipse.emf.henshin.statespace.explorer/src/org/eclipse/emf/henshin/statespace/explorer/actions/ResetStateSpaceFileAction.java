package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Action for resetting state space files.
 * @author Christian Krause
 */
public class ResetStateSpaceFileAction extends AbstractStateSpaceFileAction {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		boolean confirmed = MessageDialog.openConfirm(getShell(), "Reset State Space", "All derived states will be deleted. Really continue?");
		if (confirmed) {
			getStateSpaceManager().resetStateSpace();
			saveStateSpace();
		}
		
	}

}
