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
 * This represents the root element of the new created tree structure per critical pair analysis run.
 * 
 * @author Kristopher Born
 *
 */
public class RootElement {

	/**
	 * The list of all contained <code>TreeFolder</code>s.
	 */
	List<TreeFolder> children;

	/**
	 * Default constructor.
	 */
	public RootElement() {
		children = new LinkedList<TreeFolder>();
	}

	/**
	 * Adds a TreeFolder to this <code>RootElement</code>.
	 * 
	 * @param child a TreeFolder which shall be contained within this <code>RootElement</code>.
	 */
	public void addChild(TreeFolder child) {
		children.add(child);
	}

	/**
	 * Returns all the <code>TreeFolder</code>s within this <code>RootElement</code>.
	 * 
	 * @return the <code>TreeFolder</code>s within this <code>RootElement</code>.
	 */
	public TreeFolder[] getChildren() {
		return (TreeFolder[]) children.toArray(new TreeFolder[children.size()]);
	}
}
