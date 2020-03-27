package org.eclipse.emf.henshin.variability;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.henshin.interpreter.ApplicationMonitor;
import org.eclipse.emf.henshin.interpreter.Assignment;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareMatcher;
import org.eclipse.emf.henshin.variability.matcher.VariabilityAwareMatch;
import org.eclipse.emf.henshin.variability.util.RuleUtil;

/**
 * Variability-aware {@link org.eclipse.emf.henshin.interpreter.RuleApplication
 * RuleApplication} implementation.
 * 
 * @author Daniel Strüber
 * @author Sven Peldszus
 */
public class VarRuleApplicationImpl extends RuleApplicationImpl {
	private VariabilityAwareMatch completeVarMatch;
	private Map<String, Boolean> configuration;

	public VarRuleApplicationImpl(Engine engine, EGraph graph, Rule rule, Assignment partialMatch) {
		super(engine, graph, rule, partialMatch);
		this.completeVarMatch = null;
		this.configuration = new HashMap<String,Boolean>();
	}

	/**
	 * 
	 * @param engine
	 * @param graph
	 * @param rule
	 * @param completeMatchVar If available, a complete match
	 * @param configuration A (potentially partial) map of feature names to a boolean value
	 */
	public VarRuleApplicationImpl(Engine engine, EGraph graph, Rule rule, Map<String,Boolean> configuration, VariabilityAwareMatch completeMatchVar) {
		super(engine, graph, rule, null);
		if (configuration == null)
			this.configuration = new HashMap<String,Boolean>();
		else
			this.configuration = configuration;
		this.completeVarMatch = completeMatchVar;
	}

	public VarRuleApplicationImpl(Engine engine) {
		super(engine);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.UnitApplication#execute(org.eclipse
	 * .emf.henshin.interpreter.ApplicationMonitor)
	 */
	@Override
	public boolean execute(ApplicationMonitor monitor) {
		if (unit == null) {
			throw new NullPointerException("No transformation unit set");
		}
		// Already executed?
		if (isExecuted) {
			if (isCompleteMatchDerived) {
				completeMatch = null; // reset the complete match if it was
										// derived
				isCompleteMatchDerived = false;
			}
			isExecuted = false;
			isUndone = false;
			change = null;
			resultMatch = null;
		}
		// Do we need to derive a complete match?

		if (completeVarMatch != null) {
			completeMatch = completeVarMatch.getMatch();
		} else {
			long startTime = System.currentTimeMillis();

			if (completeMatch == null) {
				if (!RuleUtil.isVarRule(unit)) {
					completeMatch = engine.findMatches((Rule) unit, graph, partialMatch).iterator().next();
				} else {
					VariabilityAwareMatcher vbEngine;
					try {
						List<String> initiallyTrue = configuration.keySet().stream().filter(s -> configuration.get(s) == true).collect(Collectors.toList());
						List<String> initiallyFalse = configuration.keySet().stream().filter(s -> configuration.get(s) == false).collect(Collectors.toList());
						vbEngine = new VariabilityAwareMatcher((Rule) unit, graph, initiallyTrue, initiallyFalse);
					} catch (InconsistentRuleException e) {
						return false;
					}
					Set<VariabilityAwareMatch> matches = vbEngine.findMatches();
					if (!matches.isEmpty()) {
						completeVarMatch = (VariabilityAwareMatch) matches.iterator().next();
						completeMatch = completeVarMatch.getMatch();
						unit = completeVarMatch.getMatch().getRule();

					}

				}
				isCompleteMatchDerived = true;
			}
			long runtime = (System.currentTimeMillis() - startTime);
			MatchingLog.getEntries().add(new MatchingLogEntry(unit, completeMatch != null, runtime, graph.size(), 0)); // InterpreterUtil.countEdges(graph)));
			if (completeMatch == null) {
				if (monitor != null) {
					monitor.notifyExecute(this, false);
				}
				return false;
			}
		}
		if (completeVarMatch != null) {
			completeVarMatch.prepareRule();
		}
		resultMatch = new MatchImpl((Rule) unit, true);
		change = engine.createChange((Rule) unit, graph, completeMatch, resultMatch);
		if (change == null) {
			if (monitor != null) {
				monitor.notifyExecute(this, false);
			}
			return false;
		}
		change.applyAndReverse();
		isExecuted = true;
		if (monitor != null) {
			monitor.notifyExecute(this, true);
		}
		if (completeVarMatch != null) {
			completeVarMatch.undoPreparation();
		}
		return true;
	}

}
