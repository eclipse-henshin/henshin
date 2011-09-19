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
package org.eclipse.emf.henshin.interpreter.ui.wizard.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class ParameterEditTable {
	
	protected int CONTROL_OFFSET = 5;
	
	protected Collection<ParameterChangeListener> listeners = new ArrayList<ParameterEditTable.ParameterChangeListener>();
	
	protected TableViewer tableViewer;
	
	protected Group container;
	
	public ParameterEditTable(Composite parent) {
		container = new Group(parent, SWT.NONE);
		container.setText(InterpreterUIPlugin.LL("_UI_SetParameters"));
		container.setLayout(new FormLayout());
		tableViewer = new TableViewer(container, SWT.FULL_SELECTION);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			data.bottom = new FormAttachment(100, -CONTROL_OFFSET);
			data.height = 80;
			tableViewer.getTable().setLayoutData(data);
			tableViewer.getTable().setLinesVisible(true);
			tableViewer.getTable().setHeaderVisible(true);
			
		}
		buildColumns();
		
		tableViewer.setContentProvider(new IStructuredContentProvider() {
			
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				
			}
			
			@Override
			public Object[] getElements(Object inputElement) {
				@SuppressWarnings("unchecked")
				Map<Parameter, String> pMap = (Map<Parameter, String>) inputElement;
				return pMap.entrySet().toArray();
			}
			
			@Override
			public void dispose() {
			}
		});
		
	}
	
	public Control getControl() {
		return container;
	}
	
	protected void buildColumns() {
		TableViewerColumn keyColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			keyColumn.getColumn().setText(InterpreterUIPlugin.LL("_UI_ParameterColumn_Name"));
			keyColumn.getColumn().setWidth(100);
			keyColumn.setLabelProvider(new ColumnLabelProvider() {
				
				@SuppressWarnings("unchecked")
				@Override
				public String getText(Object entry) {
					return ((Entry<Parameter, String>) entry).getKey().getName();
				}
			});
		}
		
		TableViewerColumn valueColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			valueColumn.getColumn().setText(InterpreterUIPlugin.LL("_UI_ParameterColumn_Value"));
			valueColumn.getColumn().setWidth(100);
			valueColumn.setLabelProvider(new ColumnLabelProvider() {
				@SuppressWarnings("unchecked")
				@Override
				public String getText(Object entry) {
					return ((Entry<Parameter, String>) entry).getValue();
				}
			});
			valueColumn.setEditingSupport(new EditingSupport(tableViewer) {
				
				@SuppressWarnings("unchecked")
				@Override
				protected void setValue(Object entry, Object value) {
					((Entry<Parameter, String>) entry).setValue(value.toString());
					for (ParameterChangeListener l : listeners)
						l.parameterChanged(((Entry<Parameter, String>) entry).getKey(),
								value.toString());
					tableViewer.refresh();
				}
				
				@SuppressWarnings("unchecked")
				@Override
				protected Object getValue(Object entry) {
					return ((Entry<Parameter, String>) entry).getValue();
				}
				
				@Override
				protected CellEditor getCellEditor(Object element) {
					return new TextCellEditor(tableViewer.getTable());
				}
				
				@Override
				protected boolean canEdit(Object element) {
					return true;
				}
			});
		}
	}
	
	public void addParameterChangeListener(ParameterChangeListener listener) {
		listeners.add(listener);
	}
	
	public static interface ParameterChangeListener {
		void parameterChanged(Parameter parameter, String value);
	}
	
	public void setParameters(Map<Parameter, String> parameters) {
		tableViewer.setInput(parameters);
	}
	
}
