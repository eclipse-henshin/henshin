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
package org.eclipse.emf.henshin.editor.transformationCommands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.commands.HenshinModelUtils;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.editor.commands.MenuContributor;
import org.eclipse.emf.henshin.editor.commands.QuantUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.action.IMenuManager;

/**
 * 
 * @author Gregor Bonifer
 * @author Stefan Jurack (sjurack)
 */
public class TC_AddMappedNode extends TCMenuContributor {
	
	public static MenuContributor INSTANCE = new TC_AddMappedNode();
	
	protected Rule rule;
	
	@Override
	protected String getLocalHenshinFile() {
		return "transformations/AddMappedNode/AddMappedNode.henshin";
	}
	
	@Override
	protected String getMainUnitName() {
		return "main";
	}
	
	@Override
	protected boolean selectionYieldsApplicationContext(List<?> selection) {
		
		if (selection.size() != 1) return false;
		
		if (!QuantUtil.instanceOfAny(selection.get(0), Rule.class)) return false;
		
		rule = (Rule) selection.get(0);
		
		return true;
	}
	
	@Override
	void contributeActions(IMenuManager menuManager) {
		EmfGraph ctx = new EmfGraph();
		ctx.addRoot(rule);
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("newOriginNodeName", HenshinModelUtils.generateNewNodeName(rule.getLhs()));
		parameters.put("newImageNodeName", HenshinModelUtils.generateNewNodeName(rule.getRhs()));
		menuManager.add(createDefaultUnitApplicationAction("*Experimental* Add Mapped Node", ctx,
				parameters));
	}
	
}
