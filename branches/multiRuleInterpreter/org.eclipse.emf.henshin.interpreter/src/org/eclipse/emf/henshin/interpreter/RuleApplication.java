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
package org.eclipse.emf.henshin.interpreter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.interfaces.InterpreterEngine;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.interpreter.util.ModelChange;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

/**
 * An implementation of an executable rule application. It must be initialized
 * with an instance of <code>org.eclipse.emf.henshin.model.Rule</code>.
 */
public class RuleApplication {
	private InterpreterEngine interpreterEngine;
	private Rule rule;
	
	private Match match;
	private Match comatch;
	
	private ModelChange modelChange;
	
	// flags for execution status of the rule
	private boolean isExecuted = false;
	private boolean isUndone = false;
	
	/**
	 * Creates a new RuleApplication.
	 * 
	 * @param engine
	 *            The InterpreterEngine used for matchfinding. Must not be
	 *            <code>null</code>
	 * @param rule
	 *            A Henshin rule. Must not be <code>null</code>
	 */
	public RuleApplication(InterpreterEngine engine, Rule rule) {
		if (engine == null) throw new IllegalArgumentException("engine can not be null");
		
		if (rule == null) throw new IllegalArgumentException("rule can not be null");
		
		this.rule = rule;
		this.interpreterEngine = engine;
		
		this.match = new Match(rule, new HashMap<Parameter, Object>(), new HashMap<Node, EObject>());
	}
	
	/**
	 * Returns a single match for this rule.
	 * 
	 * @return One match for this rule.
	 */
	public Match findMatch() {
		return interpreterEngine.findMatch(this);
	}
	
	/**
	 * Returns all possible matches for this rule.
	 * 
	 * @return A list of all matches.
	 */
	public List<Match> findAllMatches() {
		return interpreterEngine.findAllMatches(this);
	}
	
	/**
	 * Executes this rule. First a match must be found and checked if the rule
	 * can applied to it.
	 */
	public boolean apply() {
		if (!isExecuted) {
			comatch = interpreterEngine.applyRule(this);
			
			isExecuted = (comatch != null);
			return isExecuted;
		}
		
		return false;
	}
	
	/**
	 * Restores instance before rule application.
	 */
	public void undo() {
		if (isExecuted && !isUndone) {
			interpreterEngine.undoChanges(this);
			
			isUndone = true;
		}
	}
	
	/**
	 * Reapplies rule after its application was undone.
	 */
	public void redo() {
		if (isExecuted && isUndone) {
			interpreterEngine.redoChanges(this);
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
	
	/**
	 * Return the current value of the rule's parameter with the given name.
	 * Note, if this RuleApplication has been applied, a parameter's value might
	 * have changed due to the transformation. Correspondingly, the changed (and
	 * therefore current) value is returned in this case.
	 * 
	 * @param name
	 * @return
	 */
	public Object getParameterValue(String name) {
		Object result = null;
		Parameter parameter = rule.getParameterByName(name);
		if (parameter != null) {
			if (isExecuted && !isUndone) {
				result = comatch.getParameterValues().get(parameter);
			} else {
				result = match.getParameterValues().get(parameter);
			}// if else
		}// if
		return result;
	}// getParameterValue
	
	/**
	 * Adds a value for an input parameter or input object to the current rule.
	 * 
	 * @param name
	 *            Name of the input
	 * @param value
	 *            Value of the input
	 */
	public void setParameterValue(String name, Object value) {
		Parameter parameter = rule.getParameterByName(name);
		if (parameter != null) match.getParameterValues().put(parameter, value);
	}
	
	/**
	 * Sets the current rule's values for input parameters or input objects.
	 * (Existing values will be cleared)
	 * 
	 * @param assignments
	 *            Map between Parameter Names and their values
	 */
	public void setParameterValues(Map<String, Object> assignments) {
		for (String s : assignments.keySet()) {
			setParameterValue(s, assignments.get(s));
		}
	}
	
	/**
	 * Sets a partial or complete match for the current rule. This match will be
	 * part of all completions.
	 * 
	 * @param match
	 */
	public void setMatch(Match match) {
		this.match = match;
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
		match.getNodeMapping().put(node, value);
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
	
	/**
	 * set the comatch
	 * 
	 * @param comatch
	 */
	public void setComatch(Match comatch) {
		this.comatch = comatch;
	}
	
	/**
	 * 
	 * @return the model change
	 */
	public ModelChange getModelChange() {
		return modelChange;
	}
	
	/**
	 * Sets the model change
	 * 
	 * @param modelChange
	 */
	public void setModelChange(ModelChange modelChange) {
		this.modelChange = modelChange;
	}
	
	@Override
	public String toString() {
		return rule.getName();
	}
	
	/**
	 * @return the rule
	 */
	public Rule getRule() {
		return rule;
	}
	
	/**
	 * Returns the {@link InterpreterEngine} of this {@link RuleApplication}.<br>
	 * <br>
	 * <strong>Remark</strong>: Note, that any modification on the engine may
	 * lead to unpredictable side effects.
	 * 
	 * @return the interpreterEngine
	 */
	public InterpreterEngine getInterpreterEngine() {
		return interpreterEngine;
	}// getInterpreterEngine
	
}