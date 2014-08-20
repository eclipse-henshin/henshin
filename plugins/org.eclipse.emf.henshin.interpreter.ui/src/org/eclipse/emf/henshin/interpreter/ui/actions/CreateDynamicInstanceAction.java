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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author Christian Krause
 */
public class CreateDynamicInstanceAction implements IObjectActionDelegate {
	
	// Ecore file:
	private IFile file;
	
	// Shell:
	private Shell shell;
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		
		URI modelUri = URI.createPlatformResourceURI(file.getFullPath().toOSString(), true);
		URI instanceUri = modelUri.trimFileExtension().appendFileExtension("xmi");
		
		// Load the model:
		ResourceSet set = new ResourceSetImpl();
		Resource modelResource = set.getResource(modelUri, true);
		EPackage epackage = (EPackage) modelResource.getContents().get(0);
		
		// Choose class and create an instance:
		EObject instance = null;
		for (EClassifier eclassifier : epackage.getEClassifiers()) {
			if (eclassifier instanceof EClass) {
				EClass eclass = (EClass) eclassifier;
				if (!eclass.isAbstract() && !eclass.isInterface()) {
					instance = EcoreUtil.create(eclass);
				}
			}
		}
		
		// No valid EClass found?
		if (instance==null) {
			MessageDialog.openError(shell, "Error", "No instantiable EClass found.");
			return;
		}
		
		// Save and refresh folder:
		try {
			Resource instanceResource = set.createResource(instanceUri);
			instanceResource.getContents().add(instance);
			Map<Object,Object> options = new HashMap<Object,Object>();
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
			instanceResource.save(options);
			
			// Refresh:
			file.getParent().refreshLocal(2, new NullProgressMonitor());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	}
		
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		file = null;
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			file = (IFile) sel.getFirstElement();
		}
		action.setEnabled(file != null);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}
	
}
