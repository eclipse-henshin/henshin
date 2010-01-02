package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class SetElementAction implements IObjectActionDelegate {
	
	// Currently action Henshin diagram editor.
	private HenshinDiagramEditor editor;
	
	// Selected edit parts:
	private List<IGraphicalEditPart> editparts = new ArrayList<IGraphicalEditPart>();
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		MessageDialog.openInformation(editor.getSite().getShell(), "Set element action", editparts.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {
		editor = (HenshinDiagramEditor) ((part instanceof HenshinDiagramEditor) ? part : null);
		action.setEnabled(editor!=null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		editparts.clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			Iterator<?> iterator = structured.iterator();
			while (iterator.hasNext()) {
				Object next = iterator.next();
				if (next instanceof NodeEditPart || next instanceof EdgeEditPart) {
					editparts.add((IGraphicalEditPart) next);
				}
			}
		}
		//action.setEnabled(!editparts.isEmpty());
		action.setEnabled(false);
	}

}
