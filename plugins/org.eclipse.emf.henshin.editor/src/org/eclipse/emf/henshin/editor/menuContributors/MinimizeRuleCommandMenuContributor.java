package org.eclipse.emf.henshin.editor.menuContributors;

import java.util.List;

import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.editor.commands.MinimizeRuleCommand;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IMenuManager;

/**
 * 
 * @author Timo Kehrer
 */
public class MinimizeRuleCommandMenuContributor extends MenuContributor {

	public static MenuContributor INSTANCE = new MinimizeRuleCommandMenuContributor();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.editor.commands.MenuContributor#contributeActions(org.eclipse.jface.action.IMenuManager,
	 * java.util.List)
	 */
	@Override
	protected void contributeActions(IMenuManager menuManager, List<?> selection) {

		// Selection is one Rule
		if ((selection.size() == 1) && (selection.get(0) instanceof Rule)) {
			MinimizeRuleCommand cmd = new MinimizeRuleCommand();
			cmd.setElements(selection);
			menuManager.add(createAction(getLabel("Minimize"), cmd));
		}
	}
}
