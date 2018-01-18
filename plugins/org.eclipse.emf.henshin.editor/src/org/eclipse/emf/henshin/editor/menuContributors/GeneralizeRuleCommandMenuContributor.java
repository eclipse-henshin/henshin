package org.eclipse.emf.henshin.editor.menuContributors;

import java.util.List;

import org.eclipse.emf.henshin.editor.commands.GeneralizeRuleCommand;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IMenuManager;

/**
 * 
 * @author Timo Kehrer
 */
public class GeneralizeRuleCommandMenuContributor extends MenuContributor {

	public static MenuContributor INSTANCE = new GeneralizeRuleCommandMenuContributor();

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
			GeneralizeRuleCommand cmd = new GeneralizeRuleCommand();
			cmd.setElements(selection);
			menuManager.add(createAction(getLabel("Generalize"), cmd));
		}
	}
}
