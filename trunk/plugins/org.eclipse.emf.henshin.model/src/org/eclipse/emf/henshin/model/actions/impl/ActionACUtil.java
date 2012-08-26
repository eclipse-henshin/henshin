/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model.actions.impl;

import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;

import static org.eclipse.emf.henshin.model.Action.Type.*;

/**
 * Utility methods to access and modify application conditions
 * (PACs and NACs) based on actions.
 * 
 * @author Christian Krause
 */
public class ActionACUtil {
	
	/**
	 * Find or create a positive or a negative application condition.
	 * @param action	FORBID/REQUIRE action
	 * @param rule		Rule
	 * @return the application condition.
	 */
	public static NestedCondition getOrCreateAC(Action action, Rule rule) {
		
		// Check if the action type is ok:
		if (!((action != null) && 
			((action.getType() == FORBID) || 
			 (action.getType() == REQUIRE)))) {
			throw new IllegalArgumentException("Application conditions can be created only for REQUIRE/FORBID actions");
		}
		
		// Determine whether it is a PAC or a NAC:
		boolean positive = (action.getType()==REQUIRE);
		
		// Get the name of the application condition:
		String name = null;
		String[] args = action.getArguments();
		if (args != null && args.length > 0 && args[0] != null) {
			name = args[0];
		}
		
		// Find or create the application condition:
		NestedCondition ac = null;
		for (NestedCondition cond : rule.getAllNestedConditions()) {
			if (cond.getConclusion()==null || cond.getRule()!=rule) continue;
			if ((name==null && cond.getConclusion().getName()==null) ||
				(name!=null && name.equals(cond.getConclusion().getName()))) {
				if ((positive && cond.isPAC()) || (!positive && cond.isNAC())) {
					ac = cond;
					break;
				}
			}
		}
		if (ac==null) {
			ac = positive ? rule.createPAC(name) : rule.createNAC(name);
		}
		
		// Done.
		return ac;
		
	}

}
