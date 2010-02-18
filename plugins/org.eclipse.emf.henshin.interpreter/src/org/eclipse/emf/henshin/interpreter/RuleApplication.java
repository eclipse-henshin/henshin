package org.eclipse.emf.henshin.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.common.util.ModelHelper;
import org.eclipse.emf.henshin.internal.change.ModelChange;
import org.eclipse.emf.henshin.internal.interpreter.RuleInfo;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

/**
 * An implementation of an executable rule application. It must be initialized
 * with an instance of <code>org.eclipse.emf.henshin.model.Rule</code>.
 */
public class RuleApplication {
	private InterpreterEngine interpreterEngine;

	private Rule rule;
	private ModelChange modelChange;

	private Match match;
	private Match comatch;

	// flags for execution status of the rule
	private boolean isExecuted = false;
	private boolean isUndone = false;

	// partial match respected by search for completion
	private Map<Node, EObject> prematch;

	// contains the assignments for the variables of the rule
	private Map<String, Object> assignments;

	/**
	 * Creates a new RuleApplication.
	 * 
	 * @param engine The InterpreterEngine used for matchfinding
	 * @param rule A Henshin rule
	 */
	public RuleApplication(InterpreterEngine engine, Rule rule) {
		this.rule = rule;

		modelChange = new ModelChange();
		interpreterEngine = engine;
		prematch = new HashMap<Node, EObject>();
		assignments = new HashMap<String, Object>();
	}

	/**
	 * Computes all necessary model changes resulting from the current match.
	 * Note that this method doesn't actually change the model.
	 * 
	 * @return the comatch from the RHS into the instance
	 */
	private Match generateModelChanges() {
		if (!match.isComplete())
			return null;

		RuleInfo ruleInfo = interpreterEngine.getRuleInformation().get(rule);
		if (ruleInfo == null) {
			ruleInfo = new RuleInfo(rule);
			interpreterEngine.getRuleInformation().put(rule, ruleInfo);
		}

		Map<Node, EObject> matchNodeMapping = match.getNodeMapping();
		Map<Node, EObject> comatchNodeMapping = new HashMap<Node, EObject>();

		// create new EObjects with their attributes
		for (Node node : ruleInfo.getCreatedNodes()) {

			EClass type = node.getType();
			EPackage ePackage = type.getEPackage();

			EObject newObject = ePackage.getEFactoryInstance().create(type);
			modelChange.addCreatedObject(newObject);
			interpreterEngine.addEObject(newObject);

			comatchNodeMapping.put(node, newObject);
		}

		// delete EObjects
		for (Node node : ruleInfo.getDeletedNodes()) {
			modelChange.addDeletedObject(match.getNodeMapping().get(node));
			interpreterEngine.removeEObject(match.getNodeMapping().get(node));
		}

		for (Node node : ruleInfo.getPreservedNodes()) {
			Node lhsNode = ModelHelper.getRemoteNode(rule.getMappings(), node);
			EObject targetObject = matchNodeMapping.get(lhsNode);
			comatchNodeMapping.put(node, targetObject);
		}

		// remove deleted edges
		for (Edge edge : ruleInfo.getDeletedEdges()) {
			modelChange.addObjectChange(matchNodeMapping.get(edge.getSource()),
					edge.getType(), matchNodeMapping.get(edge.getTarget()));

		}

		// add new edges
		for (Edge edge : ruleInfo.getCreatedEdges()) {
			modelChange.addObjectChange(comatchNodeMapping
					.get(edge.getSource()), edge.getType(), comatchNodeMapping
					.get(edge.getTarget()));
		}

		for (Attribute attribute : ruleInfo.getAttributeChanges()) {
			EObject targetObject = comatchNodeMapping.get(attribute.getNode());
			Object value = interpreterEngine.evalExpression(match
					.getParameterMapping(), attribute.getValue());

			//workaround for Double conversion
			String valueString = value.toString();
			if (valueString.endsWith(".0"))
				valueString = valueString.substring(0, valueString.length() - 2);
			
			value = EcoreUtil.createFromString(attribute.getType()
					.getEAttributeType(), valueString);

			modelChange.addObjectChange(targetObject, attribute.getType(),
					value);
		}

		return new Match(rule, match.getParameterMapping(),
				comatchNodeMapping);
	}

	/**
	 * Returns a single match for this rule.
	 * 
	 * @return One match for this rule.
	 */
	public Match findMatch() {
		return interpreterEngine.findMatch(rule, prematch, assignments);
	}

	/**
	 * Returns all possible matches for this rule.
	 * 
	 * @return A list of all matches.
	 */
	public List<Match> findAllMatches() {
		return interpreterEngine.findAllMatches(rule, prematch, assignments);
	}

	/**
	 * Executes this rule. First a match must be found and checked if the rule
	 * can applied to it.
	 */
	public boolean apply() {
		if (!isExecuted) {
			match = findMatch();
			if (match == null)
				return false;

			comatch = generateModelChanges();
			if (comatch == null)
				return false;

			modelChange.applyChanges();

			isExecuted = true;
			return true;
		}

		return false;
	}

	/**
	 * Restores instance before rule application.
	 */
	public void undo() {
		if (isExecuted && !isUndone) {
			modelChange.undoChanges();

			for (EObject deletedObject : modelChange.getDeletedObjects()) {
				interpreterEngine.addEObject(deletedObject);
			}

			for (EObject createdObject : modelChange.getCreatedObjects()) {
				interpreterEngine.removeEObject(createdObject);
			}

			isUndone = true;
		}
	}

	/**
	 * Reapplies rule after its application was undone.
	 */
	public void redo() {
		if (isExecuted && isUndone) {
			for (EObject createdObject : modelChange.getCreatedObjects()) {
				interpreterEngine.addEObject(createdObject);
			}

			for (EObject deletedObject : modelChange.getDeletedObjects()) {
				interpreterEngine.removeEObject(deletedObject);
			}

			modelChange.redoChanges();

			isUndone = false;
		}
	}

	/**
	 * Gives the rule descriuption read from the model rule.
	 * 
	 * @return Description as String
	 */
	public String getDescription() {
		return rule.getDescription();
	}

	public Map<String, Object> getAssignments() {
		if (isExecuted)
			return comatch.getParameterMapping();
		else
			return assignments;
	}

	/**
	 * Adds a value for an input parameter or input object to the current rule.
	 * 
	 * @param name
	 *            Name of the input
	 * @param value
	 *            Value of the input
	 */
	public void addAssignment(String name, Object value) {
		assignments.put(name, value);
	}

	/**
	 * Adds a value for an input parameter or input object to the current rule.
	 * 
	 * @param assignments
	 *            A Map with assignments
	 */
	public void addAssignments(Map<String, Object> assignments) {
		for (String name : assignments.keySet()) {
			this.assignments.put(name, assignments.get(name));
		}
	}

	/**
	 * Sets a partial or complete match for the current rule. This match will be
	 * part of all completions.
	 * 
	 * @param match
	 */
	public void setMatch(Match match) {
		this.prematch = match.getNodeMapping();
		this.assignments = match.getParameterMapping();
	}

	/**
	 * Adds a single object mapping the rule.
	 * 
	 * @param node
	 *            An LHS node.
	 * @param value
	 *            An EObject in the instance the rule should be applied to
	 */
	public void addMatch(Node node, EObject value) {
		prematch.put(node, value);
	}

	/**
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * @return the comatch
	 */
	public Match getComatch() {
		return comatch;
	}
	
	@Override
	public String toString() {
		return rule.getName();
	}
}