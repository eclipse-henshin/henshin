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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.henshin.multicda.cpa.ui.results.MultiEditorSelectionProvider;
import org.eclipse.emf.henshin.presentation.HenshinEditor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.part.MultiEditor;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.ui.views.properties.PropertySheet;

public class HenshinCPEditor extends MultiEditor {

	/** Default editor index after opening MultiEditor. */
	public static final int DEFAULT_INDEX_ACTIVE_EDITOR = 0;

	/** Constant MultiEditor ID. */
	public static final String MULTI_EDITOR_ID = "org.eclipse.emf.henshin.multicda.cpa.ui.HenshinCPEditor";

	/** Property listener of inner editors. */
	private List<IPropertyListener> propertyListenerEditorParts = new ArrayList<IPropertyListener>(3);

	/** The selection changed listener of inner editors. */
	private List<ISelectionChangedListener> selectionListenerEditorParts = new ArrayList<ISelectionChangedListener>(3);

	/** The tabs which cover the specialized editors. */
	private Item[] tabItems = new Item[3];

	/** The controls which are placed in the tabs to include the editors. */
	private ViewForm[] editorContainer = new ViewForm[3];

	/** TabFolder which contains three editor-tabs. */
	private SashForm folder;

	// ----------- Constructor and methods -----------

	/**
	 * Constructor calls super class and is setting inner editors.
	 */
	public HenshinCPEditor() {
		super();
		IEditorPart[] parts = { new HenshinEditor(), new EcoreEditor(), new HenshinEditor() };
		setChildren(parts);
	}

	@Override
	protected void drawGradient(IEditorPart innerEditor, Gradient g) {

		Item item = tabItems[getIndex(innerEditor)];

		if (item == null || item.isDisposed()) {
			return;
		}

	}

	/**
	 * Create part control = editor build (here customized).
	 */
	@Override
	public void createPartControl(Composite parent) {
		parent = new Composite(parent, SWT.BORDER);
		parent.setLayout(new FillLayout());
		// TabFolder uses the native tab folder widget, while CTabFolder is emulated.
		folder = new SashForm(parent, SWT.NONE);

		folder.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
			}

			@Override
			public void focusGained(FocusEvent e) {
				// folder.setSelection(getIndex(getActiveEditor())); //seems to be superfluous?
				updateOutlineView();
				updatePropertyView();
				setFocus();
			}
		});

		createInnerEditorTabs();
		// Method is empty in superclass
		innerEditorsCreated();
		activateEditor(getInnerEditors()[DEFAULT_INDEX_ACTIVE_EDITOR]);
		updateActionBarContributor(DEFAULT_INDEX_ACTIVE_EDITOR);
		setFocus();
	}

	/**
	 * Creates the inner tabs.
	 */
	private void createInnerEditorTabs() {
		IEditorPart innerEditors[] = getInnerEditors();
		for (int i = 0; i < innerEditors.length; i++) {
			final IEditorPart editorPart = innerEditors[i];

			ViewForm viewForm = new ViewForm(folder, SWT.NONE);
			viewForm.marginWidth = 0;
			viewForm.marginHeight = 0;

			Composite content = createInnerPartControl(viewForm, editorPart);
			viewForm.setContent(content);
			editorContainer[i] = viewForm;

			selectionListenerEditorParts.add(i, new ISelectionChangedListener() {
				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					if (event != null) {
						PropertySheet propertyView = (PropertySheet) getSite().getPage().findView(
								"org.eclipse.ui.views.PropertySheet");
						if (propertyView != null) {
							propertyView.selectionChanged(getActiveEditor(), event.getSelection());
							updateSelectionProvider(event);
						}
					}
				}
			});
			if (editorPart != null) {
				// System.err.println("ACHTUNG!!! - KEINE AHNUNG WIE ICH DAS LÖSEN SOLL!!!");
				// ((DiagramDocumentEditor)e).getDiagramGraphicalViewer().addSelectionChangedListener(selectionListenerEditorParts.get(i));
			}
		}
	}

	/**
	 * Handles a page/tab change.
	 * 
	 * @param newPageIndex New tab/page index
	 */
	protected void pageChange(int newPageIndex) {
		// folder.setSelection(newPageIndex); //superfluous?
		updateActionBarContributor(newPageIndex);
		updateOutlineView();
		updatePropertyView();
		updateSelectionProvider(null);
		activateEditor(getInnerEditors()[newPageIndex]);
		setFocus();
	}

	/**
	 * Update the outline view.
	 */
	private void updateOutlineView() {
		ContentOutline outlineView = (ContentOutline) getSite().getPage().findView(
				"org.eclipse.ui.views.ContentOutline");
		if (outlineView != null) {
			outlineView.partActivated(getActiveEditor());
		}
	}

	/**
	 * Update the property view.
	 */
	private void updatePropertyView() {
		PropertySheet propertyView = (PropertySheet) getSite().getPage().findView("org.eclipse.ui.views.PropertySheet");
		if (propertyView != null) {
			propertyView.partActivated(getActiveEditor());
		}
	}

	/**
	 * Notifies action bar contributor about page change.
	 *
	 * @param pageIndex the index of the new page
	 */
	protected void updateActionBarContributor(int pageIndex) {

	}

	/**
	 * Update selection provider.
	 */
	private void updateSelectionProvider(SelectionChangedEvent event) {
		ISelectionProvider selectionProvider = getActiveEditor().getSite().getSelectionProvider();
		if (selectionProvider != null) {
			ISelectionProvider outerProvider = getSite().getSelectionProvider();
			if (outerProvider instanceof MultiEditorSelectionProvider) {
				if (event == null) {
					event = new SelectionChangedEvent(selectionProvider, selectionProvider.getSelection());
				}
				MultiEditorSelectionProvider provider = (MultiEditorSelectionProvider) outerProvider;
				provider.fireSelectionChanged(event);
			}
		}
	}

}
