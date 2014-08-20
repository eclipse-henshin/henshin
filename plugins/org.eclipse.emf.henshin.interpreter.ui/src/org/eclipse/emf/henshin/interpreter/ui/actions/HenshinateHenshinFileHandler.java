/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizard;
import org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinWizardDialog;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.resource.HenshinResource;
import org.eclipse.emf.henshin.model.resource.HenshinResourceSet;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * @author Gregor Bonifer, Stefan Jurack, Christian Krause
 */
public class HenshinateHenshinFileHandler extends AbstractHandler {
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		// Get the active selection:
		IStructuredSelection selection = (IStructuredSelection) 
				HandlerUtil.getActiveMenuSelection(event);
		
		// We expect exactly one element:
		if (selection.size()==1) {
			Object first = selection.getFirstElement();
			
			// Should be an IFile:
			if (first instanceof IFile) {
				IFile file = (IFile) first;
				
				// Must be a Henshin file:
				if (HenshinResource.FILE_EXTENSION.equals(file.getFileExtension())) {
				
					// Create a resource set and load the resource::
					HenshinResourceSet resourceSet = new HenshinResourceSet();

					// Load the module:
					URI uri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);
					Module module = resourceSet.getModule(uri, false);

					// Run the wizard:
					if (module!=null) {
						HenshinWizard wizard = new HenshinWizard(module);
						HenshinWizardDialog dialog = new HenshinWizardDialog(
								HandlerUtil.getActiveShell(event), wizard);
						dialog.open();
						return null;
					}
				}
			}
		}
		
		// Something was wrong:
		MessageDialog.openError(HandlerUtil.getActiveShell(event), "Error", "Please select exactly one *." + 
				HenshinResource.FILE_EXTENSION + " file.");
		return null;
		
	}
	
}
