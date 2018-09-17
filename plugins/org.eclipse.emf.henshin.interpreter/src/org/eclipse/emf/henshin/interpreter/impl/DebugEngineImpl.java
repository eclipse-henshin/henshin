package org.eclipse.emf.henshin.interpreter.impl;

import java.util.Observer;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.debug.HenshinDebugTarget;
import org.eclipse.emf.henshin.interpreter.matching.conditions.ApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.DebugApplicationCondition;
import org.eclipse.emf.henshin.interpreter.matching.conditions.TestDebugApplicationCondition;
import org.eclipse.emf.henshin.model.Rule;

public class DebugEngineImpl extends EngineImpl {

	private HenshinDebugTarget debugTarget;
	private MatchFinder matchFinder;

	public DebugApplicationCondition getDebugApplicationCondition(Rule rule, EGraph graph, Match partialMatch,
			Observer matchObserver) {

		matchFinder = (MatchFinder) new MatchGenerator(rule, graph, partialMatch).iterator();
		ApplicationCondition ac = matchFinder.getSolutionFinder();

		// create a DebugApplicationCondition using the standard
		// ApplicationCondition
		DebugApplicationCondition debugApplicationCondition = new DebugApplicationCondition(debugTarget, ac.variables,
				ac.domainMap, ac.graph, ac.formula, matchObserver, getRuleInfo(rule));

		return debugApplicationCondition;
	}

	public TestDebugApplicationCondition getTestDebugApplicationCondition(Rule rule, EGraph graph, Match partialMatch,
			Observer matchObserver) {

		matchFinder = (MatchFinder) new MatchGenerator(rule, graph, partialMatch).iterator();
		ApplicationCondition ac = matchFinder.getSolutionFinder();

		// create a DebugApplicationCondition using the standard
		// ApplicationCondition
		TestDebugApplicationCondition debugApplicationCondition = new TestDebugApplicationCondition(debugTarget,
				ac.variables, ac.domainMap, ac.graph, ac.formula, matchObserver, getRuleInfo(rule));

		return debugApplicationCondition;
	}

	public void setDebugTarget(HenshinDebugTarget debugTarget) {
		this.debugTarget = debugTarget;
	}

	public HenshinDebugTarget getDebugTarget() {
		return debugTarget;
	}

	public MatchFinder getMatchFinder() {
		return matchFinder;
	}

}
