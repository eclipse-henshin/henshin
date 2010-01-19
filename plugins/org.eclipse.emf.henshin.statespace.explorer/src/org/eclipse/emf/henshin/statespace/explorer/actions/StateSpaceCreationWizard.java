package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.StateSpaceFactory;
import org.eclipse.emf.henshin.statespace.resources.StateSpaceResource;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;

/**
 * State space file creation wizard.
 * @author Christian Krause
 */
public class StateSpaceCreationWizard extends Wizard implements INewWizard {
	
	// Wizard page.
	private CreationPage page;

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		addPage(page); 
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		page = new CreationPage(workbench, selection); 
	}

	/* 
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return page.finish();
	}

	/**
	 * This WizardPage can create an empty .shapes file for the ShapesEditor.
	 */
	private class CreationPage extends WizardNewFileCreationPage {
		
		private final IWorkbench workbench;
		
		/**
		 * Create a new wizard page instance.
		 * @param workbench the current workbench
		 * @param selection the current object selection
		 * @see StateSpaceCreationWizard#init(IWorkbench, IStructuredSelection)
		 */
		CreationPage(IWorkbench workbench, IStructuredSelection selection) {
			super("shapeCreationPage1", selection);
			this.workbench = workbench;
			setTitle("Create a new " + StateSpaceResource.FILE_EXTENSION + " file");
			setDescription("Create a new " + StateSpaceResource.FILE_EXTENSION + " file");
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			super.createControl(parent);
			setFileName("default." + StateSpaceResource.FILE_EXTENSION);
			setPageComplete(validatePage());
		}

		/**
		 * This method will be invoked, when the "Finish" button is pressed.
		 * @see StateSpaceCreationWizard#performFinish()
		 */
		boolean finish() {
			
			// create a new file, result != null if successful
			IFile newFile = createNewFile();

			// open newly created file in the editor
			IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
			if (newFile != null && page != null) {
				try {
					IDE.openEditor(page, newFile, true);
				} catch (PartInitException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#getInitialContents()
		 */
		protected InputStream getInitialContents() {
			
			// Get the file path:
			final IPath containerPath = getContainerFullPath();
			IPath filePath = containerPath.append(getFileName());
			
			try {
			
				// Create a state space resource:
				URI uri = URI.createPlatformResourceURI(filePath.toString(), false);
				ResourceSet resourceSet = new ResourceSetImpl();
				Resource resource = resourceSet.createResource(uri);
				StateSpace stateSpace = StateSpaceFactory.eINSTANCE.createStateSpace();
				resource.getContents().add(stateSpace);
				resource.save(null);

				// Refresh and load the created file:
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(filePath);
				file.refreshLocal(1, new NullProgressMonitor());
				return file.getContents();
			
			} catch (Throwable t) {
				MessageDialog.openError(getShell(), "Create new state space file", "Error creating file: " + t.getMessage());
				return null;
			}
			
		}

		/**
		 * Return true, if the file name entered in this page is valid.
		 */
		private boolean validateFilename() {
			if (getFileName() != null && getFileName().endsWith("." + StateSpaceResource.FILE_EXTENSION)) {
				return true;
			}
			setErrorMessage("The file name must end with " + StateSpaceResource.FILE_EXTENSION);
			return false;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
		 */
		protected boolean validatePage() {
			return super.validatePage() && validateFilename();
		}
	}
}
