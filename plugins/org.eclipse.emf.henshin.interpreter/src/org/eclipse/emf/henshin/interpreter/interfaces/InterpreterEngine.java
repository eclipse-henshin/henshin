/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.interfaces;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.common.util.TransformationOptions;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.util.Match;
import org.eclipse.emf.henshin.model.AmalgamationUnit;
import org.eclipse.emf.henshin.model.Parameter;

/**
 * Declares basic methods for all interpreter engines.
 */
public interface InterpreterEngine {

	/**
	 * Returns all matches for the given rule.
	 * 
	 * @param ruleApplication
	 *            A RuleApplication instance
	 * @return All computed matches for the rule.
	 */
	public List<Match> findAllMatches(RuleApplication ruleApplication);

	/**
	 * Returns a match for the given rule.
	 * 
	 * @param ruleApplication
	 *            A RuleApplication instance
	 * @return One computed match for the rule.
	 */
	public Match findMatch(RuleApplication ruleApplication);

	/**
	 * Generates a RuleApplication for an amalgamated rule. The amalgamated rule
	 * is dynamically constructed and its nodes are matched, but the
	 * RuleApplication is not executed.
	 * 
	 * @param amalgamationRule
	 *            The amalgamated rule that should be executed.
	 * @param parameterValues
	 * 
	 * @return A RuleApplication instance representing the amalgamated rule
	 */
	public RuleApplication generateAmalgamationRule(
			AmalgamationUnit amalgamationRule,
			Map<Parameter, Object> parameterValues);

	public void setOptions(TransformationOptions options);

	public boolean applyRule(RuleApplication ruleApplication);

	public void undoChanges(RuleApplication ruleApplication);

	public void redoChanges(RuleApplication ruleApplication);
}
