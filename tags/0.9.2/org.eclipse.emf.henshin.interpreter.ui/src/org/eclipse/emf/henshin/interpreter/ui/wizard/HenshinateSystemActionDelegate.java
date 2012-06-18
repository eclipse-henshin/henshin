/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinateSystemActionDelegate implements IObjectActionDelegate {
	
	protected IWorkbenchPart targetPart;
	
	protected TransformationSystem tSystem;
	
	@Override
	public void run(IAction action) {
		HenshinWizard tWiz = new HenshinWizard(tSystem);
		HenshinWizardDialog dialog = new HenshinWizardDialog(targetPart.getSite().getShell(), tWiz);
		dialog.open();
	}
	
	@Override
	public void selectionChanged(IAction action, ISelection sel) {
		if (sel instanceof IStructuredSelection) {
			tSystem = (TransformationSystem) ((IStructuredSelection) sel).getFirstElement();
		} else {
			System.out.println("not an IStructuredSelection");
		}
	}
	
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}
}
