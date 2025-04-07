/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.ui.results;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorLauncher;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.registry.EditorDescriptor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiEditorInput;

public class MultiEditorLauncher implements IEditorLauncher {

	public void open(IPath file) {
		String[] editorIds = new String[] { "org.eclipse.emf.henshin.presentation.HenshinEditorID",
				"org.eclipse.emf.ecore.presentation.EcoreEditorID",
				"org.eclipse.emf.henshin.presentation.HenshinEditorID" };

		MultiEditorInput mInput = new MultiEditorInput(editorIds, resolveFileEditorInputs(file));

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			EditorDescriptor editorDescriptor = (EditorDescriptor) IDE.getEditorDescriptor(ResourcesPlugin
					.getWorkspace().getRoot().getFileForLocation(file));
			editorDescriptor.setOpenMode(EditorDescriptor.OPEN_INTERNAL);
			editorDescriptor.isInternal();
			editorDescriptor.isOpenExternal();
			IDE.openEditor(page, mInput, "org.eclipse.emf.henshin.multicda.cpa.ui.HenshinCpEditorID", true);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}

	private IEditorInput[] resolveFileEditorInputs(IPath file) {

		IPath pathOfDummyFile = file;

		IPath cloneOfPathOfDummyFile = (IPath) pathOfDummyFile.clone();
		IPath pathOfCriticalPair = cloneOfPathOfDummyFile.removeLastSegments(1);
		IPath pathOfLeftRule = pathOfCriticalPair.append("firstRule.henshin");
		IPath pathOfRightRule = pathOfCriticalPair.append("secondRule.henshin");
		IPath pathOfMinimalModel = pathOfCriticalPair.append("minimal-model.ecore_diagram");

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot workspaceRoot = workspace.getRoot();

		IFile fileOfFirstRule = workspaceRoot.getFileForLocation(pathOfLeftRule);
		IFile fileOfMinimalModel = workspaceRoot.getFileForLocation(pathOfMinimalModel);
		IFile fileOfSecondRule = workspaceRoot.getFileForLocation(pathOfRightRule);

		FileEditorInput firstRuleFileEditorInput = new FileEditorInput(fileOfFirstRule);
		boolean firstRuleFileEditorInputExists = firstRuleFileEditorInput.exists();
		System.out.println("firstRuleFileEditorInput exists:" + firstRuleFileEditorInputExists);

		FileEditorInput minimalModelFileEditorInput = new FileEditorInput(fileOfMinimalModel);
		boolean minimalModelFileEditorInputExists = minimalModelFileEditorInput.exists();
		System.out.println("firstRuleFileEditorInput exists:" + minimalModelFileEditorInputExists);

		FileEditorInput secondRuleFileEditorInput = new FileEditorInput(fileOfSecondRule);
		boolean secondRuleFileEditorInputExists = secondRuleFileEditorInput.exists();
		System.out.println("secondRuleFileEditorInput exists:" + secondRuleFileEditorInputExists);

		return new IEditorInput[] { firstRuleFileEditorInput, minimalModelFileEditorInput, secondRuleFileEditorInput };
	}
}
