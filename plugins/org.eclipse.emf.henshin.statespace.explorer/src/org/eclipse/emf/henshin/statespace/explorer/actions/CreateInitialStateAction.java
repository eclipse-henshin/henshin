package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.CreateInitialStateCommand;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateSpaceEditPart;
import org.eclipse.emf.henshin.statespace.explorer.parts.StateSpaceExplorer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Action for creating a new initial state to the current state space.
 * @author Christian Krause
 */
public class CreateInitialStateAction implements IObjectActionDelegate {
	
	// Currently action state space explorer:
	private StateSpaceExplorer explorer;
	
	// State space:
	private StateSpace stateSpace;
	
	// Location of the new state:
	private int[] location;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Display a dialog:
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(explorer.getSite().getShell(), new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		dialog.setTitle("Load resource");
		dialog.setMessage("Select the resource to load:");
		dialog.setInput(ResourcesPlugin.getWorkspace().getRoot());
		dialog.open();
		
		// Check whether the location was set:
		if (location==null) {
			location = new int[] { 50, 50, 0 };
		}
		
		// Load the selected files:
		if (dialog.getReturnCode()==Dialog.OK) {
			for (Object object : dialog.getResult()) {
				if (object instanceof IFile) load((IFile) object);
			}
		}
		
	}
	
	private void load(IFile file) {
		
		URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), false);
		ResourceSet resourceSet = stateSpace.eResource().getResourceSet();
		
		try {
			
			Resource model = resourceSet.getResource(uri, true);
			StateSpaceManager manager = explorer.getStateSpaceManager();
			CreateInitialStateCommand command = new CreateInitialStateCommand(model, manager);
			command.setLocation(location);
			
			explorer.executeCommand(command);
			
		} catch (Throwable t) {
			Shell shell = explorer.getSite().getShell();
			MessageDialog.openError(shell, "Create Initial State", "Error creating initial state for " + file + ": " + t.getMessage());
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {
		explorer = null;
		stateSpace = null;
		if (part instanceof StateSpaceExplorer) {
			explorer = (StateSpaceExplorer) part;
			stateSpace = explorer.getStateSpaceManager().getStateSpace();
		}
		action.setEnabled(stateSpace!=null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		stateSpace = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof StateSpaceEditPart) {
				stateSpace = ((StateSpaceEditPart) first).getStateSpace();
			}
		}
		action.setEnabled(stateSpace!=null);
	}
	
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}
	
	public void setStateSpaceExplorer(StateSpaceExplorer explorer) {
		this.explorer = explorer;
	}
	
	public void setLocation(int... location) {
		this.location = location;
	}
	
}
