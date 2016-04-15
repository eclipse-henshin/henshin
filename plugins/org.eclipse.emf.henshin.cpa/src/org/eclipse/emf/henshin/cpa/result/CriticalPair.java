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

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Rule;

/**
 * This class represents a critical pair
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public abstract class CriticalPair {

	/**
	 * One of the two rules which lead to the critical pair.
	 */
	private Rule r1;

	/**
	 * One of the two rules which lead to the critical pair.
	 */
	private Rule r2;

	/**
	 * The minimal model, which allows to observe the circumstance of the critical pair.
	 */
	private EPackage minimalModel;

	/**
	 * The critical elements, which are the root cause of the critical pair.
	 */
	private List<CriticalElement> criticalElements;

	CriticalPair(Rule r1, Rule r2, EPackage minimalModel) {
		this.r1 = r1;
		this.r2 = r2;
		this.minimalModel = minimalModel;
		criticalElements = new LinkedList<CriticalElement>();
	}

	/**
	 * Returns one of the two rules which lead to the critical pair.
	 * 
	 * @return One of the two rules which lead to the critical pair.
	 */
	public Rule getFirstRule() {
		return r1;
	}

	/**
	 * Returns one of the two rules which lead to the critical pair.
	 * 
	 * @return One of the two rules which lead to the critical pair.
	 */
	public Rule getSecondRule() {
		return r2;
	}

	/**
	 * Returns the minimal model to inspect the relation of the critical pair.
	 * 
	 * @return The minimal model to inspect the relation of the critical pair.
	 */
	public EPackage getMinimalModel() {
		return minimalModel;
	}

	/**
	 * Adds critical elements to the list of critical elements of this critical pair, given that each critical pair is
	 * complete.
	 * 
	 * @param newCriticalElements critical elements of this critical pair.
	 * @return <code>true</code> if all the critical elements were complete and thus could be all added.
	 */
	public boolean addCriticalElements(List<CriticalElement> newCriticalElements) {
		if (criticalElements == null)
			criticalElements = new LinkedList<CriticalElement>();

		boolean allCriticalElementsComplete = true;

		for (CriticalElement criticalElement : newCriticalElements) {
			if (criticalElement.commonElementOfCriticalGraph == null || criticalElement.elementInFirstRule == null
					|| criticalElement.elementInSecondRule == null) {
				allCriticalElementsComplete = false;
			} else {
				allCriticalElementsComplete &= criticalElements.add(criticalElement);
			}
		}
		return allCriticalElementsComplete;
	}

	/**
	 * Returns a List of critical elements of this critical pair.
	 * 
	 * @return A List of <code>CriticalElement</code>s of this critical pair.
	 */
	public List<CriticalElement> getCriticalElements() {
		return criticalElements;
	}

}
