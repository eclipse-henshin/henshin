package org.eclipse.emf.henshin.internal.conditions.nested;

import java.util.List;

import org.eclipse.emf.henshin.internal.matching.Variable;


public class ApplicationCondition extends GraphFinder implements IFormula {
	protected boolean negated;
	protected IFormula formula;
	
	public ApplicationCondition(List<Variable> variables, IFormula formula) {
		this(variables, formula, true);
	}
	
	public ApplicationCondition(List<Variable> variables, IFormula formula, boolean isNegated) {
		super(variables);
		this.negated = isNegated;
		this.formula = formula;
	}
	
	/**
	 * @return the formula
	 */
	public IFormula getFormula() {
		return formula;
	}

	/**
	 * @param formula the formula to set
	 */
	public void setFormula(IFormula formula) {
		this.formula = formula;
	}

	/**
	 * @param negated the negated to set
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
				for (Variable var: variables) {
					var.setInstanceIndex(-1);
					if (var.isSelfenabled())
						var.reset();
				}
				return !negated;
			}
		}
		
		for (Variable var: variables) {
			var.setInstanceIndex(-1);
			if (var.isSelfenabled())
				var.reset();
		}
		
		return negated;
	}
	
	protected boolean findMatch(int index) {
		if (variables.size() == 0) {
			return formula.eval();
		}
		
		if (index >= variables.size())
			return true;
		
		Variable currentVariable = variables.get(index);
		boolean validAssignment = false;
		
		while (!validAssignment) {
			validAssignment = currentVariable.nextInstance();
			
			if (validAssignment) {
				validAssignment = findMatch(index + 1);
			}
			
			if (index == variables.size() - 1) {
				if (validAssignment && formula.eval())
					return true;
				else
					validAssignment = false;
			}
		
				
			if (!validAssignment) {
				currentVariable.deinstanciate();
				if (currentVariable.getInstanceIndex() >= currentVariable.getDomain().size() - 1) {
					currentVariable.setInstanceIndex(-1);
					return false;
				}
			}
		}
		
		return validAssignment;
	}
	
	public void reset() {
		matches.clear();
		
		for (Variable var: variables) {
			var.disable();
		}
		super.reset();
	}
}