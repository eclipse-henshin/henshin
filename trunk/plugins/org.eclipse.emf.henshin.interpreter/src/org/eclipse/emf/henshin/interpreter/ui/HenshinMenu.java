package org.eclipse.emf.henshin.interpreter.ui;

import org.eclipse.emf.henshin.interpreter.util.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;


public class HenshinMenu extends ContributionItem {
	public HenshinMenu() {
	}

	public HenshinMenu(String id) {
		super(id);
	}

	@Override
	public void fill(Menu menu, int index) {
		// Here you could get selection and decide what to do
		// You can also simply return if you do not want to show a menu

		// create the menu item
		MenuItem applyItem = new MenuItem(menu, SWT.CASCADE);
		applyItem.setText("Apply");
		
		Menu subMenu = new Menu(menu);
		applyItem.setMenu(subMenu);
		
		for (TransformationSystem trafoSystem: HenshinRegistry.instance.getTransformationSystems()) {
			MenuItem trafoSystemItem = new MenuItem(subMenu, SWT.CASCADE);
			trafoSystemItem.setText(trafoSystem.getName());
			
			Menu subMenu2 = new Menu(menu);
			trafoSystemItem.setMenu(subMenu2);
		
			for (TransformationUnit unit: trafoSystem.getTransformationUnits()) {
				MenuItem trafoItem = new MenuItem(subMenu2, SWT.PUSH);
				trafoItem.setText(unit.getName());
			}
		}
	}
}
