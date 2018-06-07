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

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is part of the structure for persisting the results. Each TreeFolder represents the combination of two
 * rules for which critical pairs exist.
 * 
 * @author Kristopher Born
 *
 */
public class TreeFolder implements Comparable<TreeFolder> {

	/**
	 * List of nodes, of which each node represents a single Tree Folder.
	 */
	List<TreeFolder> nodes;

	/**
	 * A name combining the two involved rules.
	 */
	String name;

	/**
	 * The default constructor.
	 * 
	 * @param nameOfInvolvedRules The name of the two involved rules.
	 */
	public TreeFolder(String nameOfInvolvedRules) {
		this.name = nameOfInvolvedRules;
		nodes = new LinkedList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add()
	 */
	public boolean addChild(SpanNode criticalPairNode) {
		criticalPairNode.setParent(this);
		return nodes.add(criticalPairNode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add()
	 */
	public boolean addChild(TreeFolder child) {
		return nodes.add(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#add()
	 */
	public boolean removeChild(TreeFolder child) {
		return nodes.remove(child);
	}

	/**
	 * Returns the combined name of the two involved rules.
	 * 
	 * @return The combined name of the two involved rules.
	 */
	public String toString() {
		return name;
	}

	/**
	 * Returns the information if critical pair results are contained.
	 * 
	 * @return whether the number of contained single critical pairs is greater than zero.
	 */
	public boolean hasChildren() {
		return nodes.size() > 0;
	}

	/**
	 * Returns the contained single critical pairs as an Array.
	 * 
	 * @return the contained single critical pairs as an Array.
	 */
	public TreeFolder[] getChildren() {
		return (TreeFolder[]) nodes.toArray(new TreeFolder[nodes.size()]);
	}

	@Override
	public int compareTo(TreeFolder o) {
		return name.compareTo(name);
	}
	public Comparator<TreeFolder> getComparator(){
		return new Comparator<TreeFolder>() {
			@Override
			public int compare(TreeFolder o1, TreeFolder o2) {
				return o1.compareTo(o2);
			}
		};
	}
	
	public String getName() {
		return name;
	}
}
