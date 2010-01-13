package org.eclipse.emf.henshin.statespace.explorer.parts;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.actions.ActionFactory;

import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;

/**
 * Provides context menu actions for the state space explorer.
 * @generated NOT
 */
public class StateSpaceContextMenuProvider extends ContextMenuProvider {

	// Action registry:
	private ActionRegistry registry;

	/**
	 * Default constructor.
	 * @param viewer Edit part viewer.
	 * @param registry Action registry.
	 */
	public StateSpaceContextMenuProvider(EditPartViewer viewer, ActionRegistry registry) {
		super(viewer);
		if (registry==null) {
			throw new IllegalArgumentException();
		}
		this.registry = registry;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.ContextMenuProvider#buildContextMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void buildContextMenu(IMenuManager menu) {
		
		// Add standard action groups to the menu
		GEFActionConstants.addStandardActionGroups(menu);

		// Add actions to the menu
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, getAction(ActionFactory.UNDO.getId())); 
		menu.appendToGroup(GEFActionConstants.GROUP_UNDO, getAction(ActionFactory.REDO.getId()));
		menu.appendToGroup(GEFActionConstants.GROUP_EDIT, getAction(ActionFactory.DELETE.getId()));
		
	}
	
	private IAction getAction(String id) {
		return registry.getAction(id);
	}

}
