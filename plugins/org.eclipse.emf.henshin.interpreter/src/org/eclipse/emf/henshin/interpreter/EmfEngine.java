package org.eclipse.emf.henshin.interpreter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.common.util.EmfGraph;
import org.eclipse.emf.henshin.internal.conditions.attribute.AttributeConditionHandler;
import org.eclipse.emf.henshin.internal.conditions.nested.AndFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.ApplicationCondition;
import org.eclipse.emf.henshin.internal.conditions.nested.IFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.NotFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.OrFormula;
import org.eclipse.emf.henshin.internal.conditions.nested.TrueFormula;
import org.eclipse.emf.henshin.internal.interpreter.AmalgamationWrapper;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.internal.interpreter.RuleWrapper;
import org.eclipse.emf.henshin.internal.matching.Matchfinder;
import org.eclipse.emf.henshin.internal.matching.DomainSlot;
import org.eclipse.emf.henshin.internal.matching.Solution;
import org.eclipse.emf.henshin.internal.matching.Variable;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.AmalgamatedUnit;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;

/**
 * The default implementation of an interpreter engine.
 */
public class EmfEngine implements InterpreterEngine {
	EmfGraph emfGraph;

	Map<Rule, RuleWrapper> rule2wrapper;
	Map<Rule, RuleInfo> rule2ruleInfo;

	public EmfEngine(EmfGraph emfGraph) {
		this.emfGraph = emfGraph;

		rule2wrapper = new HashMap<Rule, RuleWrapper>();
		rule2ruleInfo = new HashMap<Rule, RuleInfo>();
	}

	private Map<Variable, DomainSlot> createDomainMap(RuleWrapper wrapper,
			Map<Node, EObject> prematch) {
		Map<Variable, DomainSlot> domainMap = new HashMap<Variable, DomainSlot>();
		Map<Variable, Variable> mainVariableMap = wrapper
				.getVariable2mainVariable();
		Set<EObject> usedObjects = new HashSet<EObject>();

		for (Variable var : wrapper.getMainVariables()) {
			Node node = wrapper.getVariable2node().get(var);

			DomainSlot slot = (prematch.get(node) != null) ? new DomainSlot(
					prematch.get(node), usedObjects) : new DomainSlot(var,
					usedObjects);

			domainMap.put(var, slot);
		}

		for (Variable var : mainVariableMap.keySet()) {
			Variable mainVariable = mainVariableMap.get(var);
			DomainSlot slot = domainMap.get(mainVariable);
			domainMap.put(var, slot);
		}

		return domainMap;
	}

	// TODO(enrico): refactor matchfinder construction to a factory class
	private Matchfinder prepareMatchfinder(Rule rule,
			Map<Node, EObject> prematch, Map<String, Object> assignments) {
		// TODO(enrico): check whether local script engine is a big slowdown
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");

		RuleWrapper wrapper = rule2wrapper.get(rule);

		AttributeConditionHandler handler = new AttributeConditionHandler(rule,
				scriptEngine);
		if (assignments != null) {
			for (String parameterName : assignments.keySet()) {
				handler.setParameter(parameterName, assignments
						.get(parameterName));
			}
		}

		Map<Variable, DomainSlot> domainMap = createDomainMap(wrapper, prematch);
		Map<Graph, List<Variable>> graphMap = wrapper.getGraph2variables();

		Matchfinder matchfinder = new Matchfinder(emfGraph, domainMap, handler);
		matchfinder.setVariables(graphMap.get(rule.getLhs()));
		matchfinder.setFormula(initFormula(rule.getLhs().getFormula(),
				domainMap, graphMap, handler));

		return matchfinder;
	}

