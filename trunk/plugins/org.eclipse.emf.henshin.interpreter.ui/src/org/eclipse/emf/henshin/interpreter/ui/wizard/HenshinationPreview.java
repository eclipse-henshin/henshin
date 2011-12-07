/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.ui.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.eclipse.emf.compare.ui.editor.ModelCompareEditorInput;
import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class HenshinationPreview implements HenshinationResultView {
	
	protected ModelCompareEditorInput editorInput;
	
	protected HenshinationResult henshinationResult;
	Composite cc;
	
	public HenshinationPreview(ModelCompareEditorInput compareInput,
			HenshinationResult henshinationResult) {
		this.editorInput = compareInput;
		this.henshinationResult = henshinationResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.interpreter.ui.wizard.HenshinationResultView#
	 * showDialog(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	public void showDialog(final Shell shell) {
		
		boolean result = false;
		try {
			PlatformUI.getWorkbench().getProgressService().run(true, true, editorInput);
			result = editorInput.getMessage() == null && editorInput.getCompareResult() != null;
		} catch (InterruptedException e) {
		} catch (InvocationTargetException e) {
		}
		if (!result) {
			MessageDialog.openError(shell, InterpreterUIPlugin.LL("_UI_Preview_ComparisonError"),
					InterpreterUIPlugin.LL("_UI_Preview_ComparisonError_msg"));
			return;
		}
		
		editorInput.setTitle(InterpreterUIPlugin.LL("_UI_Preview_Title"));
		editorInput.getCompareConfiguration().setRightLabel(
				InterpreterUIPlugin.LL("_UI_Preview_OriginalModel"));
		editorInput.getCompareConfiguration().setLeftLabel(
				InterpreterUIPlugin.LL("_UI_Preview_TransformedModel"));
		
		editorInput.getCompareConfiguration().setLeftEditable(false);
		editorInput.getCompareConfiguration().setRightEditable(false);
		
		Dialog dlg = new Dialog(shell) {
			
			@Override
			protected void configureShell(Shell newShell) {
				super.configureShell(newShell);
				newShell.setText(InterpreterUIPlugin.LL("_UI_Preview_Title"));
			}
			
			@Override
			protected IDialogSettings getDialogBoundsSettings() {
				return InterpreterUIPlugin.getPlugin().getDialogSettings();
			}
			
			@Override
			protected Control createDialogArea(Composite parent) {
				Composite container = (Composite) super.createDialogArea(parent);
				
				createTabFolder(container).setLayoutData(new GridData(GridData.FILL_BOTH));
				return container;
			}
			
			@Override
			protected boolean isResizable() {
				return true;
			}
			
			protected void createButtonsForButtonBar(Composite parent) {
				createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
			}
			
		};
		
		dlg.open();
	}
	
	public Control createTabFolder(Composite parent) {
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		
		// Compare Tab
		//
		TabItem compareTabItem = new TabItem(tabFolder, SWT.NONE);
		compareTabItem.setText(InterpreterUIPlugin.LL("_UI_Preview_CompareTab"));
		compareTabItem.setControl(editorInput.createContents(tabFolder));
		
		// Parameter Tab
		//
		TabItem parameterTabItem = new TabItem(tabFolder, SWT.NONE);
		parameterTabItem.setText(InterpreterUIPlugin.LL("_UI_Preview_ParameterTab"));
		parameterTabItem.setControl(createParameterTable(tabFolder));
		
		return tabFolder;
	}
	
	protected Control createParameterTable(Composite parent) {
		
		// TableViewer
		//
		TableViewer tableViewer = new TableViewer(parent, SWT.FULL_SELECTION);
		tableViewer.getTable().setLinesVisible(true);
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.setContentProvider(new ArrayContentProvider());
		
		// Column: Parameter Name
		//
		TableViewerColumn parameterNameColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		parameterNameColumn.getColumn().setText(
				InterpreterUIPlugin.LL("_UI_Preview_ParameterTable_ParameterNameColumn"));
		parameterNameColumn.getColumn().setWidth(200);
		parameterNameColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((Parameter) element).getName();
			}
		});
		
		// Column: Parameter Value before Transformation
		//
		TableViewerColumn parameterValueBeforeColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		parameterValueBeforeColumn.getColumn().setText(
				InterpreterUIPlugin.LL("_UI_Preview_ParameterTable_ValueBeforeColumn"));
		parameterValueBeforeColumn.getColumn().setWidth(200);
		parameterValueBeforeColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Parameter parameter = (Parameter) element;
				ParameterConfiguration pCfg = henshinationResult.getHenshination()
						.getParameterConfiguration(parameter.getName());
				if (pCfg.isClear())
					return "";
				return pCfg.getValue() == null ? "null" : pCfg.getValue().toString();
				
			}
		});
		
		// Column: Parameter Value after Transformation
		//
		TableViewerColumn parameterValueAfterColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		parameterValueAfterColumn.getColumn().setText(
				InterpreterUIPlugin.LL("_UI_Preview_ParameterTable_ValueAfterColumn"));
		parameterValueAfterColumn.getColumn().setWidth(200);
		parameterValueAfterColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				Parameter parameter = (Parameter) element;
				Map<Parameter, Object> pValues = henshinationResult.getUnitApplication()
						.getParameterValues();
				Object value = "";
				if (pValues.containsKey(parameter)) {
					value = pValues.get((Parameter) element);
					value = value == null ? "null" : value;
				}
				return value.toString();
			}
		});
		
		tableViewer.setInput(henshinationResult.getUnitApplication().getTransformationUnit()
				.getParameters());
		return tableViewer.getTable();
	}
}
