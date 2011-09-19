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
package org.eclipse.emf.henshin.matching.conditions.nested;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.matching.constraints.Variable;

public class ApplicationCondition implements IFormula {
	protected boolean negated;
	protected IFormula formula;

	protected EmfGraph graph;

	protected List<Variable> variables;
	protected Map<Variable, DomainSlot> domainMap;

	public ApplicationCondition(EmfGraph graph,
			Map<Variable, DomainSlot> domainMap, boolean negated) {
		this.domainMap = domainMap;
		this.graph = graph;
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

		Variable variable = variables.get(index);
		DomainSlot slot = domainMap.get(variable);

		boolean validAssignment = false;

		while (!validAssignment) {
			validAssignment = slot.instantiate(variable, domainMap, graph);

			if (validAssignment) {
				validAssignment = findMatch(index + 1);
			}

			if (!validAssignment) {
				slot.unlock(variable);
				if (!slot.instantiationPossible()) {
					slot.clear(variable);
					return false;
				}
			}
		}

		return true;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
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
		for (Variable var : variables) {
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