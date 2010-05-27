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
package org.eclipse.emf.henshin.internal.conditions.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;

public class AttributeConditionHandler {
	List<String> parameterNames;
								
	List<AttributeCondition> attributeConditions;

	HashMap<String, Object> assignedParameters;
	HashMap<String, List<AttributeCondition>> involvedConditions;

	ScriptEngine engine;

	public AttributeConditionHandler(ArrayList<String> parameterNames,
			ArrayList<String> conditionStrings, ScriptEngine scriptEngine) {
		this.parameterNames = parameterNames;
		this.attributeConditions = new ArrayList<AttributeCondition>();
		this.assignedParameters = new HashMap<String, Object>();
		this.involvedConditions = new HashMap<String, List<AttributeCondition>>();
		this.engine = scriptEngine;

		for (String conditionString : conditionStrings) {
			addAttributeCondition(conditionString);
		}
	}

	public AttributeConditionHandler(ScriptEngine scriptEngine, List<String> parameterNames, List<String> conditionStrings) {
		this.parameterNames = parameterNames;
		this.attributeConditions = new ArrayList<AttributeCondition>();
		this.assignedParameters = new HashMap<String, Object>();
		this.involvedConditions = new HashMap<String, List<AttributeCondition>>();

		engine = scriptEngine;

		for (String conditionString: conditionStrings) {
			addAttributeCondition(conditionString);
		}
	}

	private void addAttributeCondition(String conditionText) {
		List<String> usedParameters = extractParameter(conditionText);
		AttributeCondition attributeCondition = new AttributeCondition(engine,
				conditionText, usedParameters);

		for (String parameter : usedParameters) {
			List<AttributeCondition> conditionList = involvedConditions
					.get(parameter);
			if (conditionList == null) {
				conditionList = new ArrayList<AttributeCondition>();
				involvedConditions.put(parameter, conditionList);
			}

			conditionList.add(attributeCondition);
		}

		attributeConditions.add(attributeCondition);
	}

	private List<String> extractParameter(String testString) {
		ArrayList<String> usedParameters = new ArrayList<String>();

		StringTokenizer quoteParser = new StringTokenizer(testString, "\"\'");

		while (quoteParser.hasMoreElements()) {
			String nonQuotedString = quoteParser.nextToken();
			StringTokenizer variableParser = new StringTokenizer(
					nonQuotedString, ".()\t\r\n<>=! ");

			while (variableParser.hasMoreElements()) {
				String subString = variableParser.nextToken();
				for (String parameterName : parameterNames) {
					if (parameterName.equals(subString)) {
						usedParameters.add(parameterName);
					}
				}
			}

			// discount the quoted part
			if (quoteParser.hasMoreElements())
				quoteParser.nextElement();
		}

		return usedParameters;
	}

	private void increaseAssignCounter(String parameterName) {
		List<AttributeCondition> conditions = involvedConditions
				.get(parameterName);
		if (conditions != null) {
			for (AttributeCondition condition : conditions) {
				condition.increaseAssignCounter();
			}
		}
	}

	private void decreaseAssignCounter(String parameterName) {
		List<AttributeCondition> conditions = involvedConditions
				.get(parameterName);
		if (conditions != null) {
			for (AttributeCondition condition : conditions) {
				condition.decreaseAssignCounter();
			}
		}
	}

	private boolean validateConditions(String parameterName) {
		boolean result = true;

		for (AttributeCondition condition : attributeConditions) {
			result = result && condition.eval(assignedParameters);
		}

		return result;
	}

	public boolean isSet(String parameterName) {
		return assignedParameters.keySet().contains(parameterName);
	}

	public boolean setParameter(String parameterName, Object value) {
		if (!assignedParameters.keySet().contains(parameterName)) {
			assignedParameters.put(parameterName, value);

			increaseAssignCounter(parameterName);
			return validateConditions(parameterName);
		}
		return true;
	}

	public Object getParameter(String parameterName) {
		return assignedParameters.get(parameterName);
	}

	/**
	 * @return the assignedParameters
	 */
	public HashMap<String, Object> getAssignedParameters() {
		return assignedParameters;
	}

	public void unsetParameter(String parameterName) {
		if (assignedParameters.containsKey(parameterName)) {
			assignedParameters.remove(parameterName);
			decreaseAssignCounter(parameterName);
		}
	}
}
