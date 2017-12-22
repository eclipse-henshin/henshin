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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class stores all the <code>CriticalPair</code>s as a result of a conflict reason analysis.
 * 
 * @author Florian Heﬂ, Kristopher Born
 *
 */
public class CPAResult implements Iterable<ConflictReason> {

	/**
	 * List of the conflict reasons.
	 */
	private List<ConflictReason> criticalPairs;

	/**
	 * Default constructor.
	 */
	public CPAResult() {
		criticalPairs = new ArrayList<ConflictReason>();
	}

	/**
	 * Adds a conflict reason to this result set.
	 * 
	 * @param criticalPair a conflict reason which will be added to the result.
	 */
	public void addResult(ConflictReason criticalPair) {
		criticalPairs.add(criticalPair);
	}

	/**
	 * Returns an iterator over the conflict reasons of the result set in proper sequence.
	 */
	public Iterator<ConflictReason> iterator() {
		return criticalPairs.iterator();
	}

	/**
	 * Returns the list of conflict reasons.
	 * 
	 * @return The list of conflict reasons.
	 */
	public List<ConflictReason> getCriticalPairs() {
		return criticalPairs;
	}

}
