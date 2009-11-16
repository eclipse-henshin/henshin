package org.eclipse.emf.henshin.statespace.explorer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.henshin.statespace.StateSpace;
import org.eclipse.emf.henshin.statespace.util.StateSpacePrettyPrinter;
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
	@Override
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
		
		private static final String DEFAULT_EXTENSION = ".henshin_statespace";
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
			setTitle("Create a new " + DEFAULT_EXTENSION + " file");
			setDescription("Create a new " + DEFAULT_EXTENSION + " file");
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.ui.dialogs.WizardNewFileCreationPage#createControl(org.eclipse.swt.widgets.Composite)
		 */
		public void createControl(Composite parent) {
			super.createControl(parent);
			setFileName("default" + DEFAULT_EXTENSION);
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
			StateSpace stateSpace = new StateSpace();
			String data = StateSpacePrettyPrinter.print(stateSpace);
			return new ByteArrayInputStream(data.getBytes());
		}

		/**
		 * Return true, if the file name entered in this page is valid.
		 */
		private boolean validateFilename() {
			if (getFileName() != null && getFileName().endsWith(DEFAULT_EXTENSION)) {
				return true;
			}
			setErrorMessage("The 'file' name must end with " + DEFAULT_EXTENSION);
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
