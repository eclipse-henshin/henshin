package org.eclipse.emf.henshin.internal.matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.conditions.nested.ApplicationCondition;
import org.eclipse.emf.henshin.internal.conditions.nested.IFormula;

public class Matchfinder extends ApplicationCondition {
	private AttributeConditionHandler handler;
	
	public Matchfinder(List<Variable> lhsVariables, IFormula formula, AttributeConditionHandler handler) {
		super(lhsVariables, formula, false);

		this.matches = new ArrayList<Match>();
		this.handler = handler;
	}
	
	@Override
	public void optimizeVariableOrder() {
		super.optimizeVariableOrder();
		formula.optimizeVariableOrder();
	}
	
	/**
	 * Returns a random match.
	 * This is as slow as computing all matches because it actually does compute 
	 * all matches and randomly chooses one as a result.
	 * 
	 * @return A random match or null if no match exists.
	 */
	public Match getRandomMatch() {
		while(getNextMatch() != null) {};

		if (matches.size() > 0)	{
			int x = new Random().nextInt(matches.size());
			return matches.get(x);
		}
		
		return null;
	}
	
	/**
	 * Returns a match. On consecutive calls other matches will be returned. 
	 * 
	 * @return A match or null if no match exists. 
	 */
	public Match getNextMatch() {
		boolean matchIsPossible = true;
		
		//there is already a match
		if (matches.size() > 0)
			matchIsPossible = gotoNextMatch();

		if (matchIsPossible) {
			boolean foundMatch = findMatch(0);
			if (foundMatch) {
				Match match = new Match(variables, handler);
				matches.add(match);
//				System.out.print("Match: ");
//				for (Variable var: variables)
//					System.out.print(var.getInstanceValue());
//				System.out.println();
				return matches.get(matches.size() - 1);
			}
		}
		
		return null;
	}
	
	
	/**
	 * 
	 */
	public List<Match> getAllMatches() {
		while(getNextMatch() != null) {};
		
		return matches;
	}	
	

	@Override
	public boolean findGraph() {
		return super.findGraph();
	}
	
	
	@Override
	public void reset() {
		matches.clear();
		
		for (Variable var: variables) {
			var.reset();
		}
		
		formula.reset();
	}
}
