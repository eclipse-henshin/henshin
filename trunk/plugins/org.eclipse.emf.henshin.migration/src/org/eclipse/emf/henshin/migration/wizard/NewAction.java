package org.eclipse.emf.henshin.migration.wizard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.henshin.migration.Transformation;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class NewAction implements IObjectActionDelegate {

	private Shell shell;
	private URI fileUri = null;
	
	/**
	 * Constructor for Action1.
	 */
	public NewAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		if (fileUri != null) {
			try {
				Transformation tr = new Transformation();
				tr.migrate(fileUri);
				MessageDialog.openInformation(shell, "Conversion successful", "Conversion successful.");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				MessageDialog.openError(shell, "Error", "Error during conversion: Class not found:\n" + e.getMessage());
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				MessageDialog.openError(shell, "Error", "Error during conversion: Could not backup old file. Conversion aborted.");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				MessageDialog.openError(shell, "Error", "Error during conversion: IOException:\n" + e.getMessage());
				e.printStackTrace();	
			}
		}
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection != null) {
			IFile myFile = ((IFile) (((IStructuredSelection) selection).getFirstElement()));
			fileUri = myFile.getLocationURI();
			System.err.println(fileUri);
		}
	}

}
