package org.eclipse.emf.henshin.variability.ui;

import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.variability.mergein.clone.AbstractCloneGroupDetector;
import org.eclipse.emf.henshin.variability.mergein.clone.GraphMiningBasedCloneGroupDetector;
import org.eclipse.emf.henshin.variability.ui.views.CloneGroupView;
import org.eclipse.gmf.runtime.notation.impl.DiagramImpl;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class StartCloneDetectionAction implements IActionDelegate {

	public final static String ID = "org.eclipse.emf.henshin.variability.ui.StartCloneDetectionActionID";
	private ModuleEditPart selectedElement;
	
	public void run(IAction action) {
		if (selectedElement.getModel() instanceof DiagramImpl) {
			CloneGroupView cloneGroupView = openAndGetCloneGroupView();
			cloneGroupView.showBusy(true);
			
			Module module = (Module) ((DiagramImpl) selectedElement.getModel())
					.getElement();
			AbstractCloneGroupDetector cd =
//					new ConqatBasedCloneGroupDetector
					new GraphMiningBasedCloneGroupDetector //DummyCloneGroupDetector//
					(module.getAllRules(), true);
			cd.detectCloneGroups();
			cloneGroupView.setEditingDomain(selectedElement.getEditingDomain());
			cloneGroupView.setContents(cd.getResultOrderedByNumberOfCommonElements());
			cloneGroupView.setContextDiagram(selectedElement);
			cloneGroupView.showBusy(false);
		}
}

	private CloneGroupView openAndGetCloneGroupView() {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().showView(CloneGroupView.ID);
			return 
					(CloneGroupView) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
			.getActivePage().findView(CloneGroupView.ID);
		} catch (PartInitException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		selectedElement = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.getFirstElement() instanceof ModuleEditPart) {
				selectedElement = (ModuleEditPart) structuredSelection
						.getFirstElement();
			}
		}
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
