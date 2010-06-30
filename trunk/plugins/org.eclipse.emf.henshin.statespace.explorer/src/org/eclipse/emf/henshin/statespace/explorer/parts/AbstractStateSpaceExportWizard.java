package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.henshin.statespace.explorer.StateSpaceExplorerPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.ISetSelectionTarget;

/**
 * @author Christian Krause
 */
public abstract class AbstractStateSpaceExportWizard extends Wizard implements IExportWizard {
	
	// File creation page:
	protected FileCreationPage fileCreationPage;

	// Selection:
	protected IStructuredSelection selection;

	// The workbench:
	protected IWorkbench workbench;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		setWindowTitle("Export State Space");
	}
	
	/**
	 * Get the file extensions supported by this export wizard.
	 * @return File extensions.
	 */
	protected abstract String[] getFileExtensions();
	
	/**
	 * Get the description for this export wizard.
	 * @return Description.
	 */
	protected abstract String getDescription();
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			final IFile autFile = getFile();

			// Do the work within an operation.
			WorkspaceModifyOperation operation =
				new WorkspaceModifyOperation() {
					@Override
					protected void execute(IProgressMonitor progressMonitor) {
						try {
							/*
							ResourceSet resourceSet = new ResourceSetImpl();
							URI fileURI = URI.createPlatformResourceURI(autFile.getFullPath().toString(), true);
							Resource resource = resourceSet.createResource(fileURI);
							
							EObject rootObject = createInitialModel();
							if (rootObject != null) {
								resource.getContents().add(rootObject);
							}

							resource.save(options);
							*/
						}
						catch (Exception e) {
							StateSpaceExplorerPlugin.getInstance().logError("Error exporting state space", e);
						} finally {
							progressMonitor.done();
						}
					}
				};
			getContainer().run(false, false, operation);

			// Select the new file resource in the current view.
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage page = workbenchWindow.getActivePage();
			final IWorkbenchPart activePart = page.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				final ISelection targetSelection = new StructuredSelection(autFile);
				getShell().getDisplay().asyncExec
					(new Runnable() {
						 public void run() {
							 ((ISetSelectionTarget)activePart).selectReveal(targetSelection);
						 }
					 });
			}
			return true;
		}
		catch (Exception e) {
			StateSpaceExplorerPlugin.getInstance().logError("Error exporting state space", e);
			return false;
		}
	}

	/**
	 * This is the one page of the wizard.
	 */
	public class FileCreationPage extends WizardNewFileCreationPage {
		
		public FileCreationPage(String pageId, IStructuredSelection selection) {
			super(pageId, selection);
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#validatePage()
		 */
		@Override
		protected boolean validatePage() {
			if (super.validatePage()) {
				String extension = new Path(getFileName()).getFileExtension();
				for (String ext : getFileExtensions()) {
					if (ext.equals(extension)) return true;
				}
				setErrorMessage("Invalid file extension.");
			}
			return false;
		}

		public IFile getFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		
		fileCreationPage = new FileCreationPage("File creation", selection);
		fileCreationPage.setTitle("Export State Space");
		fileCreationPage.setDescription(getDescription());
		fileCreationPage.setFileName("default." + getFileExtensions()[0]);
		addPage(fileCreationPage);

		if (selection!=null && !selection.isEmpty()) {
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				IResource selectedResource = (IResource)selectedElement;
				if (selectedResource.getType() == IResource.FILE) {
					selectedResource = selectedResource.getParent();
				}

				// This gives us a directory...
				if (selectedResource instanceof IFolder || selectedResource instanceof IProject) {
					fileCreationPage.setContainerFullPath(selectedResource.getFullPath());
					String filename = "default." + getFileExtensions()[0];
					for (int i = 1; ((IContainer)selectedResource).findMember(filename)!=null; ++i) {
						filename = "default" + i + "." + getFileExtensions()[0];
					}
					fileCreationPage.setFileName(filename);
				}
			}
		}
	}

	/**
	 * Get the file from the page.
	 */
	public IFile getFile() {
		return fileCreationPage.getFile();
	}

}
