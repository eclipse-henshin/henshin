package org.eclipse.emf.henshin.interpreter.interfaces;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.AmalgamatedUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Declares basic methods for all interpreter engines.
 */
public interface InterpreterEngine {

	/**
	 * Returns all matches for the given rule.
	 * 
	 * @param rule
	 *            A Henshin Rule.
	 * @return All computed matches for the rule.
	 */
	public List<Match> findAllMatches(Rule rule);

	/**
	 * Returns all matches for the given rule.
	 * 
	 * @param rule
	 *            A Henshin Rule.
	 * @param prematch
	 *            A predefined partial match for some nodes of the rule.
	 * @param assignments
	 *            A Map of Name-Value pairs for the variables of the rule, which
	 *            will be respected by the search engine.
	 * @return All computed matches for the rule.
	 */
	public List<Match> findAllMatches(Rule rule, Map<Node, EObject> prematch,
			Map<String, Object> assignments);

	/**
	 * Returns a match for the given rule.
	 * 
	 * @param rule
	 *            A Henshin Rule.
	 * @return One computed match for the rule.
	 */
	public Match findMatch(Rule rule);

	/**
	 * Returns a match for the given rule.
	 * 
	 * @param rule
	 *            A Henshin Rule.
	 * @param prematch
	 *            A predefined partial match for some nodes of the rule.
	 * @param assignments
	 *            A Map of Name-Value pairs for the variables of the rule, which
	 *            will be respected by the search engine.
	 * @return One computed match for the rule.
	 */
	public Match findMatch(Rule rule, Map<Node, EObject> prematch,
			Map<String, Object> assignments);

	/**
	 * Generates a RuleApplication for an amalgamated rule. The amalgamated rule
	 * is dynamically constructed and its nodes are matched, but the
	 * RuleApplication is not executed.
	 * 
	 * @param amalgamatedRule The amalgamated rule that should be executed.
	 * @param portValues 
	 * 
	 * @return true, if the kernel rule was found, otherwise false.
	 */
	public RuleApplication generateAmalgamatedRule(
			AmalgamatedUnit amalgamatedRule, Map<String, Object> portValues);

	/**
	 * Evaluates the given Java-Expression.
	 * 
	 * @param expr
	 *            An expression string.
	 * @return The result of the computation.
	 */
	public Object evalExpression(Map<String, Object> parameterMapping,
			String expr);

	/**
	 * 
	 * @return
	 */
	public Map<Rule, RuleInfo> getRuleInformation();

	/**
	 * Adds an EObject to the EMFGraph handled by this engine.
	 * 
	 * @param eObject
	 *            The EObject that should be added.
	 */
	public void addEObject(EObject eObject);

	/**
	 * Removes an EObject from the EmfGraph handled by this engine.
	 * 
	 * @param eObject
	 *            The EObject that should be removed.
	 */
	public void removeEObject(EObject eObject);
}
