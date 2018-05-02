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

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.multicda.cpa.CPAUtility;

import agg.xt_basis.GraphObject;

/**
 * This class maps the different occurrences of each critical element.
 * 
 * @author Kristopher Born
 *
 */
public class CriticalElement extends OverlapElement {
	public CriticalElement() {
		super();
	}

	public CriticalElement(GraphObject commonElementOfCriticalGraph, GraphElement elementInFirstRule,
			GraphElement elementInSecondRule) {
		super(commonElementOfCriticalGraph, elementInFirstRule, elementInSecondRule);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CriticalElement) {
			String a = toString();
			String b = obj.toString();
			return a.equals(b);
		}
		return false;
	}

	@Override
	public String toString() {
		return simpleName(elementInFirstRule, elementInSecondRule);
	}

	private String simpleName(GraphElement g1, GraphElement g2) {
		if (g1 instanceof Edge && g2 instanceof Edge) {
			Edge e1 = (Edge) g1;
			Edge e2 = (Edge) g2;
			return "Edge (" + e1.getType().getName() + ") " + e1.getSource().getName() + CPAUtility.SEPARATOR
					+ e2.getSource().getName() + " -> " + e1.getTarget().getName() + CPAUtility.SEPARATOR
					+ e2.getTarget().getName();
		} else if (g1 instanceof Node && g2 instanceof Node) {
			Node n1 = (Node) g1;
			Node n2 = (Node) g2;
			return "Node " + n1.getName() + CPAUtility.SEPARATOR + n2.getName() + ":" + n1.getType().getName();
		} else if (g1 instanceof Attribute && g2 instanceof Attribute) {
			Attribute a1 = (Attribute) g1;
			Attribute a2 = (Attribute) g2;
			return simpleName(a1.getNode(), a2.getNode());
		} else if (commonElementOfCriticalGraph != null)
			return commonElementOfCriticalGraph.getObjectName() + ":"
					+ commonElementOfCriticalGraph.getType().getName();
		return "";
	}
}
