/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.modelExtension;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class ExtendedMatchImpl extends MatchImpl {

	/**
	 * Constructor.
	 * 
	 * @param rule The rule that this match is used for.
	 * @param isResultMatch Determines whether this is a result match.
	 */
	public ExtendedMatchImpl(Rule rule, boolean isResultMatch) {
		super(rule, isResultMatch);
	}

	/**
	 * Removes the <code>parameter</code> from the assigned values.
	 * 
	 * @param parameter The <code>Parameter</code> to be removed.
	 */
	public void removeParameter(Parameter parameter) {
		values.remove(parameter);
	}

	/**
	 * Hook for accessing the assigned values. For testing purpose.
	 * 
	 * @return The <code>HashMap</code> with all the assigned values.
	 */
	public Map<Object, Object> getValues() {
		return this.values;
	}

	/**
	 * Removes the <code>parameters</code> from the assigned values.
	 * 
	 * @param parameters The <code>Parameter</code>s to be removed.
	 */
	public void removeAllParameter(EList<Parameter> parameters) {
		for (Parameter parameter : parameters) {
			this.removeParameter(parameter);
		}
	}

}
