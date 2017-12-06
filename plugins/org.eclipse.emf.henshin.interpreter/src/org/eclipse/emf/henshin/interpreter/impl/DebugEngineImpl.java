package org.eclipse.emf.henshin.interpreter.impl;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.emf.henshin.model.Rule;

public class DebugEngineImpl extends EngineImpl {

	private HenshinDebugTarget debugTarget;

	
	public DebugApplicationCondition getDebugApplicationCondition(Rule rule, EGraph graph, Match partialMatch) {
		
		// first create a standard ApplicationCondition
		MatchFinder matchFinder = (MatchFinder) new MatchGenerator(rule, graph, partialMatch).iterator();
		ApplicationCondition ac = matchFinder.getSolutionFinder();
		
		// create a DebugApplicationCondition using the standard ApplicationCondition
		DebugApplicationCondition debugApplicationCondition = new DebugApplicationCondition(
				ac.variables, ac.formula, ac.graph, ac.domainMap, debugTarget);
		
		return debugApplicationCondition;
	}
	
	public void setDebugTarget(HenshinDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}
	
	public HenshinDebugTarget getDebugTarget() {
		return debugTarget;
	}

}
