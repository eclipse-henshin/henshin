/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.result;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This class represents a dependency, which is on of the two forms of a critical pair.
 * 
 * @author Florian Heﬂ, Kristopher Born
 */
public class Dependency extends CriticalPair {

	/**
	 * The comatch(mapping of the RHS) of the first rule into the minimal model.
	 */
	Match comatch;

	/**
	 * The match of the second rule into the minimal model.
	 */
	Match match;

	/**
	 * Kind of the dependency.
	 */
	DependencyKind dependencyKind;

	/**
	 * Default constructor.
	 * 
	 * @param r1 The first rule to enable the application of the second rule.
	 * @param r2 The second rule, which becomes applicable by the first rule.
	 * @param cpaEPackage The minimal model after applying the first rule on which the second rule is applicable.
	 * @param comatch The comatch of the first rule into the minimal model.
	 * @param match The match of the second rule into the minimal model.
	 * @param dependencyKind The kind of the dependency.
	 */
	public Dependency(Rule r1, Rule r2, EPackage cpaEPackage, Match comatch, Match match, DependencyKind dependencyKind, AppliedAnalysis appliedAnalysis) {
		super(r1, r2, cpaEPackage, appliedAnalysis);
		this.comatch = comatch;
		this.match = match;
		this.dependencyKind = dependencyKind;
	}

	/**
	 * Returns the comatch of the first rule into the minimal model
	 * 
	 * @return The comatch of the dependency.
	 */
	public Match getComatch() {
		return comatch;
	}

	/**
	 * Returns the match of the second rule into the minimal model
	 * 
	 * @return The match of the dependency.
	 */
	public Match getMatch() {
		return match;
	}

	/**
	 * Returns the kind of the dependency.
	 * 
	 * @return The kind of the dependency.
	 */
	public DependencyKind getDependencyKind() {
		return dependencyKind;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.emf.henshin.multicda.cpa.result.CriticalPair#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Dependency)
			return super.equals(obj);
		return false;
	}
}
