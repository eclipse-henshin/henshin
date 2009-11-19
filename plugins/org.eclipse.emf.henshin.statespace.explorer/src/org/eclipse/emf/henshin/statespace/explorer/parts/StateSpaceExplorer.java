package org.eclipse.emf.henshin.statespace.explorer.parts;

import java.util.EventObject;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateSpaceEditPartFactory;
import org.eclipse.emf.henshin.statespace.util.StateSpaceLoader;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.part.FileEditorInput;

/**
 * State space diagram editor.
 * @author Christian Krause
 */
public class StateSpaceExplorer extends GraphicalEditor {
	
	// Root element.
	private StateSpace stateSpace;
	
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
		
		// Add the tools menu:
		Composite tools = new Composite(sashForm, SWT.NONE);
		tools.setLayout(new GridLayout(1, false));
		
		// The layouting group:
		Group group = new Group(tools, SWT.NONE);
		group.setText("Layouting");
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Weights must be set at the end!
		sashForm.setWeights(new int[] { 4,1 });
		
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
		viewer.setContents(getStateSpace());
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
			IFile file = ((IFileEditorInput) getEditorInput()).getFile();
			StateSpaceLoader.save(getStateSpace(), file, monitor);
			getCommandStack().markSaveLocation();
		} catch (CoreException e) { 
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
			// try to save the editor's contents under a different file name
			final IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			try {
				new ProgressMonitorDialog(shell).run(false, false, 
						new WorkspaceModifyOperation() {
							public void execute(final IProgressMonitor monitor) {
								try {
									StateSpaceLoader.save(getStateSpace(), file, monitor);
								} catch (CoreException e) {
									MessageDialog.openError(getSite().getShell(), "Error", "Error saving file. See the error log for more info.");
									StateSpaceExplorerPlugin.getInstance().logError("Error saving file", e);
								}
							}
						});
				setInput(new FileEditorInput(file));
				getCommandStack().markSaveLocation();
			}
			catch (Throwable t) {
				StateSpaceExplorerPlugin.getInstance().logError("Unexpected exception", t);
			}
		}
	}
	
	/**
	 * Get the displayed state space.
	 * @return State space.
	 */
	public StateSpace getStateSpace() {
		return stateSpace;
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
		try {
			IFile file = ((IFileEditorInput) input).getFile();
			setPartName(file.getName());
			stateSpace = StateSpaceLoader.load(file);
		} catch (CoreException e) { 
			stateSpace = StateSpaceFactory.INSTANCE.createStateSpace();
			MessageDialog.openError(getSite().getShell(), "Error", "Error loading file. See the error log for more info.");
			StateSpaceExplorerPlugin.getInstance().logError("Error loading file", e.getCause());
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