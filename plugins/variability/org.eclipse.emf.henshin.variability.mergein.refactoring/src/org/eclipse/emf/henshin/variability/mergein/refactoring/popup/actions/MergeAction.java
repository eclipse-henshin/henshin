package org.eclipse.emf.henshin.variability.mergein.refactoring.popup.actions;

import org.eclipse.emf.henshin.variability.mergein.refactoring.logic.MergeInException;
import org.eclipse.emf.henshin.variability.mergein.refactoring.logic.NewMerger;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeSuggestion;

public class MergeAction implements IObjectActionDelegate {
	
	private Shell shell;
	private MergeSuggestion mergeSuggestion;
	private MergeRule mergeRule;
	
	private static final String HEADER = "MergeIn refactoring";


	
	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		mergeRule = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof MergeRule) {
				mergeRule = (MergeRule) structuredSelection.getFirstElement();
			}
		}
	}

	@Override
	public void run(IAction action) {
		try {
			NewMerger merger = null;
			if (mergeSuggestion != null) {
				for (MergeRule mergeRule : mergeSuggestion.getMergeClusters()) {
					merger = new NewMerger(mergeRule, true);
					merger.merge();
				}
			} else if (mergeRule != null) {
				merger = new NewMerger(mergeRule, true);
				merger.merge();
			}
		} catch (MergeInException e) {
			e.printStackTrace();
			MessageDialog.openError(shell, HEADER, e.getMessage());
		}
	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	public void setMergeSuggestion(MergeSuggestion mergeSuggestion) {
		this.mergeSuggestion = mergeSuggestion;
	}
	
}