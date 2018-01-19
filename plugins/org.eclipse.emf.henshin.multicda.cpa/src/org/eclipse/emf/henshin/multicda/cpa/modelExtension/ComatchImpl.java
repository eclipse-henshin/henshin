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

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

/**
 * This class introduces an adaption of the <code>MatchImpl</code> to serve within the context of this critical pair
 * analysis. "comatch" defines the situation where the RHS (right hand side) of a rule is mapped into a model instead of
 * the usual LHS (left ahnd side).
 * 
 * @author Kristopher Born
 *
 */
public class ComatchImpl extends ExtendedMatchImpl {

	/**
	 * Constructor.
	 * 
	 * @param rule The rule that this match is used for.
	 * @param isResultMatch Determines whether this is a result match.
	 */
	public ComatchImpl(Rule rule, boolean isResultMatch) {
		super(rule, isResultMatch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.impl.AssignmentImpl#setUnit(org.eclipse.emf.henshin.model.Unit)
	 */
	@Override
	protected void setUnit(Unit unit) {
		if (!(unit instanceof Rule)) {
			throw new IllegalArgumentException("Unit must be a rule");
		}
		this.unit = unit;
		// RHS nodes! - comatch always consists of nodes of the RHS
		this.nodes = ((Rule) unit).getRhs().getNodes();
	}
}
