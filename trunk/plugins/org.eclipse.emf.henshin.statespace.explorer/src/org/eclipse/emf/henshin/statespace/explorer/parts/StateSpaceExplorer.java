/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceManager;
import org.eclipse.emf.henshin.statespace.Trace;
import org.eclipse.emf.henshin.statespace.Transition;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateSpaceEditPartFactory;
import org.eclipse.emf.henshin.statespace.explorer.jobs.StateSpaceJobManager;
import org.eclipse.emf.henshin.statespace.impl.StateSpaceManagerImpl;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.StructuredSelection;
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
 * State space explorer.
 * @author Christian Krause
 */
public class StateSpaceExplorer extends GraphicalEditor {
	
	// State space manager:
	private StateSpaceManager stateSpaceManager;
	
	// Job manager:
	private StateSpaceJobManager jobManager;
	
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
		ScrollingGraphicalViewer viewer = (ScrollingGraphicalViewer) getGraphicalViewer();
		ScalableFreeformRootEditPart root = (ScalableFreeformRootEditPart) viewer.getRootEditPart();
		FigureCanvas canvas = (FigureCanvas) viewer.getControl();
		
		// Add the tools menu:
		toolsMenu = new StateSpaceToolsMenu(sashForm, getEditDomain());
		toolsMenu.setZoomManager(root.getZoomManager());
		toolsMenu.setCanvas(canvas);
		if (jobManager!=null) {
			toolsMenu.setJobManager(jobManager);
		}
		
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
		ContextMenuProvider provider = new StateSpaceContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(provider);
		getSite().registerContextMenu(provider, viewer);
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditorWithFlyoutPalette#initializeGraphicalViewer()
	 */
	@Override
	protected void initializeGraphicalViewer() {
		setContent();
	}
	
	/*
	 *  Set the viewer content.
	 */
	private void setContent() {
		GraphicalViewer viewer = getGraphicalViewer();
		((StateSpaceEditPartFactory) viewer.getEditPartFactory()).setStateSpaceManager(stateSpaceManager);
		viewer.setContents(stateSpaceManager.getStateSpace());		
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
		
		// Stop all background jobs first:
		jobManager.stopAllJobs();
		
		try {
			Resource resource = stateSpaceManager.getStateSpace().eResource();
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
		
		// Stop all background jobs first:
		jobManager.stopAllJobs();
		
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
				StateSpaceResource resource = (StateSpaceResource) stateSpaceManager.getStateSpace().eResource();
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
		return stateSpaceManager;
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
			
			// Ask for confirmation if the state space is big:
			int numStates = stateSpace.getStates().size();
			if (numStates>5000) {
				boolean confirmed = MessageDialog.openConfirm(getSite().getShell(), "Open State Space", 
						"This state space contains " + numStates + " states. Displaying it in the graphical " + 
						"explorer might take a while and be very slow. Really display it?");
				if (!confirmed) throw new RuntimeException("State space too large to be displayed");
			}
			
			// Create a new state space manager:
			stateSpaceManager = new StateSpaceManagerImpl(stateSpace);
			jobManager = new StateSpaceJobManager(stateSpaceManager, getEditDomain());
			
			// Initialize tools menu:
			if (toolsMenu!=null) {
				toolsMenu.setJobManager(jobManager);
			}
			
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
			
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ui.parts.GraphicalEditor#dispose()
	 */
	@Override
	public void dispose() {
		jobManager.stopAllJobs(); // stop all jobs first.
		super.dispose(); // and dispose.
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

	/**
	 * Select a trace in this explorer instance.
	 * @param trace Trace to be shown.
	 */
	public void selectTrace(Trace trace) {
		Map<?,?> registry = getGraphicalViewer().getEditPartRegistry();
		List<EditPart> editparts = new ArrayList<EditPart>();
		if (registry.containsKey(trace.getSource())) {
			editparts.add((EditPart) registry.get(trace.getSource()));
			for (Transition transition : trace) {
				editparts.add((EditPart) registry.get(transition));
				editparts.add((EditPart) registry.get(transition.getTarget()));
			}
		}
		getGraphicalViewer().setSelection(new StructuredSelection(editparts ));
	}
	
}