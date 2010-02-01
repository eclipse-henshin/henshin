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

	protected List<Solution> solutions;

	public ApplicationCondition(EmfGraph graph,
			Map<Variable, DomainSlot> domainMap,
			AttributeConditionHandler conditionHandler, boolean negated) {
		this.domainMap = domainMap;
		this.conditionHandler = conditionHandler;
		this.graph = graph;
		this.negated = negated;
	}

	public boolean findGraph() {
		boolean matchIsPossible = false;

		if (solutions == null) {
			solutions = new ArrayList<Solution>();
			matchIsPossible = true;
		} else {
			for (int i = variables.size() - 1; i >= 0; i--) {
				Variable var = variables.get(i);
				if (domainMap.get(var).unlock(var)) {
					matchIsPossible = true;
					break;
				} else {
					domainMap.get(var).clear(var);
				}
			}
		}

		if (matchIsPossible) {
			boolean success = findMatch(0);
			if (success)
				solutions.add(new Solution(variables, domainMap,
						conditionHandler));

			return success;
		}

		return false;
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

	public void reset() {
		for (Variable variable : variables) {
			DomainSlot slot = domainMap.get(variable);
			slot.clear(variable);
		}
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

	/**
	 * @param negated
	 *            the negated to set
	 */
	public void setNegated(boolean negated) {
		this.negated = negated;
	}

	/**
	 * 
	 */
	public boolean eval() {
		while (findGraph()) {
			if (formula.eval()) {
				return !negated;
			}
		}

		return negated;
	}
}