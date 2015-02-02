package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class GenerateGiraphCodeAction implements IObjectActionDelegate {

	private Unit mainUnit;

	private IFile file;

	private Shell shell;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		GenerateGiraphCodeWizard wizard = new GenerateGiraphCodeWizard(mainUnit);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 * org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		mainUnit = null;
		if (selection instanceof IStructuredSelection) {
			Iterator<?> it = ((IStructuredSelection) selection).iterator();
			while (it.hasNext()) {
				Object next = it.next();
				if (next instanceof IGraphicalEditPart) {
					mainUnit = (Unit) ((IGraphicalEditPart) next).getNotationView().getElement();
					break;
				}
			}
		}
		action.setEnabled(mainUnit != null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 * org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart part) {
		file = null;
		shell = part.getSite().getShell();
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			IEditorInput input = editor.getEditorInput();
			if (input instanceof IFileEditorInput) {
				file = ((IFileEditorInput) input).getFile();
			}
		}
		action.setEnabled(file != null);
	}

}
