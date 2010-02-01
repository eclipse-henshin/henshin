package org.eclipse.emf.henshin.internal.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;

public class GraphFinder {
	protected EmfGraph graph;
	protected AttributeConditionHandler conditionHandler;

	protected List<Variable> variables;
	protected Map<Variable, DomainSlot> domainMap;

	protected List<Solution> solutions;

	public GraphFinder(EmfGraph graph, Map<Variable, DomainSlot> domainMap,
			AttributeConditionHandler conditionHandler) {
		this.domainMap = domainMap;
		this.conditionHandler = conditionHandler;
		this.graph = graph;
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
				}
				else {
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
		if (index == variables.size())
			return true;

		Variable variable = variables.get(index);
		DomainSlot slot = domainMap.get(variable);

		boolean validAssignment = false;

		while (!validAssignment) {
			validAssignment = slot.applyTypeConstraint(variable
					.getTypeConstraint(), graph);

			for (AttributeConstraint constraint : variable
					.getAttributeConstraints()) {
				if (validAssignment)
					validAssignment = slot.applyAttributeConstraint(constraint);
				else
					break;
			}

			if (validAssignment)
				validAssignment = slot.instanciate();

			for (ParameterConstraint constraint : variable
					.getParameterConstraints()) {
				if (validAssignment)
					validAssignment = slot.applyParameterConstraint(constraint,
							conditionHandler);
				else
					break;
			}

			for (ReferenceConstraint constraint : variable
					.getReferenceConstraints()) {
				if (validAssignment)
					validAssignment = slot.applyReferenceConstraint(constraint,
							domainMap.get(constraint.getTarget()));
				else
					break;
			}

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
}
