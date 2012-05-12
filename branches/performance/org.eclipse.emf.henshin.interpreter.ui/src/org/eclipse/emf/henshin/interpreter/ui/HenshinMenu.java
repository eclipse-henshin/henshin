package org.eclipse.emf.henshin.interpreter.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.HenshinRegistry;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.services.IServiceLocator;

public class HenshinMenu extends CompoundContributionItem {
	public HenshinMenu() {
	}
	
	public HenshinMenu(String id) {
		super(id);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected IContributionItem[] getContributionItems() {
		IServiceLocator serviceLocator = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		List<IContributionItem> items = new ArrayList<IContributionItem>();
		
		for (TransformationSystem trafoSystem : HenshinRegistry.INSTANCE.getTransformationSystems()) {
			for (TransformationUnit unit : trafoSystem.getTransformationUnits()) {
				Map commandParams = new HashMap<String, Object>();
				commandParams.put("org.eclipse.emf.henshin.UnitParameter",
						unit.getName());
				commandParams.put("org.eclipse.emf.henshin.TrafoSystemParameter",
						trafoSystem.getName());
				
				CommandContributionItemParameter param = new CommandContributionItemParameter(
						serviceLocator, trafoSystem.getName() + ":" + unit.getName(),
						"org.eclipse.emf.henshin.interpreter.ui.ApplyTrafoUnit", commandParams,
						null, null, null, trafoSystem.getName() + ":" + unit.getName(), null, null,
						CommandContributionItem.STYLE_PUSH, null, false);
				
				items.add(new CommandContributionItem(param));
			}
		}
		
		return items.toArray(new IContributionItem[items.size()]);
	}
}
