package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

/**
 * @author Christian Krause
 */
public class OpenStateModelAction implements IObjectActionDelegate {
	
	private State state;
	private IWorkbenchPart part;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		String path = state.getModel().getURI().toPlatformString(false);
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(path));		
		IWorkbenchPage page = part.getSite().getPage();
		
		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {
		this.part = part;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		state = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof StateEditPart) {
				state = ((StateEditPart) first).getState();
			}
		}
		action.setEnabled(state!=null && state.isInitial());
	}
	
}
