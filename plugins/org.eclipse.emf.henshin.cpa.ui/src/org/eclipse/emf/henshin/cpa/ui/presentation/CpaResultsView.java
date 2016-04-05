/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.ui.presentation;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.cpa.persist.CriticalPairNode;
import org.eclipse.emf.henshin.cpa.persist.RootElement;
import org.eclipse.emf.henshin.cpa.persist.TreeFolder;
import org.eclipse.emf.henshin.cpa.ui.util.CpEditorUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;

/**
 * This view lists the result of a critical pair analysis run and provides the ability to open the conjugate files of a
 * single result(the both involved rules and the minimal model).
 * 
 * @author Kristopher Born
 *
 */
public class CpaResultsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.henshin.cpa.ui.views.CPAView";

	private TreeViewer viewer;
	private Action doubleClickAction;
	private HashMap<String, Set<CriticalPairNode>> content;

	class CPAViewContentProvider implements ITreeContentProvider {

		private RootElement invisibleRoot;

		void initialize() {
			invisibleRoot = new RootElement();

			for (String ruleCombinationName : content.keySet()) {
				TreeFolder treeFolder = new TreeFolder(ruleCombinationName);
				invisibleRoot.addChild(treeFolder);

				Set<CriticalPairNode> theCriticalPairsForTheRulecombination = content.get(ruleCombinationName);

				for (CriticalPairNode criticalPairNode : theCriticalPairsForTheRulecombination) {
					treeFolder.addChild(criticalPairNode);
				}
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged()
		 */
		@Override
		public void dispose() {
			// Nothing to do yet.
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 * java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// Nothing to do yet.
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
		 */
		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement.equals(getViewSite())) {
				if (invisibleRoot == null) {
					invisibleRoot = new RootElement();
				}
				return getChildren(invisibleRoot);
			}
			return getChildren(inputElement);

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		@Override
		public Object getParent(Object element) {
			if (element instanceof CriticalPairNode) {
				return ((CriticalPairNode) element).getParent();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
		 */
		@Override
		public boolean hasChildren(Object element) {
			if (element instanceof TreeFolder) {
				return ((TreeFolder) element).hasChildren();
			} else {
				return false;
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
		 */
		@Override
		public Object[] getChildren(Object parentElement) {
			if (parentElement instanceof TreeFolder) {
				return ((TreeFolder) parentElement).getChildren();
			} else if (parentElement instanceof RootElement) {
				return ((RootElement) parentElement).getChildren();
			}
			return new Object[0];
		}
	}

	class ViewLabelProvider extends LabelProvider {

		public String getText(Object obj) {
			return obj.toString();
		}

		public Image getImage(Object obj) {
			String imageKey = ISharedImages.IMG_OBJ_ELEMENT;
			if (obj instanceof TreeFolder)
				imageKey = ISharedImages.IMG_OBJ_FOLDER;
			return PlatformUI.getWorkbench().getSharedImages().getImage(imageKey);
		}
	}

	class NameSorter extends ViewerSorter {
	}

	public TreeViewer getTreeViewer() {
		return viewer;
	}

	public void update() {
		CPAViewContentProvider content = new CPAViewContentProvider();
		content.initialize();
		viewer.setContentProvider(content);
	}

	public void createPartControl(Composite parent) {
		viewer = new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setContentProvider(new CPAViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(getViewSite());

		makeActions();
		hookDoubleClickAction();
	}

	private void makeActions() {

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				if (obj instanceof CriticalPairNode) {

					CriticalPairNode criticalPairNode = (CriticalPairNode) obj;

					URI firstRuleUri = criticalPairNode.getFirstRuleURI();
					URI overlapuri = criticalPairNode.getMinimalModelURI();
					URI secondRuleUri = criticalPairNode.getSecondRuleURI();

					CpEditorUtil.openResultInCpEditor(firstRuleUri, overlapuri, secondRuleUri);

				}
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	/**
	 * Opens the editor with the file of the provided <code>URI</code>.
	 * 
	 * @param theUri
	 */
	private void openEditor(URI theUri) {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(new Path(theUri.toFileString()));

		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		try {
			page.openEditor(new FileEditorInput(file),
					PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getFullPath().toString())
							.getId());

		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}

	public void setContent(HashMap<String, Set<CriticalPairNode>> persistedResults) {
		this.content = persistedResults;
	}
}