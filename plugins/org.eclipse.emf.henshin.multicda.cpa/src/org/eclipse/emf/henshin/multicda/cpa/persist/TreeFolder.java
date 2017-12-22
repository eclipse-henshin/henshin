/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.persist;

import java.util.LinkedList;
import java.util.List;

/**
 * This class is part of the structure for persisting the results. Each TreeFolder represents the combination of two
 * rules for which conflict reasons exist.
 * 
 * @author Kristopher Born
 *
 */
public class TreeFolder {

	/**
	 * List of nodes, of which each node represents a single conflict reason.
	 */
	List<ConflictReasonNode> singleCriticalPairResults;

	/**
	 * A name combining the two involved rules.
	 */
	String nameOfInvolvedRules;

	/**
	 * The default constructor.
	 * 
	 * @param nameOfInvolvedRules The name of the two involved rules.
	 */
	public TreeFolder(String nameOfInvolvedRules) {
		this.nameOfInvolvedRules = nameOfInvolvedRules;
		singleCriticalPairResults = new LinkedList<ConflictReasonNode>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add()
	 */
	public boolean addChild(ConflictReasonNode criticalPairNode) {
		// criticalPairNode.setParent(this);
		return singleCriticalPairResults.add(criticalPairNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove()
	 */
	public boolean removeChild(ConflictReasonNode child) {
		// child.setParent(null);
		return singleCriticalPairResults.remove(child);
	}

	/**
	 * Returns the combined name of the two involved rules.
	 * 
	 * @return The combined name of the two involved rules.
	 */
	public String toString() {
		return nameOfInvolvedRules;
	}

	/**
	 * Returns the information if conflict reason results are contained.
	 * 
	 * @return whether the number of contained single conflict reasons is greater than zero.
	 */
	public boolean hasChildren() {
		return singleCriticalPairResults.size() > 0;
	}

	/**
	 * Returns the contained single conflict reasons as an Array.
	 * 
	 * @return the contained single conflict reasons as an Array.
	 */
	public ConflictReasonNode[] getChildren() {
		return (ConflictReasonNode[]) singleCriticalPairResults.toArray(new ConflictReasonNode[singleCriticalPairResults
				.size()]);
	}
}
