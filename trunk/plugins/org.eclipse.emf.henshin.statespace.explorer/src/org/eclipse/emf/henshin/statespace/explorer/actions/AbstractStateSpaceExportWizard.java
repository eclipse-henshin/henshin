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
package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.util.Iterator;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateSpaceEditPart;
import org.eclipse.emf.henshin.statespace.resource.StateSpaceResource;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

/**
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceExportWizard extends Wizard implements IExportWizard {
	
	// File creation page:
	private FileCreationPage fileCreationPage;
	
	// The workbench:
	private IWorkbench workbench;
	
	// State space to be exported:
	private StateSpace stateSpace;
	
	// Selection:
	private IStructuredSelection selection;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
		setWindowTitle("Export State Space");
		this.workbench = workbench;
		this.selection = selection;
		
		// Try to extract the state space out of the selection:
		Iterator<?> iterator = selection.iterator();
		while (iterator.hasNext() && stateSpace==null) {
			Object current = iterator.next();
			if (current instanceof StateSpaceEditPart) {
				stateSpace = ((StateSpaceEditPart) current).getStateSpace();
			} else if (current instanceof IFile) {
				ResourceSet resourceSet = new ResourceSetImpl();
				URI fileURI = URI.createPlatformResourceURI(((IFile) current).getFullPath().toString(), true);
				try {
					stateSpace = ((StateSpaceResource) resourceSet.createResource(fileURI)).getStateSpace();
				} catch (Throwable t) {
					StateSpaceExplorerPlugin.getInstance().logError("Error loading state space from file " + fileURI.toFileString(), t);
				}
			}
		}
		
	}
	
	/**
	 * Set the state space to be used.
	 * @param stateSpace State space.
	 */
	public void setStateSpace(StateSpace stateSpace) {
		this.stateSpace = stateSpace;
	}
	
	/**
	 * Perform the export operation.
	 * @param stateSpace State space.
	 * @param file File to be exported to.
	 * @param monitor Monitor.
	 * @throws Exception On errors.
	 */
	protected abstract void doExport(StateSpace stateSpace, IFile file, IProgressMonitor monitor) throws Exception;

	/**
	 * Get the file extension supported by this export wizard.
	 * @return File extension.
	 */
	protected abstract String getFileExtension();
	
	/**
	 * Get the description for this export wizard.
	 * @return Description.
	 */
	protected abstract String getDescription();
	
	/**
	 * Get the default file name to be used.
	 * @return Default file name.
	 */
	protected String getDefaultFileName() {
		return stateSpace.eResource().getURI().trimFileExtension().lastSegment();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			IFile file = getFile();
			performExport(file);
			selectExport(file);
			return true;
		} catch (Throwable t) {
			StateSpaceExplorerPlugin.getInstance().logError("Error exporting state space", t);
			return false;
		}
	}
	
	/*
	 * Perform the export operation.
	 */
	protected void performExport(final IFile file) throws Exception {
		WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {
				@Override
				protected void execute(IProgressMonitor monitor) {
					try {
						doExport(stateSpace, file, monitor);
					} catch (Throwable t) {
						StateSpaceExplorerPlugin.getInstance().logError("Error exporting state space", t);
					} finally {
						monitor.done();
					}
				}
			};
		getContainer().run(false, false, operation);
	}
	
	/* 
	 * Select the new file resource in the current window.
	 */
	protected void selectExport(IFile file) {		
		try {
			IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = window.getActivePage();
			IDE.openEditor(page, file, true);
		}
		catch (Throwable t) {
			StateSpaceExplorerPlugin.getInstance().logError("Error opening exported file.", t);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		
		fileCreationPage = new FileCreationPage("file-creation", selection, getFileExtension());
		fileCreationPage.setTitle("Export State Space");
		fileCreationPage.setDescription(getDescription());
		fileCreationPage.setFileName(getDefaultFileName() + "." + getFileExtension());
		addPage(fileCreationPage);
		
		// Find out which directory to use:
		String directory = stateSpace.eResource().getURI().trimSegments(1).toPlatformString(true);
		IContainer container = (IContainer) ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(directory));
		fileCreationPage.setContainerFullPath(container.getFullPath());
	
		// Check if the file exists already:
		String filename = getDefaultFileName() + "." + getFileExtension();
		for (int i=1; container.findMember(filename)!=null; ++i) {
			filename = getDefaultFileName() + i + "." + getFileExtension();
		}
		fileCreationPage.setFileName(filename);
		
	}
	
	/**
	 * Get the file from the page.
	 */
	public IFile getFile() {
		return fileCreationPage.getFile();
	}

	/*
	 * This is the one page of the wizard.
	 */
	protected class FileCreationPage extends WizardNewFileCreationPage {
		
		private String fileExt;
		
		public FileCreationPage(String pageId, IStructuredSelection selection, String fileExt) {
			super(pageId, selection);
			this.fileExt = fileExt;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
		 */
		@Override
		protected boolean validatePage() {
			if (!super.validatePage()) return false;
			String extension = new Path(getFileName()).getFileExtension();
			if (!fileExt.equals(extension)) {
				setErrorMessage("Invalid file extension, should be *." + getFileExtension());
				return false;
			}
			return true;
		}

		public IFile getFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}
	

}
