/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.ui.presentation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.multicda.cpa.persist.RootElement;
import org.eclipse.emf.henshin.multicda.cpa.persist.SpanNode;
import org.eclipse.emf.henshin.multicda.cpa.persist.TreeFolder;
import org.eclipse.emf.henshin.multicda.cpa.ui.util.CpEditorUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
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
 * This view lists the result of a critical pair analysis run and provides the
 * ability to open the conjugate files of a single result(the both involved
 * rules and the minimal model).
 * 
 * @author Kristopher Born
 *
 */
public class CpaResultsView extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "org.eclipse.emf.henshin.multicda.cpa.ui.views.CPAView";

	private TreeViewer viewer;
	private Action doubleClickAction;
	private Map<String, List<SpanNode>> contentCDAB;
	private Map<String, List<SpanNode>> contentCDAC;
	private Map<String, List<SpanNode>> contentCDAF;
	private Map<String, List<SpanNode>> initialCpaResult;
	private Map<String, List<SpanNode>> essentialCpaResult;
	private Map<String, List<SpanNode>> otherCpaResult;

	class CPAViewContentProvider implements ITreeContentProvider {

		private RootElement invisibleRoot;

		void initialize() {
			invisibleRoot = new RootElement();
			TreeFolder B = new TreeFolder("Binary");
			TreeFolder C = new TreeFolder("Coarse");
			TreeFolder F = new TreeFolder("Fine");
			TreeFolder SF = new TreeFolder("Very fine");

			TreeFolder iCP = new TreeFolder("Initial conflicts / intial dependencies");
			TreeFolder eCP = new TreeFolder("Further essential critical pairs");
			TreeFolder CP = new TreeFolder("Further critical pairs");

			for (String ruleCombinationName : contentCDAC.keySet()) {
				TreeFolder treeFolder = new TreeFolder(ruleCombinationName);
				C.addChild(treeFolder);

				List<SpanNode> theCriticalPairsForTheRulecombination = contentCDAC.get(ruleCombinationName);

				for (SpanNode spanNode : theCriticalPairsForTheRulecombination) {
					treeFolder.addChild(spanNode);
				}
			}
			for (String ruleCombinationName : contentCDAF.keySet()) {
				TreeFolder treeFolder = new TreeFolder(ruleCombinationName);
				F.addChild(treeFolder);

				List<SpanNode> theCriticalPairsForTheRulecombination = contentCDAF.get(ruleCombinationName);

				for (SpanNode spanNode : theCriticalPairsForTheRulecombination) {
					treeFolder.addChild(spanNode);
				}
			}
			for (String ruleCombinationName : contentCDAB.keySet()) {
				String c = "";
				String d = "";
				for (SpanNode spanNodes : contentCDAB.get(ruleCombinationName))
					if (spanNodes.conflict)
						c = "conflicts";
					else
						d = "dependencies";
				String name = ruleCombinationName + ": " + c + (!c.isEmpty() && !d.isEmpty() ? " and " : " ") + d
						+ " detected.";
				TreeFolder treeFolder = new TreeFolder(name);
				B.addChild(treeFolder);
			}

			for (String ruleCombinationName : initialCpaResult.keySet()) {
				TreeFolder treeFolder = new TreeFolder(ruleCombinationName);
				iCP.addChild(treeFolder);
				List<SpanNode> theCriticalPairsForTheRulecombination = initialCpaResult.get(ruleCombinationName);
				for (SpanNode spanNode : theCriticalPairsForTheRulecombination) {
					treeFolder.addChild(spanNode);
				}
			}
			for (String ruleCombinationName : essentialCpaResult.keySet()) {
				TreeFolder treeFolder = new TreeFolder(ruleCombinationName);
				eCP.addChild(treeFolder);
				List<SpanNode> theCriticalPairsForTheRulecombination = essentialCpaResult.get(ruleCombinationName);
				for (SpanNode spanNode : theCriticalPairsForTheRulecombination) {
					treeFolder.addChild(spanNode);
				}
			}
			for (String ruleCombinationName : otherCpaResult.keySet()) {
				TreeFolder treeFolder = new TreeFolder(ruleCombinationName);
				CP.addChild(treeFolder);
				List<SpanNode> theCriticalPairsForTheRulecombination = otherCpaResult.get(ruleCombinationName);
				for (SpanNode spanNode : theCriticalPairsForTheRulecombination) {
					treeFolder.addChild(spanNode);
				}
			}
			if (B.hasChildren())
				invisibleRoot.addChild(B);
			if (C.hasChildren())
				invisibleRoot.addChild(C);
			if (F.hasChildren())
				invisibleRoot.addChild(F);

			if (iCP.hasChildren())
				SF.addChild(iCP);
			if (eCP.hasChildren())
				SF.addChild(eCP);
			if (CP.hasChildren())
				SF.addChild(CP);
			if (SF.hasChildren())
				invisibleRoot.addChild(SF);
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
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.
		 * viewers.Viewer, java.lang.Object)
		 */
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// Nothing to do yet.
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getElements(java.lang.Object)
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
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object)
		 */
		@Override
		public Object getParent(Object element) {
			if (element instanceof SpanNode) {
				return ((SpanNode) element).getParent();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.Object)
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
		 * @see
		 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.Object)
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
		viewer.setInput(getViewSite());

		makeActions();
		hookDoubleClickAction();
	}

	private void makeActions() {

		doubleClickAction = new Action() {
			public void run() {
				ISelection selection = viewer.getSelection();
				Object obj = ((IStructuredSelection) selection).getFirstElement();
				if (obj instanceof SpanNode) {

					SpanNode spanNode = (SpanNode) obj;

					URI firstRuleUri = spanNode.getFirstRuleURI();
					URI overlapuri = spanNode.getMinimalModelURI();
					URI secondRuleUri = spanNode.getSecondRuleURI();

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
			page.openEditor(new FileEditorInput(file), PlatformUI.getWorkbench().getEditorRegistry()
					.getDefaultEditor(file.getFullPath().toString()).getId());

		} catch (PartInitException e1) {
			e1.printStackTrace();
		}
	}

	public void setContent(Map<String, List<SpanNode>> persistedB, Map<String, List<SpanNode>> persistedC,
			Map<String, List<SpanNode>> persistedF, Map<String, List<SpanNode>> initialCpaResult,
			Map<String, List<SpanNode>> essentialCpaResult, Map<String, List<SpanNode>> otherCpaResult) {
		this.contentCDAB = persistedB;
		this.contentCDAC = persistedC;
		this.contentCDAF = persistedF;
		this.initialCpaResult = initialCpaResult;
		this.essentialCpaResult = essentialCpaResult;
		this.otherCpaResult = otherCpaResult;
	}
}