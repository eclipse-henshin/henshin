/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.matching.conditions.AttributeConditionHandler;

/**
 * Solution class. A solution is an internal representation of a {@link Match}.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class Solution {
	
	// Map associating variables to matched objects:
	public final Map<Variable, EObject> objectMatches;
	
	// Map associating parameter names to values:
	public final Map<String, Object> parameterValues;

	/**
	 * Default constructor.
	 * @param variables List of variables.
	 * @param domainMap Domain map.
	 * @param conditionHandler Attribute condition handler.
	 */
	public Solution(List<Variable> variables,
			Map<Variable, DomainSlot> domainMap,
			AttributeConditionHandler conditionHandler) {
		
		// Collect the object matches:
		objectMatches = new HashMap<Variable, EObject>();
		for (Variable variable : variables) {
			DomainSlot slot = domainMap.get(variable);
			objectMatches.put(variable, slot.value);
		}
		
		// Collect the parameter values:
		parameterValues = new HashMap<String, Object>(
				conditionHandler.getParameterValues());
		
	}

}