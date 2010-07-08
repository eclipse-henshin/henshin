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
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * Action for resetting state space files.
 * @author Christian Krause
 */
public class ResetStateSpaceFileAction extends AbstractStateSpaceFileAction {

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		boolean confirmed = MessageDialog.openConfirm(getShell(), "Reset State Space", "All derived states will be deleted. Really continue?");
		if (confirmed) {
			getStateSpaceManager().resetStateSpace();
			saveStateSpace();
		}
		
	}

}
