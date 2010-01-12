package org.eclipse.emf.henshin.internal.conditions.nested;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.internal.matching.Match;
import org.eclipse.emf.henshin.internal.matching.Variable;


public class GraphFinder {
	protected List<Variable> variables;
	protected List<Match> matches;
	
	public GraphFinder(List<Variable> variables) {
		this.variables = variables;
		this.matches = new ArrayList<Match>();
	}
	
	public boolean findGraph() {
		boolean matchIsPossible = true;
		
		//there is already a match
		if (matches.size() > 0)
			matchIsPossible = gotoNextMatch();

		if (matchIsPossible) {
			return findMatch(0);
		}
		
		return false;
	}
	
	/**
	 * Finds a match for the variable at the given index in the lhsVariables vector.
	 */
	protected boolean findMatch(int index) {
		if (index >= variables.size())
			return true;
		
		Variable currentVariable = variables.get(index);
		boolean validAssignment = false;
		
		while (!validAssignment) {
			validAssignment = currentVariable.nextInstance();
			
			if (validAssignment) {
				validAssignment = findMatch(index + 1);
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
	
	protected boolean gotoNextMatch() {
		int continueAt = -1;
		
		// find the variable with the highest index which has additional domain objects for instanciation 
		for (int i = variables.size() - 1; i >= 0; i--) {
			Variable currentVariable = variables.get(i);
			if (!currentVariable.isEnabled()) {
				continueAt = -2;
				break;
				//return true;
			}
			
			int instanceIndex = currentVariable.getInstanceIndex();
			currentVariable.deinstanciate();

			int domainSize = currentVariable.getDomain().size();
			if (instanceIndex != domainSize - 1) {
				currentVariable.setStartingIndex(currentVariable.getInstanceIndex());
				continueAt = i;
				break;
			}
		}
		
		// deinstanciate all variables
		for (Variable currentVariable: variables) {
			if (variables.indexOf(currentVariable) < continueAt) {
				currentVariable.setInstanceIndex(Math.max(currentVariable.getInstanceIndex() - 1, -1));
				currentVariable.setStartingIndex(-1);
			}
			else if (variables.indexOf(currentVariable) > continueAt) {
				currentVariable.setInstanceIndex(-1);
				currentVariable.setStartingIndex(-1);
			}
			
			currentVariable.deinstanciate();
		}
		
		return continueAt != -1;
	}
	
	/**
	 * Optimizes the order in which variables are evaluated.
	 */
	public void optimizeVariableOrder() {
		Collections.sort(variables);
	}
	
	/**
	 * Randomizes the order of EObjects in the domain vectors of variables.
	 * This will result in a semi random match order.
	 * Note that this doesn't mean each match has the same probability of occuring. 
	 */
	public void randomizeDomainVectors() {
		for (Variable variable: variables) {
			Collections.shuffle(variable.getDomain());
		}
	}

	/**
	 * @return the variables
	 */
	public List<Variable> getVariables() {
		return variables;
	}
	
	public void reset() {
		matches.clear();
		
		for (Variable var: variables) {
			//var.reset();
			if (var.isSelfenabled()) {
				var.disable();
			}
		}
	}
}
