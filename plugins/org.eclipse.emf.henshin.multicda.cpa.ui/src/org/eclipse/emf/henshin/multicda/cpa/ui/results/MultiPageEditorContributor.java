/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.ui.results;

import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IActionBars2;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.part.EditorActionBarContributor;

/**
 * Manages the installation/deinstallation of global actions for multi editors. Responsible for the redirection of
 * global actions to the active editor. Multi-page contributor replaces the contributors for the individual editors in
 * the multi editor.
 * 
 */
public class MultiPageEditorContributor extends EditorActionBarContributor {

	/**
	 * The action bars; <code>null until init is called.
	 */
	private IActionBars bars;

	/**
	 * The workbench page; <code>null until init is called.
	 */
	private IWorkbenchPage page;

	/**
	 * Creates an empty editor action bar contributor. The action bars are furnished later via the <code>init method.
	 */
	public MultiPageEditorContributor() {
	}

	/**
	 * Contributes to the given menu.
	 * <p>
	 * The <code>EditorActionBarContributor implementation of this method
	 * does nothing. Subclasses may reimplement to add to the menu portion of this
	 * contribution.
	 * </p>
	 *
	 * @param menuManager the manager that controls the menu
	 */
	public void contributeToMenu(IMenuManager menuManager) {
	}

	/**
	 * Contributes to the given status line.
	 * <p>
	 * The <code>EditorActionBarContributor implementation of this method
	 * does nothing. Subclasses may reimplement to add to the status line portion of
	 * this contribution.
	 * </p>
	 *
	 * @param statusLineManager the manager of the status line
	 */
	public void contributeToStatusLine(IStatusLineManager statusLineManager) {
	}

	/**
	 * Contributes to the given tool bar.
	 * <p>
	 * The <code>EditorActionBarContributor implementation of this method
	 * does nothing. Subclasses may reimplement to add to the tool bar portion of
	 * this contribution.
	 * </p>
	 *
	 * @param toolBarManager the manager that controls the workbench tool bar
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
	}

	/**
	 * Contributes to the given cool bar.
	 * <p>
	 * The <code>EditorActionBarContributor implementation of this method
	 * does nothing. Subclasses may reimplement to add to the cool bar portion of
	 * this contribution. There can only be contributions from a cool bar or a tool bar.
	 * </p>
	 *
	 * @param coolBarManager the manager that controls the workbench cool bar.
	 * 
	 * @since 3.0
	 */
	public void contributeToCoolBar(ICoolBarManager coolBarManager) {
	}

	/**
	 * Returns this contributor's action bars.
	 *
	 * @return the action bars
	 */
	public IActionBars getActionBars() {
		return bars;
	}

	/**
	 * Returns this contributor's workbench page.
	 *
	 * @return the workbench page
	 */
	public IWorkbenchPage getPage() {
		return page;
	}

	/**
	 * The <code>EditorActionBarContributor implementation of this 
	 * <code>IEditorActionBarContributor method does nothing,
	 * subclasses may override.
	 */
	public void dispose() {
	}

	/**
	 * The <code>EditorActionBarContributor implementation of this 
	 * <code>IEditorActionBarContributor method remembers the page
	 * then forwards the call to <code>init(IActionBars) for
	 * backward compatibility
	 */
	public void init(IActionBars bars, IWorkbenchPage page) {
		this.page = page;
		init(bars);
	}

	/**
	 * This method calls:
	 * <ul>
	 * <li>contributeToMenu with bars' menu manager
	 * <li>contributeToToolBar with bars' tool bar manager</li>
	 * <li>contributeToCoolBar with bars' cool bar manager if <code>IActionBars is of extended type IActionBars2 
	 *  <li>contributeToStatusLine with bars' status line
	 *    manager</li>
	 * </ul>
	 * The given action bars are also remembered and made accessible via <code>getActionBars.
	 * 
	 * @param bars the action bars
	 */
	public void init(IActionBars bars) {
		this.bars = bars;
		contributeToMenu(bars.getMenuManager());
		contributeToToolBar(bars.getToolBarManager());
		if (bars instanceof IActionBars2) {
			contributeToCoolBar(((IActionBars2) bars).getCoolBarManager());
		}
		contributeToStatusLine(bars.getStatusLineManager());

	}

	/**
	 * Sets the active editor for the contributor.
	 * <p>
	 * The <code>EditorActionBarContributor implementation of this method does
	 * nothing. Subclasses may reimplement. This generally entails disconnecting
	 * from the old editor, connecting to the new editor, and updating the actions
	 * to reflect the new editor.
	 * </p>
	 * 
	 * @param targetEditor the new target editor
	 */
	public void setActiveEditor(IEditorPart targetEditor) {
	}

}
