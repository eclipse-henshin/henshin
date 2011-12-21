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

import org.eclipse.emf.henshin.interpreter.ui.InterpreterUIPlugin;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * 
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack
 */
public class UnitSelector {
	
	protected int CONTROL_OFFSET = 5;
	
	protected Collection<UnitSelectionListener> listeners = new ArrayList<UnitSelector.UnitSelectionListener>();
	
	protected Group container;
	
	protected Combo unitSelector;
	
	protected ArrayList<TransformationUnit> units;
	
	public UnitSelector(Composite parent) {
		container = new Group(parent, SWT.NONE);
		container.setText(InterpreterUIPlugin.LL("_UI_SelectTransformationUnit"));
		container.setLayout(new FormLayout());
		unitSelector = new Combo(container, SWT.NONE);
		{
			FormData data = new FormData();
			data.top = new FormAttachment(0, CONTROL_OFFSET);
			data.left = new FormAttachment(0, CONTROL_OFFSET);
			data.right = new FormAttachment(100, -CONTROL_OFFSET);
			data.bottom = new FormAttachment(100, -CONTROL_OFFSET);
			unitSelector.setLayoutData(data);
		}
		
		unitSelector.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				for (UnitSelectionListener l : listeners)
					l.unitSelected(unitSelector.getSelectionIndex());
			}
		});
		
	}
	
	public Control getControl() {
		return container;
	}
	
	public void setSelection(int idx) {
		unitSelector.select(idx);
	}
	
	public void setSelectableUnits(String[] units) {
		unitSelector.setItems(units);
	}
	
	public void addUnitSelectionListener(UnitSelectionListener listener) {
		listeners.add(listener);
	}
	
	public static interface UnitSelectionListener {
		boolean unitSelected(int idx);
	}
	
	/**
	 * @param enabled
	 */
	public void setEnabled(boolean enabled) {
		this.unitSelector.setEnabled(enabled);
	}
	
}