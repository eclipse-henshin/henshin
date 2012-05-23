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
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.AttributeConditionHandler;

public class SolutionFinder extends ApplicationCondition {
	
	protected List<Solution> solutions;
	protected AttributeConditionHandler conditionHandler;
	
	public SolutionFinder(EGraph graph, Map<Variable, DomainSlot> variableDomainMap,
			AttributeConditionHandler conditionHandler) {
		super(graph, variableDomainMap, false);
		this.conditionHandler = conditionHandler;
	}
	
	public boolean findSolution() {
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
			boolean success = findGraph();
			if (success)
				solutions.add(new Solution(variables, domainMap, conditionHandler));
			
			return success;
		}
		
		return false;
	}
	
	/**
	 * Returns a random solution. This is as slow as computing all solutions because
	 * it actually does compute all solutions and randomly chooses one as a result.
	 * 
	 * @return A random match or null if no match exists.
	 */
	public Solution getRandomSolution() {
		while (getNextSolution() != null) {
		}
		if (solutions.size() > 0) {
			int x = new Random().nextInt(solutions.size());
			return solutions.get(x);
		}
		return null;
	}
	
	/**
	 * Returns a solution. On consecutive calls other matches will be returned.
	 * 
	 * @return A solution or null if no match exists.
	 */
	public Solution getNextSolution() {
		if (findSolution()) {
			return solutions.get(solutions.size() - 1);
		}
		return null;
	}
	
	public List<Solution> getAllSolutions() {
		while (getNextSolution() != null) {}
		return solutions;
	}
	
}
