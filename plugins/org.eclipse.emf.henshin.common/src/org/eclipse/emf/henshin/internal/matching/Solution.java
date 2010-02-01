package org.eclipse.emf.henshin.internal.matching;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;


public class Solution {
	Map<Variable, EObject> objectMatches;
	Map<String, Object> parameterMatches;
	
	public Solution(List<Variable> variables, Map<Variable, DomainSlot> domainMap, AttributeConditionHandler handler) {
		Map<Variable, EObject> match = new HashMap<Variable,EObject>();
		
		for (Variable variable: variables) {
			DomainSlot slot = domainMap.get(variable);
			match.put(variable, slot.value);
		}
		
		this.objectMatches = match;		
		this.parameterMatches = new HashMap<String, Object>(handler.getAssignedParameters());
	}

	/**
	 * @return the objectMatches
	 */
	public Map<Variable, EObject> getObjectMatches() {
		return objectMatches;
	}

	/**
	 * @return the parameterMatches
	 */
	public Map<String, Object> getParameterMatches() {
		return parameterMatches;
	}
}