package org.eclipse.emf.henshin.internal.conditions.nested;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.matching.DomainSlot;
import org.eclipse.emf.henshin.internal.matching.Solution;
import org.eclipse.emf.henshin.internal.matching.Variable;

public class ApplicationCondition implements IFormula {
	protected boolean negated;
	protected IFormula formula;

	protected EmfGraph graph;
	protected AttributeConditionHandler conditionHandler;

	protected List<Variable> variables;
	protected Map<Variable, DomainSlot> domainMap;

	public ApplicationCondition(EmfGraph graph,
			Map<Variable, DomainSlot> domainMap,
			AttributeConditionHandler conditionHandler, boolean negated) {
		this.domainMap = domainMap;
		this.conditionHandler = conditionHandler;
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
			validAssignment = slot.instanciate(variable, domainMap, graph,
					conditionHandler);

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