/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.matching;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;

public class Solution<TType, TNode> {
	Map<Variable<TType, TNode>, TNode> objectMatches;
	Map<String, Object> parameterValues;

	public Solution(List<Variable<TType, TNode>> variables,
			Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap,
			AttributeConditionHandler conditionHandler) {
		Map<Variable<TType, TNode>, TNode> match = new HashMap<Variable<TType, TNode>, TNode>();

		for (Variable<TType, TNode> variable : variables) {
			DomainSlot<TType, TNode> slot = domainMap.get(variable);
			match.put(variable, slot.value);
		}

		this.objectMatches = match;
		this.parameterValues = new HashMap<String, Object>(conditionHandler
				.getParameterValues());
	}


	/**
	 * @return the objectMatches
	 */
	public Map<Variable<TType, TNode>, TNode> getObjectMatches() {
		return objectMatches;
	}

	/**
	 * @return the parameterValues
	 */
	public Map<String, Object> getParameterValues() {
		return parameterValues;
	}
}