	private IFormula initFormula(Formula formula,
			Map<Variable, DomainSlot> domainMap,
			Map<Graph, List<Variable>> graphMap,
			AttributeConditionHandler conditionHandler) {
		if (formula instanceof And) {
			And and = (And) formula;
			IFormula left = initFormula(and.getLeft(), domainMap, graphMap,
					conditionHandler);
			IFormula right = initFormula(and.getRight(), domainMap, graphMap,
					conditionHandler);
			AndFormula andFormula = new AndFormula(left, right);

			return andFormula;
		} else if (formula instanceof Or) {
			Or or = (Or) formula;
			IFormula left = initFormula(or.getLeft(), domainMap, graphMap,
					conditionHandler);
			IFormula right = initFormula(or.getRight(), domainMap, graphMap,
					conditionHandler);
			OrFormula orFormula = new OrFormula(left, right);

			return orFormula;
		} else if (formula instanceof Not) {
			Not not = (Not) formula;
			IFormula child = initFormula(not.getChild(), domainMap, graphMap,
					conditionHandler);
			NotFormula notFormula = new NotFormula(child);

			return notFormula;
		} else if (formula instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) formula;
			IFormula ac = initApplicationCondition(nc, domainMap, graphMap,
					conditionHandler);

			return ac;
		}

		return new TrueFormula();
	}

	private ApplicationCondition initApplicationCondition(NestedCondition nc,
			Map<Variable, DomainSlot> domainMap,
			Map<Graph, List<Variable>> graphMap,
			AttributeConditionHandler conditionHandler) {
		ApplicationCondition ac = new ApplicationCondition(emfGraph, domainMap,
				conditionHandler, nc.isNegated());
		ac.setVariables(graphMap.get(nc.getConclusion()));
		ac.setFormula(initFormula(nc.getConclusion().getFormula(), domainMap,
				graphMap, conditionHandler));

		return ac;
	}

	public List<Match> findAllMatches(Rule rule) {
		return findAllMatches(rule, null, null);
	}

	public List<Match> findAllMatches(Rule rule, Map<Node, EObject> prematch,
			Map<String, Object> assignments) {
		RuleWrapper wrapper = rule2wrapper.get(rule);

		if (wrapper == null) {
			wrapper = new RuleWrapper(rule);
			rule2wrapper.put(rule, wrapper);
		}

		Matchfinder matchfinder = prepareMatchfinder(rule, prematch,
				assignments);
		List<Solution> solutions = matchfinder.getAllMatches();
		List<Match> matches = new ArrayList<Match>();
		for (Solution solution : solutions) {
			Match match = new Match(rule, solution, wrapper.getNode2variable());
			matches.add(match);
		}

		return matches;
	}

	public Match findMatch(Rule rule) {
		return findMatch(rule, null, null);
	}

	public Match findMatch(Rule rule, Map<Node, EObject> prematch,
			Map<String, Object> assignments) {
		RuleWrapper wrapper = rule2wrapper.get(rule);

		if (wrapper == null) {
			wrapper = new RuleWrapper(rule);
			rule2wrapper.put(rule, wrapper);
		}

		Matchfinder matchfinder = prepareMatchfinder(rule, prematch,
				assignments);
		Solution solution = matchfinder.getNextMatch();

		if (solution != null) {
			Match match = new Match(rule, solution, wrapper.getNode2variable());
			return match;
		} else
			return null;
	}

	public boolean executeAmalgamatedUnit(AmalgamatedUnit amalgamatedUnit) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");

		AmalgamationWrapper amalgamationWrapper = new AmalgamationWrapper(this,
				amalgamatedUnit, emfGraph, scriptEngine);
		Match amalgamatedMatch = amalgamationWrapper.getAmalgamatedRule();

		if (amalgamatedMatch != null) {
			RuleApplication amalgamatedRule = new RuleApplication(this,
					amalgamatedMatch.getRule());
			amalgamatedRule.setMatch(amalgamatedMatch);
			return amalgamatedRule.apply();
		} else {
			return false;
		}
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

	// TODO: delete this method
	public Object evalExpression(Map<String, Object> parameterMapping,
			String expr) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");
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
}