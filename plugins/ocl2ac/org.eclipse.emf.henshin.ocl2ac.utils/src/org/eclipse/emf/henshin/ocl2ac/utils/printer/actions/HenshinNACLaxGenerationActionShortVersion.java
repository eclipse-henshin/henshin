/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer.actions;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.utils.printer.HenshinNACPrinter;

public class HenshinNACLaxGenerationActionShortVersion implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;
	// Henshin rule:
	protected Rule rule;

	@Override
	public void run(IAction action) {
		print();
	}

	protected void print() {
		HenshinNACPrinter henshinNACPrinter = new HenshinNACPrinter(rule, rule.eClass().getEPackage(), true);
		henshinNACPrinter.printDocument();
		MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Info",
				"The latex file for presenting the application condition of the rule is generated. It can be found in the tex folder in your project");

	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		rule = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof Rule) {
				rule = (Rule) first;
			}
		}
		action.setEnabled(rule != null);
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart part) {
		workbenchPart = (part instanceof HenshinEditor) ? part : null;
		action.setEnabled(workbenchPart != null);
	}

}
