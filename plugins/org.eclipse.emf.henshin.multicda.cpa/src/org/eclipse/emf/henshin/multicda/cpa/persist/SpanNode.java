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

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

import agg.util.Pair;

/**
 * This class links the different files composing a critical pair within the file system.
 * 
 * @author Kristopher Born
 *
 */
public class SpanNode extends TreeFolder {

	URI minimalModelURI;
	Set<URI> minimalModelS2URI;
	/**
	 * The <code>TreeFolder</code> in which the <code>CriticalPairNode</code> is contained.
	 */
	TreeFolder parent;

	/**
	 * The <code>URI</code>s of the three involved files.
	 */
	URI firstRuleURI, secondRuleURI;
	/**
	 * A String concatenation of a sorting number and the kind of conflict/dependency.
	 */
	String numberedNameOfCPKind;

	private URI nodeURI;
	
	public final boolean conflict;

	/**
	 * Default constructor.
	 * 
	 * @param firstRuleURI The <code>URI</code> of the first rule.
	 * @param secondRuleURI The <code>URI</code> of the second rule.
	 * @param nodeURI
	 */
	public SpanNode(String numberedNameOfCPKind, URI firstRuleURI, URI secondRuleURI, URI minimalModelURI, boolean conflict) {
		this(numberedNameOfCPKind, firstRuleURI, secondRuleURI, minimalModelURI, null, conflict);
	}

	public SpanNode(String numberedNameOfCPKind, URI firstRuleURI, URI secondRuleURI, URI minimalModelURI,
			URI nodeURI, boolean conflict) {
		super(numberedNameOfCPKind);
		this.conflict = conflict;
		this.firstRuleURI = firstRuleURI;
		this.numberedNameOfCPKind = numberedNameOfCPKind;
		this.secondRuleURI = secondRuleURI;
		this.minimalModelURI = minimalModelURI;
		this.nodeURI = nodeURI;
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

	public URI getNodeURI() {
		return nodeURI;
	}

	public void setNodeURI(URI nodeURI) {
		this.nodeURI = nodeURI;
	}

	/**
	 * Returns the String concatenation of a sorting number and the kind of conflict/dependency.
	 * 
	 * @return The String concatenation of a sorting number and the kind of conflict/dependency.
	 */
	@Override
	public String toString() {
		return numberedNameOfCPKind;
	}

	/**
	 * Returns the <code>URI</code> of the minimal model.
	 * 
	 * @return The <code>URI</code> of the minimal model.
	 */
	public URI getMinimalModelURI() {
		return minimalModelURI;
	}

	/**
	 * Returns the <code>URI</code> of the s2 minimal model.
	 * 
	 * @return The <code>URI</code> of the s2 minimal model.
	 */
	public Set<URI> getMinimalModelS2URI() {
		return minimalModelS2URI;
	}
}
