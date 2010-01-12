package org.eclipse.emf.henshin.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.interpreter.AmalgamationWrapper;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleWrapper;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.RuleMatch;
import org.eclipse.emf.henshin.model.AmalgamatedUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * The default implementation of an interpreter engine. 
 */
public class EmfEngine implements InterpreterEngine {
	EmfGraph emfGraph;
	
	Map<Rule, RuleWrapper> rule2wrapper;
	Map<Rule, RuleInfo> rule2ruleInfo;

	ScriptEngine scriptEngine;

	public EmfEngine(EmfGraph emfGraph) {
		this.emfGraph = emfGraph;

		rule2wrapper = new HashMap<Rule, RuleWrapper>();
		rule2ruleInfo = new HashMap<Rule, RuleInfo>();

		ScriptEngineManager mgr = new ScriptEngineManager();
		scriptEngine = mgr.getEngineByName("JavaScript");
	}

	public List<RuleMatch> findAllMatches(Rule rule) {
		return findAllMatches(rule, null, null);
	}

	public List<RuleMatch> findAllMatches(Rule rule,
			Map<Node, EObject> prematch, Map<String, Object> assignments) {
		RuleWrapper wrapper = rule2wrapper.get(rule);

		if (wrapper != null) {
			wrapper.reset();
			wrapper.setAssignments(assignments);
			wrapper.setMatchObjects(prematch);
		} else {
			wrapper = new RuleWrapper(rule, emfGraph, scriptEngine);
			wrapper.setAssignments(assignments);
			wrapper.setMatchObjects(prematch);
			rule2wrapper.put(rule, wrapper);
		}

		return wrapper.getAllMatches();
	}

	public RuleMatch findMatch(Rule rule) {
		return findMatch(rule, null, null);
	}

	public RuleMatch findMatch(Rule rule, Map<Node, EObject> prematch,
			Map<String, Object> assignments) {
		RuleWrapper wrapper = rule2wrapper.get(rule);

		if (wrapper != null) {
			wrapper.reset();
			wrapper.setAssignments(assignments);
			wrapper.setMatchObjects(prematch);
		} else {
			wrapper = new RuleWrapper(rule, emfGraph, scriptEngine);
			wrapper.setAssignments(assignments);
			wrapper.setMatchObjects(prematch);
			rule2wrapper.put(rule, wrapper);
		}

		return wrapper.getMatch();
	}

	public boolean executeAmalgamatedUnit(AmalgamatedUnit amalgamatedUnit) {
		AmalgamationWrapper amalgamationWrapper = new AmalgamationWrapper(this,
				amalgamatedUnit, emfGraph, scriptEngine);
		RuleMatch amalgamatedMatch = amalgamationWrapper.getAmalgamatedRule();

		if (amalgamatedMatch != null) {
			RuleApplication amalgamatedRule = new RuleApplication(this,
					amalgamatedMatch.getRule());
			amalgamatedRule.setMatch(amalgamatedMatch);
			return amalgamatedRule.apply();
		} else {
			return false;
		}
	}

	public ScriptEngine getEngine() {
		return scriptEngine;
	}

	public Object evalExpression(Map<String, Object> parameterMapping,
			String expr) {
		try {
			for (String parameter : parameterMapping.keySet()) {
				scriptEngine.put(parameter, parameterMapping.get(parameter));
			}

			return scriptEngine.eval(expr);
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	@Override
	public Map<Rule, RuleInfo> getRuleInformation() {
		return rule2ruleInfo;
	}

	@Override
	public void addEObject(EObject eObject) {
		emfGraph.addEObject(eObject);
	}

	@Override
	public void removeEObject(EObject eObject) {
		emfGraph.removeEObject(eObject);
	}
	
	public void purgeRuleCache() {
		rule2ruleInfo.clear();
		rule2wrapper.clear();
	}

	/**
	 * @return the emfGraph
	 */
	public EmfGraph getEmfGraph() {
		return emfGraph;
	}
}