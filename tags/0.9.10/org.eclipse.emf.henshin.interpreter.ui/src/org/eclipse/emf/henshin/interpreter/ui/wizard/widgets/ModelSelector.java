/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard.widgets;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.ui.CommonUIPlugin;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

/**
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class ModelSelector {
	
	protected Collection<ModelSelectorListener> listeners = new ArrayList<ModelSelectorListener>();
	
	protected static int CONTROL_OFFSET = 5;
	
	protected Group container;
	
	protected Button checkResourceButton;
	
	protected Button browseWorkspaceButton;
	
	protected Button browseFileSystemButton;
	
	protected Button newFileButton;
	
	protected Combo uriField;

	public ModelSelector(Composite parent, final IResource baseDir) {
		
		container = new Group(parent, SWT.NONE);
		container.setText(HenshinInterpreterUIPlugin.LL("_UI_SelectModel"));
		container.setLayout(new FormLayout());
		Composite buttonComp = new Composite(container, SWT.NONE);
		{
			buttonComp.setLayout(new FormLayout());
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			buttonComp.setLayoutData(data);
		}
		
		browseWorkspaceButton = new Button(buttonComp, SWT.PUSH);
		browseWorkspaceButton.setText(CommonUIPlugin.INSTANCE
				.getString("_UI_BrowseWorkspace_label"));
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			browseWorkspaceButton.setLayoutData(data);
		}
		
		browseFileSystemButton = new Button(buttonComp, SWT.PUSH);
		browseFileSystemButton.setText(CommonUIPlugin.INSTANCE
				.getString("_UI_BrowseFileSystem_label"));
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(browseWorkspaceButton, -CONTROL_OFFSET);
			browseFileSystemButton.setLayoutData(data);
		}
		
		Composite fieldComp = new Composite(container, SWT.NONE);
		{
			fieldComp.setLayout(new FormLayout());
			FormData data = new FormData();
			data.top = new FormAttachment(buttonComp, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			//data.bottom = new FormAttachment(100, -CONTROL_OFFSET);
			fieldComp.setLayoutData(data);
		}
		
		checkResourceButton = new Button(fieldComp, SWT.PUSH);
		checkResourceButton.setText(HenshinInterpreterUIPlugin.LL("_UI_CheckResource"));
		checkResourceButton.setEnabled(false);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			checkResourceButton.setLayoutData(data);
		}
		
		uriField = new Combo(fieldComp, SWT.BORDER);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(checkResourceButton, -CONTROL_OFFSET);
			uriField.setLayoutData(data);
		}
		uriField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				checkResourceButton.setEnabled(true);
				fireURIFieldDirty();
			}
		});
		
		uriField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fireURIChanged();
			}
		});
		
		Composite newFileComp = new Composite(container, SWT.NONE);
		{
			newFileComp.setLayout(new FormLayout());
			FormData data = new FormData();
			data.top = new FormAttachment(fieldComp, CONTROL_OFFSET);
			data.left = new FormAttachment(buttonComp, 0, SWT.LEFT);
			data.right = new FormAttachment(buttonComp, 0, SWT.RIGHT);
			newFileComp.setLayoutData(data);
		}
		
		newFileButton = new Button(newFileComp, SWT.CHECK);
		newFileButton.setAlignment(SWT.CENTER);
		newFileButton.setText(HenshinInterpreterUIPlugin.LL("_UI_CreateNewFile"));
		//newFileButton.setBounds(272, 10, 144, 18);
		newFileButton.setSelection(HenshinInterpreterUIPlugin.getPlugin().getPreferenceStore().getBoolean("createNewFile"));
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, 5);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			newFileButton.setLayoutData(data);
		}
		// uriField.set
		
		// ControlDecoration f = new ControlDecoration(uriField, SWT.LEFT);
		// f.setDescriptionText("Model Description_NOLL");
		
		// URL imgURL = (URL)
		// TransformatorPlugin.getPlugin().getImage("Henshin_small.png");
		
		// f.setImage(PlatformUI.getWorkbench().getSharedImages()
		// .getImage(ISharedImages.IMG_DEF_VIEW));
		
		checkResourceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fireURIChanged();
			}
		});
		
		browseWorkspaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Object[] selection = baseDir==null ? new Object[0] : new Object[] { baseDir };
				IFile[] files = WorkspaceResourceDialog.openFileSelection(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), HenshinInterpreterUIPlugin.LL("_UI_BrowseWorkspace_Title"), HenshinInterpreterUIPlugin.LL("_UI_BrowseWorkspace_Message"), 
						false, selection, null);
				if (files.length != 1)
					return;
				IFile file = files[0];
				if (file != null) {
					URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
					uriField.setText(uri.toString());
					fireURIChanged();
				}
			}
		});
		
		browseFileSystemButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				FileDialog fileDialog = new FileDialog(PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getShell(), SWT.NONE);
				fileDialog.open();
				String path = fileDialog.getFilterPath();
				String fileName = fileDialog.getFileName();
				if (fileName != null) {
					URI uri = URI.createFileURI(path + File.separator + fileName);
					uriField.setText(uri.toString());
					fireURIChanged();
				}
			}
		});
		
		
		newFileButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HenshinInterpreterUIPlugin.getPlugin().getPreferenceStore().setValue("createNewFile", newFileButton.getSelection());
			}
		});
	}
	
	protected void fireURIChanged() {
		checkResourceButton.setEnabled(false);
		for (ModelSelectorListener l : listeners)
			l.modelURIChanged(URI.createURI(uriField.getText()));
	}
	
	protected void fireURIFieldDirty() {
		for (ModelSelectorListener l : listeners)
			l.uriFieldDirty();
	}
	
	public void setLastUsedModels(String[] models) {
		uriField.setItems(models);
		uriField.select(0);
	}
	
	/**
	 * @return
	 */
	public Control getControl() {
		return container;
	}
	
	public void addModelSelectorListener(ModelSelectorListener listener) {
		listeners.add(listener);
	}
	
	public static interface ModelSelectorListener {
		
		boolean modelURIChanged(URI modelURI);
		
		void uriFieldDirty();
	}
}
