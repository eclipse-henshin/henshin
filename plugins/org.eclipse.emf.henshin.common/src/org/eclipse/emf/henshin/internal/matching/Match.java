package org.eclipse.emf.henshin.internal.matching;

import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;


public class Match {
	HashMap<Variable, EObject> objectMatches;
	HashMap<String, Object> parameterMatches;
	
	public Match(List<Variable> variables, AttributeConditionHandler handler) {
		HashMap<Variable, EObject> match = new HashMap<Variable,EObject>();
		
		for (Variable variable: variables) {
			match.put(variable, variable.getInstanceValue());
		}
		
		this.objectMatches = match;		
		this.parameterMatches = new HashMap<String, Object>(handler.getAssignedParameters());
	}

	/**
	 * @return the objectMatches
	 */
	public HashMap<Variable, EObject> getObjectMatches() {
		return objectMatches;
	}

	/**
	 * @return the parameterMatches
	 */
	public HashMap<String, Object> getParameterMatches() {
		return parameterMatches;
	}
}