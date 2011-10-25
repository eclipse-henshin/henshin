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

import java.util.Collection;
import java.util.HashSet;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class AttributeCondition {
	
	String conditionText;
	
	/**
	 * Collection of parameters used in the conditionText which are not set yet.  
	 */
	private Collection<String> remainingParameters;
	
	private ScriptEngine scriptEngine;
	
	public AttributeCondition(String condition, Collection<String> conditionParameters,
			ScriptEngine engine) {
		this.conditionText = condition;
		this.remainingParameters = new HashSet<String>(conditionParameters);
		this.scriptEngine = engine;
	}
	
	/**
	 * Evaluates the conditionText if there are no more outstanding parameters.
	 * 
	 * @return
	 */
	public boolean eval() {
		if (remainingParameters.isEmpty()) {
			try {
				return (Boolean) scriptEngine.eval(conditionText);
			} catch (ScriptException ex) {
				throw new RuntimeException(ex.getMessage());
				// ex.printStackTrace();
			} catch (ClassCastException ex) {
				throw new RuntimeException(
						"Warning: Attribute condition did not return a boolean value");
			}
		}
		
		return true;
	}
	
	void addParameter(String parameterName) {
		remainingParameters.add(parameterName);
	}
	
	void removeParameter(String parameterName) {
		remainingParameters.remove(parameterName);
	}
}
