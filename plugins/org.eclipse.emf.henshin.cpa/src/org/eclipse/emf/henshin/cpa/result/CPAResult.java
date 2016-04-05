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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class stores all the <code>CriticalPair</code>s as a result of a critical pair analysis.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class CPAResult implements Iterable<CriticalPair> {

	/**
	 * List of the critical pairs.
	 */
	private List<CriticalPair> criticalPairs;

	/**
	 * Default constructor.
	 */
	public CPAResult() {
		criticalPairs = new ArrayList<CriticalPair>();
	}

	/**
	 * Adds a critical pair to this result set.
	 * 
	 * @param criticalPair a critical pair which will be added to the result.
	 */
	public void addResult(CriticalPair criticalPair) {
		criticalPairs.add(criticalPair);
	}

	/**
	 * Returns an iterator over the critical pairs of the result set in proper sequence.
	 */
	public Iterator<CriticalPair> iterator() {
		return criticalPairs.iterator();
	}

	/**
	 * Returns the list of critical pairs.
	 * 
	 * @return The list of critical pairs.
	 */
	public List<CriticalPair> getCriticalPairs() {
		return criticalPairs;
	}

}
