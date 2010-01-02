package org.eclipse.emf.henshin.diagram.part;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramCommandStack;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class ImportPackageAction implements IObjectActionDelegate {
	
	public static final String IMPORT_FROM_WORKSPACE_ACTION_ID = "org.eclipse.emf.henshin.diagram.ImportFromWorkspace";
	public static final String IMPORT_FROM_REGISTRY_ACTION_ID = "org.eclipse.emf.henshin.diagram.ImportFromRegistry";
	
	// Editor instance:
	private HenshinDiagramEditor editor;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		final EPackage newPackage;
		Shell shell = editor.getSite().getShell();
		
		if (IMPORT_FROM_WORKSPACE_ACTION_ID.equals(action.getActionDefinitionId())) {
			newPackage = EcoreSelectionDialogUtil.selectEcoreFilePackage(shell);
		} else {
			newPackage = EcoreSelectionDialogUtil.selectRegisteredPackage(shell);			
		}
		
		// Check if the package is set:
		if (newPackage!=null && newPackage.getNsURI()!=null) {
			
			// Create a new transactional command:
			TransactionalEditingDomain domain = editor.getEditingDomain();
			AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Import", null) {
				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					doImport(newPackage);
					return CommandResult.newOKCommandResult(newPackage);
				}
			};
			
			// Execute it:
			DiagramCommandStack stack = editor.getDiagramEditDomain().getDiagramCommandStack();
			stack.execute(new ICommandProxy(command));
		}
		
	}
	
	
	/*
	 * Perform the import.
	 */
	private void doImport(EPackage epackage) {
		
		// Get the transformation system:
		TransformationSystem system = (TransformationSystem) editor.getDiagram().getElement();
		
		// Check if a package with the same nsURI exists already:
		String nsURI = epackage.getNsURI();
		for (int i=0; i<system.getImports().size(); i++) {
			EPackage current = system.getImports().get(i);
			
			// Replace the current package if it has the same nsURI:
			if (nsURI.equals(current.getNsURI())) {
				if (epackage!=current) {
					system.getImports().set(i,epackage);
					return;
				}
			}
		}
		
		// Otherwise add it:
		system.getImports().add(epackage);
		
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
	}

}
