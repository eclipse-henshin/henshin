package org.eclipse.emf.henshin.interpreter.ui.giraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.henshin.model.Rule;
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

	private final List<Rule> rules = new ArrayList<Rule>();
	
	private IFile file;

	private Shell shell;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		IContainer container = file.getParent();
		for (Rule rule : rules) {
			GenerateGiraphCodeWizard wizard = new GenerateGiraphCodeWizard(rule, container);
			WizardDialog dialog = new WizardDialog(shell, wizard);
			dialog.open();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		rules.clear();
		if (selection instanceof IStructuredSelection) {
			Iterator<?> it = ((IStructuredSelection) selection).iterator();
			while (it.hasNext()) {
				Object next = it.next();
				if (next instanceof IGraphicalEditPart) {
					rules.add((Rule) ((IGraphicalEditPart) next).getNotationView().getElement());
				}
			}
		}
		action.setEnabled(!rules.isEmpty());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
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
		action.setEnabled(file!=null);
	}

}
