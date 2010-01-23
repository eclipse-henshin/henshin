package org.eclipse.emf.henshin.statespace.explorer.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.commands.CreateInitialStateCommand;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * Action for creating a new initial state to the current state space.
 * @author Christian Krause
 */
public class CreateInitialStateAction extends AbstractExplorerAction {
	
	// Location of the new state:
	private int[] location;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Display a dialog:
		Shell shell = getExplorer().getSite().getShell();
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
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
		
		StateSpaceManager manager = getExplorer().getStateSpaceManager();
		StateSpace stateSpace = manager.getStateSpace();
		ResourceSet resourceSet = stateSpace.eResource().getResourceSet();
		URI absolute = URI.createPlatformResourceURI(file.getFullPath().toString(),true);
		URI relative = absolute.deresolve(stateSpace.eResource().getURI());
		
		try {
			
			// Load the model and assign the relative path to it:
			Resource model = resourceSet.getResource(absolute, true);
			model.setURI(relative);
			
			CreateInitialStateCommand command = new CreateInitialStateCommand(model, manager);
			command.setLocation(location);
			
			getExplorer().executeCommand(command);
			
		} catch (Throwable t) {
			Shell shell = getExplorer().getSite().getShell();
			MessageDialog.openError(shell, "Create Initial State", "Error creating initial state for " + file + ": " + t.getMessage());
		}
		
	}
	
	public void setLocation(int... location) {
		this.location = location;
	}
	
}
