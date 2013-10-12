/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.migration.wizard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.henshin.migration.Transformation;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.IProgressService;

/**
 * @author Felix Rieger, Christian Krause
 */
public class MigrateHenshinAction implements IObjectActionDelegate {

	private Shell shell;
	private IFile file = null;
	
	/**
	 * Constructor for Action1.
	 */
	public MigrateHenshinAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		if (targetPart != null) {
			shell = targetPart.getSite().getShell();
		}
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {

		if (file != null) {
		   IWorkbench wb = PlatformUI.getWorkbench();
		   IProgressService ps = wb.getProgressService();

			try {
			ps.busyCursorWhile(new IRunnableWithProgress() {
			      public void run(IProgressMonitor pm) {
			    	  //pm.beginTask("Migration of " + fileUri, 1024);
						try {
							Transformation tr = new Transformation();
							pm.worked(100);
							tr.migrate(file.getLocationURI(), false, true, pm);
							pm.done();
							
							// refresh:
							try {
								file.getParent().refreshLocal(2, new NullProgressMonitor());
							} catch (CoreException e) {}
							
							//MessageDialog.openInformation(shell, "Conversion successful", "Conversion successful.");
							return;
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
						pm.done();
						MessageDialog.openError(shell, "Error", "Error during conversion.");
			      }
			   });
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		}
		
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		file = null;
		if (selection != null) {
			IFile myFile = ((IFile) (((IStructuredSelection) selection).getFirstElement()));
			if (myFile != null) {
				file = myFile;
			}
		}
		action.setEnabled(file!=null);
	}

}
