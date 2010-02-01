package org.eclipse.emf.henshin.internal.conditions.attribute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Variable;

public class AttributeConditionHandler {
	List<String> parameterNames;
	Set<String> constants; //TODO(enrico): find better way to handle constants
								
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
		this.constants = new HashSet<String>();
		this.engine = scriptEngine;

		for (String conditionString : conditionStrings) {
			addAttributeCondition(conditionString);
		}
	}

	public AttributeConditionHandler(Rule rule, ScriptEngine scriptEngine) {
		this.parameterNames = new ArrayList<String>();
		this.attributeConditions = new ArrayList<AttributeCondition>();
		this.assignedParameters = new HashMap<String, Object>();
		this.involvedConditions = new HashMap<String, List<AttributeCondition>>();
		this.constants = new HashSet<String>();

		engine = scriptEngine;

		for (Variable variable : rule.getVariables()) {
			if (!parameterNames.contains(variable.getName())) {
				parameterNames.add(variable.getName());
			}
		}

		for (org.eclipse.emf.henshin.model.AttributeCondition condition : rule
				.getAttributeConditions()) {
			addAttributeCondition(condition.getConditionText());
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
		if (assignedParameters.containsKey(parameterName)
				&& !constants.contains(parameterName)) {
			assignedParameters.remove(parameterName);
			decreaseAssignCounter(parameterName);
		}
	}

	public void clear() {
		assignedParameters.clear();
		constants.clear();
		for (AttributeCondition condition : attributeConditions) {
			condition.resetAssignCounter();
		}
	}

	public void fixParameter(String parameterName) {
		constants.add(parameterName);
	}
}
