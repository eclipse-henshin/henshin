/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.editor.util;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.DefaultToolTip;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Provides ToolTip support to TreeViewers whose LabelProvider implements
 * {@link IToolTipProvider}.
 * 
 * @author Gregor Bonifer
 * 
 */
public class TreeViewerToolTipSupport extends DefaultToolTip {
	
	private TreeViewer viewer;
	
	private IToolTipProvider tipProvider;
	
	private static final int DEFAULT_SHIFT_X = 10;
	
	private static final int DEFAULT_SHIFT_Y = 0;
	
	private TreeViewerToolTipSupport(TreeViewer viewer, int style, boolean manualActivation) {
		super(viewer.getControl(), style, manualActivation);
		this.viewer = viewer;
		tipProvider = (IToolTipProvider) viewer.getLabelProvider();
		setShift(new Point(DEFAULT_SHIFT_X, DEFAULT_SHIFT_Y));
		setStyle(SWT.SHADOW_OUT);
		setPopupDelay(0);
		setHideDelay(0);
		viewer.getControl().setToolTipText(""); //$NON-NLS-1$
	}
	
	/**
	 * This method must be called after the viewer's {@link LabelProvider} is
	 * set.
	 * 
	 * @param viewer
	 */
	public static void enableFor(TreeViewer viewer) {
		if (!(viewer.getLabelProvider() instanceof IToolTipProvider))
			return;
		new TreeViewerToolTipSupport(viewer, ToolTip.NO_RECREATE, false);
	}
	
	protected TreeItem getToolTipArea(Event event) {
		return viewer.getTree().getItem(new Point(event.x, event.y));
	}
	
	protected Composite createToolTipContentArea(Event event, Composite parent) {
		return super.createToolTipContentArea(event, parent);
	}
	
	protected boolean shouldCreateToolTip(Event event) {
		if (!super.shouldCreateToolTip(event))
			return false;
		
		Object element = getToolTipArea(event).getData();
		
		Object tipText = tipProvider.getToolTip(element);
		if (tipText == null)
			return false;
		
		setText(tipText.toString());
		
		return true;
	}
	
	protected void afterHideToolTip(Event event) {
		super.afterHideToolTip(event);
		if (event != null && event.widget != viewer.getControl()) {
			viewer.getControl().setFocus();
		}
	}
	
}
