/**
 * <copyright>
 * OCL2AC is developed by Nebras Nassar based on an initial version developed by Thorsten Arendt and Jan Steffen Becker.
 * </copyright>
 */
package org.eclipse.emf.henshin.ocl2ac.utils.printer.actions;

import java.io.IOException;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification.EquivalencesSimplifier;
import org.eclipse.emf.henshin.ocl2ac.utils.henshin.simplification.HenshinNACSimplifier;

public class HenshinNACSimplifierAction implements IObjectActionDelegate {

	// Workbench part:
	protected IWorkbenchPart workbenchPart;
	// Henshin rule:
	protected Rule rule;

	@Override
	public void run(IAction action) {
		simplifyAC();
	}

	protected void simplifyAC() {

		long start = System.currentTimeMillis();
		if (rule.getLhs().getFormula() != null) {
			HenshinNACSimplifier.eliminateNotNotFromNAC(rule);
			HenshinNACSimplifier.simplifyOrGraphsInAC(rule);
			HenshinNACSimplifier.simplifyAndGraphsInAC(rule);
			HenshinNACSimplifier.eliminateNotNotFromNAC(rule);

			// HenshinModelCleaner reduce the graphs bing isomorphisim
			HenshinModelCleaner.cleanFormula(rule.getLhs().getFormula());

			EquivalencesSimplifier es = new EquivalencesSimplifier(rule.getLhs().getFormula());
			HenshinModelCleaner.cleanFormula(rule.getLhs().getFormula());

			long stop = System.currentTimeMillis();

			try {
				rule.getModule().eResource().save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Simplification", "Simplification is finished. The Simplification time is: ("
							+ ((stop - start) / (double) 1000) + ") second(s).");
		} else {

			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Simplification", "The selected rule has no application condition.");
		}
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
