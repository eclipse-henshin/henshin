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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.henshin.interpreter.ui.HenshinInterpreterUIPlugin;
import org.eclipse.emf.henshin.interpreter.ui.util.ParameterConfig;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
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
import org.eclipse.swt.widgets.TableColumn;

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
		container.setText(HenshinInterpreterUIPlugin.LL("_UI_Parameters"));
		container.setLayout(new FormLayout());
		tableViewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.BORDER);
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
				Collection<ParameterConfig> paramCfgs = (Collection<ParameterConfig>) inputElement;	
				Iterator<ParameterConfig> it = paramCfgs.iterator();
				
				//Remove configs of parameters that may not be set from outside (OUT & VAR)
				while(it.hasNext()) {
					ParameterConfig paramCfg = it.next();
					if(paramCfg.getKind() == ParameterKind.VAR || paramCfg.getKind() == ParameterKind.OUT) {
						it.remove();
					}
				}
				
				return paramCfgs.toArray();
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
			keyColumn.getColumn().setText(HenshinInterpreterUIPlugin.LL("_UI_ParameterColumn_Name"));
			keyColumn.getColumn().setWidth(100);
			keyColumn.setLabelProvider(new ColumnLabelProvider() {
				
				@Override
				public String getText(Object entry) {
					return ((ParameterConfig) entry).getName();
				}
			});
		}
		
		TableViewerColumn kindColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			kindColumn.getColumn().setText(HenshinInterpreterUIPlugin.LL("_UI_ParameterColumn_Kind"));
			kindColumn.getColumn().setWidth(100);
			kindColumn.setLabelProvider(new ColumnLabelProvider() {
				
				@Override
				public String getText(Object entry) {
					return ((ParameterConfig) entry).getKind().getAlias();
				}
			});
		}
		
		TableViewerColumn typeColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			typeColumn.getColumn().setText(HenshinInterpreterUIPlugin.LL("_UI_ParameterColumn_Type"));
			typeColumn.getColumn().setWidth(100);
			typeColumn.setLabelProvider(new ColumnLabelProvider() {
				
				@Override
				public String getText(Object element) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					return paramCfg.getTypeLabel();
				}
			});
			typeColumn.setEditingSupport(new EditingSupport(tableViewer) {
				
				@Override
				protected void setValue(Object element, Object value) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					
					paramCfg.setType((Integer) value);
					
					for (ParameterChangeListener l : listeners)
						l.parameterChanged(paramCfg);
					tableViewer.refresh();
				}
				
				@Override
				protected Object getValue(Object element) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					return paramCfg.getType();
				}
				
				@Override
				protected CellEditor getCellEditor(Object element) {
					return new ComboBoxCellEditor(tableViewer.getTable(),
							ParameterConfig.getSupportedTypes().values().toArray(new String[0]),
							SWT.READ_ONLY);
				}
				
				@Override
				protected boolean canEdit(Object element) {
					return !((ParameterConfig) element).isUnset();
				}
				
				
			});
		}
		
		TableViewerColumn valueColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			valueColumn.getColumn().setText(HenshinInterpreterUIPlugin.LL("_UI_ParameterColumn_Value"));
			valueColumn.getColumn().setWidth(100);
			valueColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					switch (paramCfg.getType()) {
						case ParameterConfig.CLEAR:
							return "";
						case ParameterConfig.NULL:
							return "null";
						case ParameterConfig.STRING:
							if (paramCfg.getValue() == null) {
								return "null";
							}
							return "\"" + paramCfg.getValue() + "\"";
						default:
							return paramCfg.getValue() + "";
					}
				}
			});
			
			valueColumn.setEditingSupport(new EditingSupport(tableViewer) {
				
				@Override
				protected void setValue(Object element, Object value) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					try {
						switch (paramCfg.getType()) {
							case ParameterConfig.STRING:
								if (! (value.toString().trim().equals("") || value.toString().trim().equals("null"))) {
									paramCfg.setValue(value.toString());
								}
								else {
									paramCfg.setValue(null);
								}
								break;
							case ParameterConfig.FLOAT:
								if (! (value.toString().trim().equals("") || value.toString().trim().equals("null"))) {
									paramCfg.setValue(Float.parseFloat(value.toString()));
								}
								else {
									paramCfg.setValue(null);
								}
								break;
							case ParameterConfig.DOUBLE:
								if (! (value.toString().trim().equals("") || value.toString().trim().equals("null"))) {
									paramCfg.setValue(Double.parseDouble(value.toString()));
								}
								else {
									paramCfg.setValue(null);
								}	
								break;
							case ParameterConfig.INT:
								if (! (value.toString().trim().equals("") || value.toString().trim().equals("null"))) {
									paramCfg.setValue(Integer.parseInt(value.toString()));
								}
								else {
									paramCfg.setValue(null);
								}	
								break;
							case ParameterConfig.LONG:
								if (! (value.toString().trim().equals("") || value.toString().trim().equals("null"))) {
									paramCfg.setValue(Long.parseLong(value.toString()));
								}
								else {
									paramCfg.setValue(null);
								}	
								break;
							case ParameterConfig.BOOLEAN:
								if (! (value.toString().trim().equals("") || value.toString().trim().equals("null"))) {
									paramCfg.setValue((Integer) value > 0 ? true : false);
								}
								else {
									paramCfg.setValue(null);
								}	
								break;
							default:
								// should this case not simply throw an exception?
								if (! value.toString().trim().equals("") || value.toString().trim().equals("null")) {
									paramCfg.setValue(value);
								}
								else {
									paramCfg.setValue(null);
								}
						}
						for (ParameterChangeListener l : listeners)
							l.parameterChanged(paramCfg);
						tableViewer.refresh();
					} catch (Exception e) {						
					}
					
					
				}
				
				@Override
				protected Object getValue(Object entry) {
					ParameterConfig paramCfg = (ParameterConfig) entry;
					switch (paramCfg.getType()) {
						case ParameterConfig.BOOLEAN:
							boolean value = (Boolean) paramCfg.getValue();
							return value ? 1 : 0;
						default:
							return paramCfg.getValue() + "";
					}
				}
				
				@Override
				protected CellEditor getCellEditor(Object element) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					// case ParameterConfiguration.NULL is not editable
					switch (paramCfg.getType()) {
						case ParameterConfig.BOOLEAN:
							return new ComboBoxCellEditor(tableViewer.getTable(), new String[] {
									"false", "true" }, SWT.READ_ONLY);
						default:
							// default covers the cases:
							// STRING,INT,LONG,FLOAT,DOUBLE
							return new TextCellEditor(tableViewer.getTable());
					}
				}
				
				@Override
				protected boolean canEdit(Object element) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					return !paramCfg.isUnset();
				}
			});
		}
		
	}
	
	// builds the las column of the table ('unset')
	public void buildUnsetColumn() {
		TableViewerColumn unsetColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		{
			unsetColumn.getColumn().setText(HenshinInterpreterUIPlugin.LL("_UI_ParameterColumn_Unset"));
			unsetColumn.getColumn().setWidth(100);
			unsetColumn.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public String getText(Object element) {
					ParameterConfig paramCfg = (ParameterConfig) element;
					// only display the 'unset' property for UNKNOWN parameter kinds
					if (paramCfg.getKind() == ParameterKind.UNKNOWN) {
						return String.valueOf(paramCfg.isUnset());
					}
					
					return null;
				}
			});
			
			unsetColumn.setEditingSupport(new EditingSupport(tableViewer) {
				
				@Override
				protected void setValue(Object element, Object value) {
					ParameterConfig paramCfg = (ParameterConfig) element;

					paramCfg.setUnset((Integer) value == 1);
					
					if (paramCfg.isUnset()) {
						paramCfg.setValue(null);
					}

					for (ParameterChangeListener l : listeners)
						l.parameterChanged(paramCfg);
					tableViewer.refresh();
				}
				
				@Override
				protected Object getValue(Object entry) {
					ParameterConfig paramCfg = (ParameterConfig) entry;
					int isUnset = paramCfg.isUnset() ? 1 : 0;
					return Integer.valueOf(isUnset);
				}
				
				@Override
				protected CellEditor getCellEditor(Object element) {
					return ((ParameterConfig) element).getKind() == ParameterKind.UNKNOWN ? 
						new ComboBoxCellEditor(tableViewer.getTable(), new String[] { "false", "true" }, SWT.READ_ONLY) : null;
				}
				
				@Override
				protected boolean canEdit(Object element) {
					// only parameters of kind UNKNOWN can be 'unset'
					return ((ParameterConfig) element).getKind() == ParameterKind.UNKNOWN;
				}
			});
			
		}
	}
	
	public void addParameterChangeListener(ParameterChangeListener listener) {
		listeners.add(listener);
	}
	
	public static interface ParameterChangeListener {
		void parameterChanged(ParameterConfig paramCfg);
	}
	
	public void setParameters(Collection<ParameterConfig> paramCfgs) {
		tableViewer.setInput(paramCfgs);
		
		// check if unset column should be built or disposed
		boolean hasUnknownParameter = false;
		
		// loop through parameter list to determine whether there is a unknown parameter or not
		for (ParameterConfig param : paramCfgs) {
			if (param.getKind() == ParameterKind.UNKNOWN) {
				hasUnknownParameter = true;
				break;
			}
		}
		
		// display unset column
		if (hasUnknownParameter) {
			buildUnsetColumn();
		// hide unset column if present
		} else {
			int columnCount = tableViewer.getTable().getColumns().length;
			TableColumn lastColumn = tableViewer.getTable().getColumn(columnCount - 1);
			String lastColumnIdentifier = lastColumn.getText();
			// check if unset column is present
			if (lastColumnIdentifier.equals(HenshinInterpreterUIPlugin.LL("_UI_ParameterColumn_Unset"))) {
				lastColumn.dispose();
			}
		}
		
		// refresh table with new parameters
		tableViewer.refresh();
	}
}
