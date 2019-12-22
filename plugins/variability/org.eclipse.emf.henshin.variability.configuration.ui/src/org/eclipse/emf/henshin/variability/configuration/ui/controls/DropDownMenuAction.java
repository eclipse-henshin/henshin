package org.eclipse.emf.henshin.variability.configuration.ui.controls;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * This class provides a drop down menu where every entry can be configured to execute a defined {@link Action} when selected.
 * 
 * @author Stefan Schulz
 *
 */
public class DropDownMenuAction extends Action implements IAction, IMenuCreator {

	private Menu menu;

	public DropDownMenuAction(String text, Control parent) {
		super(text, IAction.AS_DROP_DOWN_MENU);
		menu = new Menu(parent);
		setMenuCreator(this);
	}

	@Override
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}

	}

	@Override
	public Menu getMenu(Control parent) {
		if (menu == null) {
			menu = new Menu(parent);
		}

		return menu;
	}

	@Override
	public Menu getMenu(Menu parent) {
		return null;
	}

	public void addActionToMenu(Action action) {
		ActionContributionItem item = new ActionContributionItem(action);
		item.fill(menu, -1);
	}
	
	public void clearMenu() {
		menu = new Menu(menu.getParent());
	}

	public void uncheckAll() {
		for(MenuItem item : menu.getItems()) {
			Object o = item.getData();
			if(o instanceof ActionContributionItem) {
				((ActionContributionItem)o).getAction().setChecked(false);
			}
		}
	}
}
