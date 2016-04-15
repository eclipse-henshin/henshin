/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa.result;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This class represents a conflict, which is on of the two forms of a critical pair.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class Conflict extends CriticalPair {

	/**
	 * The match of one of the two involved rules into the minimal model.
	 */
	Match match1;

	/**
	 * The match of one of the two involved rules into the minimal model.
	 */
	Match match2;

	/**
	 * Kind of the conflict.
	 */
	ConflictKind conflictKind;

	/**
	 * Default constructor.
	 * 
	 * @param r1 One of the two rules of the conflict.
	 * @param r2 The other rule of the two rules of the conflict.
	 * @param cpaEPackage The minimal model on which both rules are applicable.
	 * @param match1 The match of the rule <code>r1</code> into the <code>minimalModel</code>.
	 * @param match2 The match of the rule <code>r2</code> into the <code>minimalModel</code>.
	 * @param conflictKind The kind of the conflict.
	 */
	public Conflict(Rule r1, Rule r2, EPackage cpaEPackage, Match match1, Match match2, ConflictKind conflictKind) {
		super(r1, r2, cpaEPackage);
		this.match1 = match1;
		this.match2 = match2;
		this.conflictKind = conflictKind;
	}

	/**
	 * Returns the match of rule r1 into the minimal model.
	 * 
	 * @return The match of rule r1 into the minimal model.
	 */
	public Match getMatch1() {
		return match1;
	}

	/**
	 * Returns the match of rule r2 into the minimal model.
	 * 
	 * @return The match of rule r2 into the minimal model.
	 */
	public Match getMatch2() {
		return match2;
	}

	/**
	 * Returns the kind of the conflict.
	 * 
	 * @return The kind of the conflict.
	 */
	public ConflictKind getConflictKind() {
		return conflictKind;
	}
}
