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

import org.eclipse.emf.common.util.URI;

/**
 * This class links the different files composing a critical pair within the file system.
 * 
 * @author Kristopher Born
 *
 */
public class CriticalPairNode {

	/**
	 * A String concatenation of a sorting number and the kind of conflict/dependency.
	 */
	String numberedNameOfCPKind;

	/**
	 * The <code>TreeFolder</code> in which the <code>CriticalPairNode</code> is contained.
	 */
	TreeFolder parent;

	/**
	 * The <code>URI</code>s of the three involved files.
	 */
	URI firstRuleURI, secondRuleURI, minimalModelURI;

	private URI criticalPairDummyURI;

	/**
	 * Default constructor.
	 * 
	 * @param numberedNameOfCPKind The String concatenation of a sorting number and the kind of conflict/dependency.
	 * @param firstRuleURI The <code>URI</code> of the first rule.
	 * @param secondRuleURI The <code>URI</code> of the second rule.
	 * @param minimalModelURI The <code>URI</code> of the minimal model.
	 * @param criticalPairURI
	 */
	public CriticalPairNode(String numberedNameOfCPKind, URI firstRuleURI, URI secondRuleURI, URI minimalModelURI,
			URI criticalPairURI) {
		this.numberedNameOfCPKind = numberedNameOfCPKind;
		this.firstRuleURI = firstRuleURI;
		this.secondRuleURI = secondRuleURI;
		this.minimalModelURI = minimalModelURI;
		this.criticalPairDummyURI = criticalPairURI;
	}

	/**
	 * Sets the <code>TreeFolder> in which this <code>CriticalPairdNode</code> shall be contained.
	 * 
	 * @param containgTreeFolder The <code>TreeFolder> in which this <code>CriticalPairdNode</code> shall be contained.
	 */
	public void setParent(TreeFolder containgTreeFolder) {
		parent = containgTreeFolder;
	}

	/**
	 * Returns the <code>TreeFolder> in which this <code>CriticalPairdNode</code> is contained.
	 * 
	 * @return The <code>TreeFolder> in which this <code>CriticalPairdNode</code> is contained.
	 */
	public TreeFolder getParent() {
		return parent;
	}

	/**
	 * Returns the String concatenation of a sorting number and the kind of conflict/dependency.
	 * 
	 * @return The String concatenation of a sorting number and the kind of conflict/dependency.
	 */
	public String toString() {
		return numberedNameOfCPKind;
	}

	/**
	 * Returns the <code>URI</code> of the first rule.
	 * 
	 * @return The <code>URI</code> of the first rule.
	 */
	public URI getFirstRuleURI() {
		return firstRuleURI;
	}

	/**
	 * Returns the <code>URI</code> of the second rule.
	 * 
	 * @return The <code>URI</code> of the second rule.
	 */
	public URI getSecondRuleURI() {
		return secondRuleURI;
	}

	/**
	 * Returns the <code>URI</code> of the minimal model.
	 * 
	 * @return The <code>URI</code> of the minimal model.
	 */
	public URI getMinimalModelURI() {
		return minimalModelURI;
	}

	public URI getCriticalPairDummyURI() {
		return criticalPairDummyURI;
	}

	public void setCriticalPairDummyURI(URI criticalPairDummyURI) {
		this.criticalPairDummyURI = criticalPairDummyURI;
	}
}
