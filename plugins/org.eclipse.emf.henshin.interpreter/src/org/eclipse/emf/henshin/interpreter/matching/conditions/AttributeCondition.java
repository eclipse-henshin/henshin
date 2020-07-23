/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.matching.conditions;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.script.ScriptException;

import org.eclipse.emf.henshin.model.util.ScriptEngineWrapper;

/**
 * Attribute condition.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class AttributeCondition {
	
	// Condition text:
	public final String conditionText;
	
	// Collection of parameters used in the conditionText which are not set yet.  
	public final Set<String> parameters;
	
	// Script engine:
	final ScriptEngineWrapper scriptEngine;

	final private List<String> localJavaImports;
	
	/**
	 * Default constructor.
	 * @param condition String representation of the condition.
	 * @param conditionParameters Condition parameters.
	 * @param engine Script engine.
	 */
	public AttributeCondition(String condition, Collection<String> conditionParameters,
			ScriptEngineWrapper engine, List<String> localJavaImports) {
		this.conditionText = condition;
		this.parameters = new HashSet<String>(conditionParameters);
		this.scriptEngine = engine;
		this.localJavaImports = localJavaImports;
	}
	
	/**
	 * Evaluates the conditionText if there are no more outstanding parameters.
	 * @return the result of the evaluation.
	 */
	public boolean eval() {
		if (parameters.isEmpty()) {
			try {
				return (Boolean) scriptEngine.eval(conditionText, localJavaImports);
			} catch (ScriptException ex) {
				throw new RuntimeException(ex.getMessage());
			} catch (ClassCastException ex) {
				throw new RuntimeException("Warning: Attribute condition did not return a boolean value");
			}
		}
		return true;
	}
	
}
