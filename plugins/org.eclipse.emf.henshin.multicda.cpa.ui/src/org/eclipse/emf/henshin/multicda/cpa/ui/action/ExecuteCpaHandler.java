/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.ui.action;

import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.henshin.multicda.cpa.ui.wizard.CpaWizard;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class ExecuteCpaHandler extends AbstractHandler {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if(sel instanceof IStructuredSelection){
		    IStructuredSelection selection = (IStructuredSelection) sel;
		    List<IStructuredSelection> selectedFiles = selection.toList();
			    
		    Shell shell = HandlerUtil.getActiveShell(event);
			WizardDialog wizardDialog = new WizardDialog(shell.getShell(), new CpaWizard(selectedFiles));
			wizardDialog.open();
		}
		return null;
	}
}