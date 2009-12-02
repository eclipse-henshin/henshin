package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateSpaceEditPartFactory;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl;
import org.eclipse.emf.henshin.statespace.resources.StateSpaceResource;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;

/**
 * State space diagram editor.
 * @author Christian Krause
 */
public class StateSpaceExplorer extends GraphicalEditor {
	
	// State space manager:
	private StateSpaceManager manager;
	
	// Job for loading the state space:
	private Job loader;
	
	// Tool menu:
	private StateSpaceToolsMenu toolsMenu;
	
	/** 
	 * Create a new editor instance. 
	 * This is called by the workspace. 
	 */
	public StateSpaceExplorer() {
		setEditDomain(new DefaultEditDomain(this));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		
		// Create a sash form:
		parent.setLayout(new FillLayout());
		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		sashForm.SASH_WIDTH = 5;
		sashForm.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		
		// Create the graphical viewer:
		createGraphicalViewer(sashForm);
		RootEditPart root = getGraphicalViewer().getRootEditPart();
		ZoomManager zoomManager = ((ScalableFreeformRootEditPart) root).getZoomManager();
		
		// Add the tools menu:
		toolsMenu = new StateSpaceToolsMenu(sashForm, getEditDomain());
		toolsMenu.setStateSpaceManager(manager);
		toolsMenu.setZoomManager(zoomManager);
		
		// Weights must be set at the end!
		sashForm.setWeights(new int[] { 5,2 });
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#configureGraphicalViewer()
	 */
	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		
		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setEditPartFactory(new StateSpaceEditPartFactory());
		viewer.setRootEditPart(new ScalableFreeformRootEditPart());
		viewer.setKeyHandler(new GraphicalViewerKeyHandler(viewer));
		
		// Configure the context menu provider:
		ContextMenuProvider provider = new StateSpaceEditorContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(provider);
		getSite().registerContextMenu(provider, viewer);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		// Set the viewer content:
		GraphicalViewer viewer = getGraphicalViewer();
		((StateSpaceEditPartFactory) viewer.getEditPartFactory()).setStateSpaceManager(manager);
		viewer.setContents(manager.getStateSpace());
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#commandStackChanged(java.util.EventObject)
	 */
	@Override
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {		
		try {
			Resource resource = manager.getStateSpace().eResource();
			resource.save(null);
			getCommandStack().markSaveLocation();
		} catch (Exception e) { 
			MessageDialog.openError(getSite().getShell(), "Error", "Error saving file. See the error log for more info.");
			StateSpaceExplorerPlugin.getInstance().logError("Error saving file", e);
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		
		// Show a SaveAs dialog
		Shell shell = getSite().getWorkbenchWindow().getShell();
		SaveAsDialog dialog = new SaveAsDialog(shell);
		dialog.setOriginalFile(((IFileEditorInput) getEditorInput()).getFile());
		dialog.open();
		
		IPath path = dialog.getResult();	
		if (path != null) {
			
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			URI uri = URI.createPlatformResourceURI(path.toString(), false);
			
			try {
				// Save the file:
				StateSpaceResource resource = (StateSpaceResource) manager.getStateSpace().eResource();
				resource.setURI(uri);
				resource.save(null);
				
				// Set the new file as editor input:
				setInput(new FileEditorInput(file));
				getCommandStack().markSaveLocation();				
			
			} catch (Exception e) {
				MessageDialog.openError(getSite().getShell(), "Error", "Error saving file. See the error log for more info.");
				StateSpaceExplorerPlugin.getInstance().logError("Error saving file", e);
			}
			
		}
	}
	
	/**
	 * Get the used state space manager.
	 * @return State space manager.
	 */
	public StateSpaceManager getStateSpaceManager() {
		return manager;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.ISaveablePart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#setInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void setInput(IEditorInput input) {
		
		super.setInput(input);
		
		// Set the editor name:
		final IFile file = ((IFileEditorInput) input).getFile();
		setPartName(file.getName());
		
		// Prepare the loading:
		ResourceSet resourceSet = new ResourceSetImpl();
    	URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), false);
    	StateSpace stateSpace;

		try {			
			// Perform the loading:
			StateSpaceResource resource = (StateSpaceResource) resourceSet.getResource(uri, true);
			stateSpace = resource.getStateSpace();
			
		} catch (Throwable e) { 
			
			// Create a fresh state space:
			Resource resource = resourceSet.createResource(uri);
			stateSpace = StateSpaceFactory.eINSTANCE.createStateSpace();
			resource.getContents().add(stateSpace);
						
			// Run a dummy reset command that marks the editor as dirty:
			getCommandStack().execute(new Command("reset state space") { 
				public boolean canUndo() {
					return false;
				}
			} );
			
			// Give an error message:
			String message = "Error loading file: " + file.getName();
			StateSpaceExplorerPlugin.getInstance().logError(message, e);
			IStatus status = new Status(IStatus.ERROR, StateSpaceExplorerPlugin.ID, 0, e.toString(), e);
			ErrorDialog.openError(getSite().getShell(), "Error", message + ". See the error log for more details.", status);
			
		}

		// Create the state space manager:
		final StateSpaceManagerImpl managerImpl = new StateSpaceManagerImpl(stateSpace);
		manager = managerImpl;
		
		// Refresh the tools menu:
		if (toolsMenu!=null && !toolsMenu.isDisposed()) {
			toolsMenu.setStateSpaceManager(manager);
		}
		
		// Load the state space manager:
		loader = new Job("Loading state space") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				//managerImpl.reload(monitor);
				loader = null;
				return new Status(IStatus.OK, StateSpaceExplorerPlugin.ID, 0, null, null);
			}
		};
		loader.setPriority(Job.LONG);
		loader.schedule();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
		if (loader!=null) {
			loader.cancel();
		}
	}
	
	/**
	 * Execute a GEF command in this editor.
	 * @param command Command to be executed.
	 */
	public void executeCommand(Command command) {
		getCommandStack().execute(command);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#getGraphicalViewer()
	 */
	@Override
	public GraphicalViewer getGraphicalViewer() {
		return super.getGraphicalViewer();
	}
	
}