/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.utils.printer.NestedConditionPrinter;
import nestedcondition.NestedConstraint;

public class NGCLaxGenerationActionShortVersion implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;

	// Nested graph constraint:
	protected NestedConstraint constraint;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		print();
	}

	/*
	 * Print the graph constraint to the console
	 */
	protected void print() {
		if (constraint != null) {
			NestedConditionPrinter nestedConditionPrinter = new NestedConditionPrinter(constraint, true);
			nestedConditionPrinter.printDocument();
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Info",
					"The latex file for presenting the graph constranit is generated. It can be found in the tex folder in your project");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		constraint = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof NestedConstraint) {
				constraint = (NestedConstraint) first;
				System.out.println(constraint.getName() + " " + constraint.eClass().getEPackage().getName());
			}
		}
		action.setEnabled(constraint != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {

	}

}
