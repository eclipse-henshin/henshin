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
package org.eclipse.emf.henshin.internal.conditions.nested;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.common.util.GraphSkeleton;
import org.eclipse.emf.henshin.internal.matching.DomainSlot;
import org.eclipse.emf.henshin.internal.matching.Variable;

public class ApplicationCondition<TType, TNode> implements IFormula {
	protected boolean negated;
	protected IFormula formula;

	protected List<Variable<TType, TNode>> variables;
	protected Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap;

	public ApplicationCondition(GraphSkeleton<TType, TNode> graph,
			Map<Variable<TType, TNode>, DomainSlot<TType, TNode>> domainMap,
			boolean negated) {
		this.domainMap = domainMap;
		this.negated = negated;
	}

	public boolean findGraph() {
		return findMatch(0);
	}

	/**
	 * Finds a match for the variable at the given index in the lhsVariables
	 * vector.
	 */
	protected boolean findMatch(int index) {
		if (index == variables.size()) {
			return formula.eval();
		}

		Variable<TType, TNode> variable = variables.get(index);
		DomainSlot<TType, TNode> slot = domainMap.get(variable);

		boolean validAssignment = false;

		while (!validAssignment) {
			validAssignment = slot.instanciate(variable, domainMap);

			if (validAssignment) {
				validAssignment = findMatch(index + 1);
			}

			if (!validAssignment) {
				slot.unlock(variable);
				if (!slot.instanciationPossible()) {
					slot.clear(variable);
					return false;
				}
			}
		}

		return true;
	}

	public List<Variable<TType, TNode>> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable<TType, TNode>> variables) {
		this.variables = variables;
	}

	/**
	 * @return the formula
	 */
	public IFormula getFormula() {
		return formula;
	}

	public void setFormula(IFormula formula) {
		this.formula = formula;
	}

	private void resetVariables() {
		for (Variable<TType, TNode> var : variables) {
			domainMap.get(var).clear(var);
		}
	}

	/**
	 * 
	 */
	public boolean eval() {
		boolean result = findGraph();
		resetVariables();

		return (result) ? !negated : negated;
	}
}