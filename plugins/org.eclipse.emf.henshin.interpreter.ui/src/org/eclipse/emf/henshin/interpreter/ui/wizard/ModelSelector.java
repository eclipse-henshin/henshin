/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.ui.wizard;

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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

/**
 * @author Gregor Bonifer
 * @author Stefan Jurack
 * @author Christian Krause
 */
public class ModelSelector {

	public static interface ModelSelectorListener {
		boolean modelURIChanged(String modelURI);
	}

	protected Collection<ModelSelectorListener> listeners = new ArrayList<ModelSelectorListener>();

	protected Group container;

	protected Button browseWorkspaceButton;

	protected Button browseFileSystemButton;

	protected Text uriField;

	public ModelSelector(Composite parent, final IResource baseDir,
			boolean output) {

		container = new Group(parent, SWT.NONE);
		container.setText(HenshinInterpreterUIPlugin
				.LL(output ? "_UI_OutputModel" : "_UI_InputModel"));
		container.setLayout(new GridLayout(3, false));

		GridData data;

		uriField = new Text(container, SWT.BORDER);
		data = new GridData(SWT.FILL, SWT.FILL, true, false);
		data.horizontalSpan = 3;
		uriField.setLayoutData(data);
		uriField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				fireURIChanged();
			}
		});
		uriField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				fireURIChanged();
			}
		});

		if (!output) {

			Label label = new Label(container, SWT.NONE);
			label.setText("");
			label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

			browseWorkspaceButton = new Button(container, SWT.PUSH);
			browseWorkspaceButton.setText(CommonUIPlugin.INSTANCE
					.getString("_UI_BrowseWorkspace_label"));
			browseWorkspaceButton.setLayoutData(new GridData(SWT.FILL,
					SWT.FILL, false, false));

			browseFileSystemButton = new Button(container, SWT.PUSH);
			browseFileSystemButton.setText(CommonUIPlugin.INSTANCE
					.getString("_UI_BrowseFileSystem_label"));
			browseFileSystemButton.setLayoutData(new GridData(SWT.FILL,
					SWT.FILL, false, false));

			browseWorkspaceButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					Object[] selection = baseDir == null ? new Object[0]
							: new Object[] { baseDir };
					IFile[] files = WorkspaceResourceDialog.openFileSelection(
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getShell(),
							HenshinInterpreterUIPlugin
									.LL("_UI_BrowseWorkspace_Title"),
							HenshinInterpreterUIPlugin
									.LL("_UI_BrowseWorkspace_Message"), false,
							selection, null);
					if (files.length != 1) {
						return;
					}
					IFile file = files[0];
					if (file != null) {
						URI uri = URI.createPlatformResourceURI(file
								.getFullPath().toString(), true);
						uriField.setText(uri.toString());
						fireURIChanged();
					}
				}
			});
			browseFileSystemButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					FileDialog fileDialog = new FileDialog(PlatformUI
							.getWorkbench().getActiveWorkbenchWindow()
							.getShell(), SWT.NONE);
					fileDialog.open();
					String path = fileDialog.getFilterPath();
					String fileName = fileDialog.getFileName();
					if (fileName != null) {
						URI uri = URI.createFileURI(path + File.separator
								+ fileName);
						uriField.setText(uri.toString());
						fireURIChanged();
					}
				}
			});
		}

	}

	protected void fireURIChanged() {
		for (ModelSelectorListener l : listeners) {
			l.modelURIChanged(uriField.getText());
		}
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

	public void setModelURI(String modelURI) {
		uriField.setText(modelURI);
	}

	public String getModelURI() {
		return uriField.getText();
	}

	public Button getBrowseWorkspaceButton() {
		return browseWorkspaceButton;
	}

	public Button getBrowseFileSystemButton() {
		return browseFileSystemButton;
	}
	
}
