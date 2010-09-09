/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.statespace.explorer.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.statespace.State;
import org.eclipse.emf.henshin.statespace.explorer.edit.StateEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;

/**
 * @author Christian Krause
 */
public class OpenStateModelAction extends AbstractStateSpaceAction {
	
	// States to be opened:
	private List<State> states = new ArrayList<State>();
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		for (State state : states) {
		
			// Build the absolute platform URI:
			URI base = state.eResource().getURI();
			URI unresolved = state.getModel().getURI();
			URI resolved = unresolved.resolve(base);

			IPath path = new Path(resolved.toPlatformString(true));
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
			IWorkbenchPage page = getExplorer().getSite().getPage();

			try {
				IDE.openEditor(page, file, true);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		states.clear();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structured = (IStructuredSelection) selection;
			for (Object selected : structured.toArray()) {
				if (selected instanceof StateEditPart) {
					State state = ((StateEditPart) selected).getState();
					if (state.isInitial()) states.add(state);
				}
			}
		}
		action.setEnabled(!states.isEmpty());
	}
	
}
