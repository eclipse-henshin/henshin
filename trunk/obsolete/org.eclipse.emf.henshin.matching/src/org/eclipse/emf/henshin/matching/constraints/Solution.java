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
package org.eclipse.emf.henshin.matching.constraints;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.conditions.attribute.AttributeConditionHandler;

public class Solution {
	Map<Variable, EObject> objectMatches;
	Map<String, Object> parameterValues;

	public Solution(List<Variable> variables,
			Map<Variable, DomainSlot> domainMap,
			AttributeConditionHandler conditionHandler) {
		Map<Variable, EObject> match = new HashMap<Variable, EObject>();

		for (Variable variable : variables) {
			DomainSlot slot = domainMap.get(variable);
			match.put(variable, slot.value);
		}

		this.objectMatches = match;
		this.parameterValues = new HashMap<String, Object>(conditionHandler
				.getParameterValues());
	}

	/**
	 * @return the objectMatches
	 */
	public Map<Variable, EObject> getObjectMatches() {
		return objectMatches;
	}

	/**
	 * @return the parameterValues
	 */
	public Map<String, Object> getParameterValues() {
		return parameterValues;
	}
}