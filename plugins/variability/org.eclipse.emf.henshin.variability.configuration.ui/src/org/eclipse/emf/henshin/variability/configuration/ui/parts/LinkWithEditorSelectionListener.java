package org.eclipse.emf.henshin.variability.configuration.ui.parts;

import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.gef.editparts.AbstractEditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * @author Stefan Schulz
 *
 */
public class LinkWithEditorSelectionListener implements ISelectionListener {

	private final ILinkedWithEditorView view;

	public LinkWithEditorSelectionListener(ILinkedWithEditorView view) {
		this.view = view;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		RuleEditPart selectedRuleEditPart = null;

		if (selection instanceof IStructuredSelection && ((IStructuredSelection) selection).size() == 1) {
			Object firstElement = ((IStructuredSelection) selection).getFirstElement();
			if(!(firstElement instanceof AbstractEditPart)) {
				return;
			}
			while (!(firstElement instanceof RuleEditPart) && ((AbstractEditPart) firstElement).getParent() != null) {
				if (firstElement instanceof EdgeEditPart) {
					firstElement = ((EdgeEditPart) firstElement).getSource();
				} else {
					firstElement = ((AbstractEditPart) firstElement).getParent();
				}
			}

			if (firstElement instanceof RuleEditPart) {
				selectedRuleEditPart = (RuleEditPart) firstElement;
			}
		}
		view.selectedRuleChanged(selectedRuleEditPart);
	}

}
