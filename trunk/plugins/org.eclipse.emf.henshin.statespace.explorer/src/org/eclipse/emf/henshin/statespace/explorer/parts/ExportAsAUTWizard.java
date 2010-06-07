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
public class ExportAsAUTWizard extends Wizard implements IExportWizard {
	
	// File extension:
	public static final String FILE_EXTENSION = "aut";
	
	// Wizard page:
	protected ExportAsAUTWizardFileCreationPage fileCreationPage;

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
		setWindowTitle("Export As AUT");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		try {
			final IFile autFile = getAUTFile();

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
	public class ExportAsAUTWizardFileCreationPage extends WizardNewFileCreationPage {
		
		public ExportAsAUTWizardFileCreationPage(String pageId, IStructuredSelection selection) {
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
				if (extension == null || !FILE_EXTENSION.equals(extension)) {
					setErrorMessage("File extension must be *." + FILE_EXTENSION);
					return false;
				}
				return true;
			}
			return false;
		}

		public IFile getAUTFile() {
			return ResourcesPlugin.getWorkspace().getRoot().getFile(getContainerFullPath().append(getFileName()));
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		fileCreationPage = new ExportAsAUTWizardFileCreationPage("Whatever", selection);
		fileCreationPage.setTitle("Export As AUT");
		fileCreationPage.setDescription("Export the state space to the Aldebaran format (*." + FILE_EXTENSION + ")");
		fileCreationPage.setFileName("default." + FILE_EXTENSION);
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
					String filename = "default." + FILE_EXTENSION;
					for (int i = 1; ((IContainer)selectedResource).findMember(filename)!=null; ++i) {
						filename = "default" + i + "." + FILE_EXTENSION;
					}
					fileCreationPage.setFileName(filename);
				}
			}
		}
	}

	/**
	 * Get the file from the page.
	 */
	public IFile getAUTFile() {
		return fileCreationPage.getAUTFile();
	}

}
