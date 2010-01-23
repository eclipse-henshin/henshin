package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Action that shows a dialog for importing rules into a statespace.
 * @author Christian Krause
 */
public class ImportRulesAction extends AbstractExplorerAction {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Create a new import wizard:
		IWizard wizard = new ImportRulesWizard(getExplorer());
		
		// Display a dialog:
		Shell shell = getExplorer().getSite().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.setTitle("Import Rules");
		dialog.open();
		
	}
	
}
