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
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.multicda.cpa.ui.util.CpEditorUtil;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

public class OpenCpHandler extends AbstractHandler {

	private IEditorPart editor;

	private URI firstRuleUri;

	private URI overlapUri;

	private URI secondRuleUri;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		ISelectionService service = window.getSelectionService();
		ISelection iSelection = service.getSelection();
		IStructuredSelection iStructuredSelection = (IStructuredSelection) iSelection;
		List<IResource> selection = iStructuredSelection.toList();

		createEditorInputsAndModelURIs(selection);
		IWorkspace iWorkspace = ResourcesPlugin.getWorkspace();

		CpEditorUtil.openResultInCpEditor(firstRuleUri, overlapUri, secondRuleUri);

		return null;
	}

	private void createEditorInputsAndModelURIs(List<IResource> selection) {

		try {

			selection.get(0).getParent();
			selection.get(0).getParent().exists();
			IResource[] members = selection.get(0).getParent().members();

			int i = members.length;

			int numberOfMembers = members.length;

			if (numberOfMembers != 5)
				System.err.println("falsche Anzahl an Dateien im Verzeichnis!");

			for (IResource resource : members) {

				IPath rawLocation = resource.getRawLocation();
				java.net.URI rawLocationURI = resource.getRawLocationURI();

				java.net.URI locationURI = resource.getLocationURI();

				if (resource instanceof IFile) {
					IFile iFile = (IFile) resource;
					String fileName = iFile.getName();
					if (fileName.startsWith("(1)")) {
						FileEditorInput firstRuleFileEditorInput = new FileEditorInput(iFile);
						boolean firstRuleFileEditorInputExists = firstRuleFileEditorInput.exists();
						System.out.println("firstRuleFileEditorInput exists:" + firstRuleFileEditorInputExists);
						// fullPathOfFirstRule = iFile.getFullPath();
						firstRuleUri = URI.createFileURI(resource.getRawLocation().toString());
						// editorInputs[0] = firstRuleFileEditorInput;
					} else if (fileName.startsWith("(2)")) {
						FileEditorInput secondRuleFileEditorInput = new FileEditorInput(iFile);
						boolean secondRuleFileEditorInputExists = secondRuleFileEditorInput.exists();
						System.out.println("secondRuleFileEditorInput exists:" + secondRuleFileEditorInputExists);
						// fullPathOfMinimalModel = iFile.getFullPath();
						secondRuleUri = URI.createFileURI(resource.getRawLocation().toString());
						// editorInputs[1] = secondRuleFileEditorInput;
					} else if (fileName.endsWith(".henshinCp")) {
						// the specific file extension should be placed in a central field, being accessible from
						// different classes.

					} else if (fileName.equals("minimal-model.ecore_diagram")) {
						FileEditorInput minimalModelFileEditorInput = new FileEditorInput(iFile);
						boolean minimalModelFileEditorInputExists = minimalModelFileEditorInput.exists();
						System.out.println("minimalModelFileEditorInput exists:" + minimalModelFileEditorInputExists);
						// fullPathOfSecondRule = iFile.getFullPath();
						overlapUri = URI.createFileURI(resource.getRawLocation().toString());
						// editorInputs[1] = minimalModelFileEditorInput;
					}
				}
			}
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
	}

}
