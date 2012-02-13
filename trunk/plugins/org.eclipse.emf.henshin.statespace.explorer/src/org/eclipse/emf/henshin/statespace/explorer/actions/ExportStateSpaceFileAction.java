package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbench;

/**
 * Export a state space from a file.
 * @author Christian Krause
 */
public class ExportStateSpaceFileAction extends AbstractStateSpaceFileAction {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {

		// Create the wizard:
		ExportStateSpaceWizard wizard = new ExportStateSpaceWizard();
		IWorkbench workbench = getWorkbenchPart().getSite().getWorkbenchWindow().getWorkbench();
		wizard.setStateSpaceIndex(getStateSpaceManager());
		wizard.init(workbench, (IStructuredSelection) getSelection());
		
		// Wizard dialog:
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.setTitle("Export State Space");
		dialog.open();
		
	}

}
