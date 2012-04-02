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
package org.eclipse.emf.henshin.matching.conditions.attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.script.ScriptEngine;

public class AttributeConditionHandler {
	Collection<AttributeCondition> attributeConditions;
	Map<String, Collection<AttributeCondition>> involvedConditions;
	Collection<String> assignedParameters;

	ScriptEngine scriptEngine;

	public AttributeConditionHandler(
			Map<String, Collection<String>> conditionParameters,
			ScriptEngine scriptEngine) {
		this.attributeConditions = new ArrayList<AttributeCondition>();
		this.involvedConditions = new HashMap<String, Collection<AttributeCondition>>();
		this.assignedParameters = new HashSet<String>();
		this.scriptEngine = scriptEngine;

		for (String conditionString : conditionParameters.keySet()) {
			Collection<String> usedParameters = conditionParameters
					.get(conditionString);

			AttributeCondition attributeCondition = new AttributeCondition(
					conditionString, usedParameters, scriptEngine);
			attributeConditions.add(attributeCondition);

			// create a map for easy lookup of conditions a parameter is
			// involved in
			for (String usedParameter : usedParameters) {
				Collection<AttributeCondition> conditionList = involvedConditions
						.get(usedParameter);
				if (conditionList == null) {
					conditionList = new ArrayList<AttributeCondition>();
					involvedConditions.put(usedParameter, conditionList);
				}

				conditionList.add(attributeCondition);
			}
		}
	}

	public boolean setParameter(String parameterName, Object value) {
		boolean result = true;

		if (!assignedParameters.contains(parameterName)) {
			assignedParameters.add(parameterName);
			scriptEngine.put(parameterName, value);

			Collection<AttributeCondition> conditionList = involvedConditions
					.get(parameterName);

			if (conditionList != null) {
				for (AttributeCondition condition : conditionList) {
					condition.removeParameter(parameterName);
					result = result && condition.eval();
				}
			}
		}

		return result;
	}

	public void unsetParameter(String parameterName) {
		if (assignedParameters.contains(parameterName)) {
			assignedParameters.remove(parameterName);

			Collection<AttributeCondition> conditionList = involvedConditions
					.get(parameterName);

			if (conditionList != null) {
				for (AttributeCondition condition : involvedConditions
						.get(parameterName)) {
					condition.addParameter(parameterName);
				}
			}
		}
	}

	public boolean isSet(String parameterName) {
		return assignedParameters.contains(parameterName);
	}

	public Object getParameter(String parameterName) {
		return scriptEngine.get(parameterName);
	}

	public Map<String, Object> getParameterValues() {
		Map<String, Object> parameterValues = new HashMap<String, Object>();
		for (String parameterName : assignedParameters) {
			parameterValues.put(parameterName, scriptEngine.get(parameterName));
		}
		return parameterValues;
	}
}
