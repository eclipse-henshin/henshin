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

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Action for exporting state space as AUT files.
 * @author Christian Krause
 */
public class ExportStateSpaceTikZAction extends AbstractStateSpaceAction {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		// Create and execute the wizard:
		StateSpaceTikZExportWizard wizard = new StateSpaceTikZExportWizard();
		wizard.init(getExplorer().getEditorSite().getWorkbenchWindow().getWorkbench(), (IStructuredSelection) getSelection());
		wizard.setStateSpace(getExplorer().getStateSpaceManager().getStateSpace());
		
		Shell shell = getExplorer().getSite().getShell();
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.setTitle("Export State Space");
		dialog.open();
		
	}

}
