/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.editor.actions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.presentation.EcoreActionBarContributor.ExtendedLoadResourceAction.RegisteredPackageDialog;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Christian Krause
 */
public class ImportPackageAction implements IObjectActionDelegate {
	
	// Action IDs:
	public static final String FROM_WORKSPACE_ACTION_ID = "importFromWorkspace";
	public static final String FROM_REGISTRY_ACTION_ID = "importFromRegistry";
	
	// Workbench part:
	protected IWorkbenchPart workbenchPart;
	
	// Module:
	protected Module module;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		
		final EPackage newPackage;
		Shell shell = workbenchPart.getSite().getShell();
		ResourceSet resourceSet = module.eResource().getResourceSet();
		
		if (FROM_WORKSPACE_ACTION_ID.equals(action.getId())) {
			newPackage = EcoreSelectionDialogUtil.selectEcoreFilePackage(shell, resourceSet);
		} else {
			newPackage = selectRegisteredPackage(shell, resourceSet);
		}
		
		// Check if the package is set:
		if (newPackage != null && newPackage.getNsURI() != null) {
			runImportCommand(newPackage);
		}
		
	}
	
	/*
	 * Run the import command.
	 */
	protected void runImportCommand(final EPackage epackage) {
		
		// Create the new command:
		Command command = new AbstractCommand("Import Package") {
			
			/*
			 * (non-Javadoc)
			 * @see org.eclipse.emf.common.command.AbstractCommand#canUndo()
			 */
			@Override
			public boolean canUndo() {
				return false;
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.eclipse.emf.common.command.AbstractCommand#prepare()
			 */
			@Override
			public boolean prepare() {
				return true;
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.eclipse.emf.common.command.Command#execute()
			 */
			public void execute() {
				redo();
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.eclipse.emf.common.command.Command#redo()
			 */
			public void redo() {
				doImport(epackage);
			}
		};
		
		// Execute it:
		HenshinEditor editor = (HenshinEditor) workbenchPart;
		CommandStack stack = editor.getEditingDomain().getCommandStack();
		stack.execute(command);
		
	}
	
	/*
	 * Perform the import.
	 */
	protected void doImport(EPackage epackage) {
		
		// Check if a package with the same nsURI exists already:
		String nsURI = epackage.getNsURI();
		for (int i = 0; i < module.getImports().size(); i++) {
			EPackage current = module.getImports().get(i);
			
			// Replace the current package if it has the same nsURI:
			if (nsURI.equals(current.getNsURI())) {
				if (epackage != current) {
					module.getImports().set(i, epackage);
					return;
				}
			}
		}
		
		// Otherwise add it:
		module.getImports().add(epackage);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.
	 * action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart part) {
		workbenchPart = (part instanceof HenshinEditor) ? part : null;
		action.setEnabled(workbenchPart != null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action
	 * .IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		module = null;
		if (selection instanceof IStructuredSelection) {
			Object first = ((IStructuredSelection) selection).getFirstElement();
			if (first instanceof Module) {
				module = (Module) first;
			}
		}
		action.setEnabled(module != null);
	}
	
	
	/**
	 * Open a dialog that lets the user choose a registered package.
	 * 
	 * @param shell
	 *            Shell to be used.
	 * @param resourceSet
	 *            Resource set.
	 * @return The selected package.
	 */
	public static EPackage selectRegisteredPackage(Shell shell, ResourceSet resourceSet) {
		
		CustomRegisteredPackageDialog dialog = new CustomRegisteredPackageDialog(shell);
		dialog.setMultipleSelection(false);
		dialog.open();
		EPackage epackage = null;
		
		Object[] result = dialog.getResult();
		if (result != null) {
			List<?> nsURIs = Arrays.asList(result);
			if (dialog.isDevelopmentTimeVersion()) {
				resourceSet.getURIConverter().getURIMap()
						.putAll(EcorePlugin.computePlatformURIMap());
				Map<String, URI> locationMap = EcorePlugin.getEPackageNsURIToGenModelLocationMap();
				if (result.length > 0) {
					URI location = locationMap.get(result[0]);
					Resource resource = resourceSet.getResource(location, true);
					EcoreUtil.resolveAll(resource);
				}
				for (Resource resource : resourceSet.getResources()) {
					for (EPackage current : EcoreSelectionDialogUtil.getAllPackages(resource)) {
						if (nsURIs.contains(current.getNsURI())) {
							epackage = current;
							break;
						}
					}
				}
			} else {
				if (result.length > 0) {
					String uri = result[0].toString();
					return EPackage.Registry.INSTANCE.getEPackage(uri);
				}
			}
		}
		
		return epackage;
		
	}

	protected static class CustomRegisteredPackageDialog extends RegisteredPackageDialog {

		public CustomRegisteredPackageDialog(Shell shell) {
			super(shell);
			setMessage("Select the package to import. In most cases you need the runtime version.");
			super.isDevelopmentTimeVersion = false; // we want the runtime version
		}
	
		@Override
		protected Control createDialogArea(Composite parent) {
			Control result = super.createDialogArea(parent);
			updateButtons(result);
			return result;
		}
		
		private void updateButtons(Control control) {
			if (control instanceof Button) {
				Button button = (Button) control;
				// button.setEnabled(false);
				String text = button.getText().toLowerCase();
				if (text.indexOf("runtime")>=0) {
					button.setSelection(true);
				} else {
					button.setSelection(false);
				}
			}
			if (control instanceof Composite) {
				for (Control child : ((Composite) control).getChildren()) {
					updateButtons(child);
				}
			}
		}

	}
	
}
