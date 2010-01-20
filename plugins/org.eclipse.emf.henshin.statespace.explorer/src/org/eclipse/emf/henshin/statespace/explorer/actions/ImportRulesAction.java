package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.henshin.statespace.explorer.parts.StateSpaceExplorer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Action that shows a dialog for importing rules into a statespace.
 * @author Christian Krause
 */
public class ImportRulesAction implements IObjectActionDelegate {
	
	// Currently action state space explorer:
	private StateSpaceExplorer explorer;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Create a new import wizard:
		IWizard wizard = new ImportRulesWizard(explorer);
		
		// Display a dialog:
		Shell shell = explorer.getSite().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.setTitle("Import Rules");
		dialog.open();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {
		explorer = null;
		if (part instanceof StateSpaceExplorer) {
			explorer = (StateSpaceExplorer) part;
		}
		action.setEnabled(explorer!=null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(explorer!=null);
	}
	
	public void setStateSpaceExplorer(StateSpaceExplorer explorer) {
		this.explorer = explorer;
	}
	
}
