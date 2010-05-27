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

import java.util.Collection;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class AttributeCondition {
	String conditionText;
	Collection<String> usedParameters;

	ScriptEngine engine;

	int assignedParameters;

	public AttributeCondition(ScriptEngine engine, String condition,
			Collection<String> usedParameters) {
		this.conditionText = condition;
		this.usedParameters = usedParameters;
		this.assignedParameters = 0;
		this.engine = engine;
	}

	public boolean eval(Map<String, Object> assignment) {
		if (assignedParameters == usedParameters.size()) {

			for (String parameter : usedParameters) {
				engine.put(parameter, assignment.get(parameter));
			}
			try {
				return (Boolean) engine.eval(conditionText);
			} catch (ScriptException ex) {
				ex.printStackTrace();
			} catch (ClassCastException ex) {
				System.err
						.println("Warning: Attribute condition did not return a boolean value");
			}
		}

		return true;
	}

	public void increaseAssignCounter() {
		assignedParameters++;
	}

	public void decreaseAssignCounter() {
		assignedParameters--;
	}

	public void resetAssignCounter() {
		assignedParameters = 0;
	}
}
