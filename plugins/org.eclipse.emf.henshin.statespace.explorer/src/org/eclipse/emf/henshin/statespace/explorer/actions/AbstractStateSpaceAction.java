package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.emf.henshin.statespace.explorer.parts.StateSpaceExplorer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceAction implements IEditorActionDelegate {
	
	// Currently action state space explorer:
	private StateSpaceExplorer explorer;
	
	protected StateSpaceExplorer getExplorer() {
		return explorer;
	}
	
	public void setExplorer(StateSpaceExplorer explorer) {
		this.explorer = explorer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart editor) {
		explorer = null;
		if (editor instanceof StateSpaceExplorer) {
			explorer = (StateSpaceExplorer) editor;
		}
		action.setEnabled(explorer!=null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		action.setEnabled(true);
	}
	
}
