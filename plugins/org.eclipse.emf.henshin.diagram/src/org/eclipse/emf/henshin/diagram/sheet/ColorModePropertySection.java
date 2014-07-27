/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.sheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.diagram.edit.helpers.ColorModeHelper;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.provider.util.HenshinColorMode;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractNotationPropertiesSection;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;

/**
 * Custom property section in the appearance tab for changing the color mode.
 * @author Christian Krause
 */
public class ColorModePropertySection extends AbstractNotationPropertiesSection {

	private Group colorModeGroup;

	private List modeList;

	private ArrayList<HenshinColorMode> modes;

	private View view;

	/*
	 * @see org.eclipse.gmf.runtime.diagram.ui.properties.sections.AbstractNotationPropertiesSection#initializeControls(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void initializeControls(Composite parent) {
		super.initializeControls(parent);
		FormLayout layout = (FormLayout) composite.getLayout();
		layout.marginWidth = 12;
		createColorModeGroup();
	}

	protected void createColorModeGroup() {
		colorModeGroup = getWidgetFactory().createGroup(composite, "Color Mode");
		GridLayout layout = new GridLayout(1, false);
		colorModeGroup.setLayout(layout);
		

		modeList = getWidgetFactory().createList(colorModeGroup, SWT.SINGLE);

		// Sort the color modes by name:
		modes = new ArrayList<HenshinColorMode>(HenshinColorMode.REGISTRY.values());
		Collections.sort(modes, new Comparator<HenshinColorMode>() {
			public int compare(HenshinColorMode m1, HenshinColorMode m2) {
				return String.valueOf(m1.getName()).compareToIgnoreCase(String.valueOf(m2.getName()));
			}
		});
		// Default mode first:
		HenshinColorMode def = HenshinColorMode.getDefaultColorMode();
		if (def!=null) {
			modes.remove(def);
			modes.add(0, def);
		}

		// Init mode list:
		for (int i=0; i<modes.size(); i++) {
			modeList.add(String.valueOf(modes.get(i).getName()));
		}
		modeList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionChanged();
			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				selectionChanged();
			}
		});
	}

	private void selectionChanged() {

		// Get the selected color mode:
		int index = modeList.getSelectionIndex();
		final HenshinColorMode mode = (index>=0) ? modes.get(index) : HenshinColorMode.getDefaultColorMode();

		// Set the color mode:
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(view);
		if (editingDomain != null) {
			AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Set Color Mode", null) {
				protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
					ColorModeHelper.setColorMode(view, mode);
					return CommandResult.newOKCommandResult();
				}
			};
			try {
				command.execute(null, null);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		// Refresh all edit parts:
		refreshEditPart(getSingleInput());

	}

	private void refreshEditPart(IGraphicalEditPart ep) {
		ep.refresh();
		for (Object o : ep.getChildren()) {
			if (o instanceof IGraphicalEditPart) {
				refreshEditPart((IGraphicalEditPart) o);
			}
		}
		if (ep instanceof DiagramEditPart) {
			for (Object o : ((DiagramEditPart) ep).getConnections()) {
				if (o instanceof IGraphicalEditPart) {
					refreshEditPart((IGraphicalEditPart) o);
				}
			}
		}
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.AbstractPropertySection#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		view = null;
		if (!isDisposed()) {
			IGraphicalEditPart ep = getSingleInput();
			if (ep==null) {
				return;
			}
			view = ep.getNotationView();
			if (view==null) {
				return;
			}
			if (view.getElement() instanceof Module) {
				HenshinColorMode mode = ColorModeHelper.getColorMode(view);
				if (mode==null) {
					mode = HenshinColorMode.getDefaultColorMode();
				}
				modeList.select(modes.indexOf(mode));
			}
		}
	}

}
