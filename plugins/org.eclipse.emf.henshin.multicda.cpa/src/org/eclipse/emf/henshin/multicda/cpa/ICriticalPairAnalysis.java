/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa;

import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.multicda.cpa.result.CPAResult;

/**
 * Interface for executing the critical pair analysis on a set of Henshin rules.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public interface ICriticalPairAnalysis {

	/**
	 * Initializes the critical pair calculation with the <code>rules</code> parameter, used as first and second rules as well as the <code>options</code>.
	 * 
	 * @param rules The rules for which the critical pair analysis shall be executed.
	 * @param options The options settings that shall be applied on the calculation of the critical pairs
	 * @throws UnsupportedRuleException in case of invalid rules.
	 */
	void init(Set<Rule> rules, CDAOptions options) throws UnsupportedRuleException;

	/**
	 * Initializes the critical pair calculation with <code>r1</code> as first rules and <code>r2</code> as second rules
	 * of the critical pairs as well as the <code>options</code>.
	 * 
	 * @param r1 the first rules for the critical pair analysis.
	 * @param r2 the second rules for the critical pair analysis.
	 * @param options the options settings that shall be applied on the calculation of the critical pairs.
	 * @throws UnsupportedRuleException in case of invalid rules.
	 */
	void init(Set<Rule> r1, Set<Rule> r2, CDAOptions options) throws UnsupportedRuleException;

	/**
	 * Check for the validity of the rules in regard to the supported features.
	 * 
	 * @param rules The rules to be checked.
	 * @return <code>true</code> if the rules are valid for critical pair analysis.
	 * @throws UnsupportedRuleException in case of invalid rules.
	 */
	boolean check(Set<Rule> rules) throws UnsupportedRuleException;

	/**
	 * Starts the calculation of conflicts for the initialized rules and options.
	 * 
	 * @return a <code>CPAResult</code>, which consists of a set of <code>Conflict</code>s.
	 */
	CPAResult runConflictAnalysis();

	/**
	 * Starts the calculation of the conflicts for the initialized rules and options.
	 * 
	 * @param monitor a monitor to report the progress of the calculation.
	 * @return a set of critical pair results which are conflicts.
	 */
	CPAResult runConflictAnalysis(IProgressMonitor monitor);

	/**
	 * Starts the calculation of the dependencies for the initialized rules and options.
	 * 
	 * @return a set of critical pair results which are dependencies.
	 */
	CPAResult runDependencyAnalysis();

	/**
	 * Starts the calculation of the dependencies for the initialized rules and options.
	 * 
	 * @param monitor a monitor to report the progress of the calculation.
	 * @return a set of critical pair results which are dependencies.
	 */
	CPAResult runDependencyAnalysis(IProgressMonitor monitor);


}
