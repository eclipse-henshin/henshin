package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.StringTokenizer;

import javax.script.ScriptEngine;

import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class ConditionInfo {
	
	//private AttributeConditionHandler conditionHandler;
	
	private Map<String, Collection<String>> conditionParameters;
	
	public ConditionInfo(Rule rule, ScriptEngine scriptEngine) {
		this.conditionParameters = new HashMap<String, Collection<String>>();
		
		Collection<String> parameterNames = new ArrayList<String>();
		for (Parameter parameter : rule.getParameters()) {
			parameterNames.add(parameter.getName());
		}
		
		for (AttributeCondition condition : rule.getAttributeConditions()) {
			String conditionText = condition.getConditionText();
			Collection<String> usedParameters = extractParameter(conditionText, parameterNames);
			
			conditionParameters.put(conditionText, usedParameters);
		}
	}
	
	private Collection<String> extractParameter(String testString, Collection<String> parameterNames) {
		Collection<String> usedParameters = new HashSet<String>();
		
		StringTokenizer quoteParser = new StringTokenizer(testString, "\"\'");

		while (quoteParser.hasMoreElements()) {
			String nonQuotedString = quoteParser.nextToken();
			StringTokenizer variableParser = new StringTokenizer(nonQuotedString, ".,()\t\r\n<>=!+[] ");
			while (variableParser.hasMoreElements()) {
				String subString = variableParser.nextToken();
				for (String parameterName : parameterNames) {
					if (parameterName.equals(subString)) {
						usedParameters.add(parameterName);
					}
				}
			}
			// discard the quoted part
			if (quoteParser.hasMoreElements())
				quoteParser.nextElement();
		}
		
		return usedParameters;
	}
	
	/**
	 * @return the conditionParameters
	 */
	public Map<String, Collection<String>> getConditionParameters() {
		return conditionParameters;
	}
}
