/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.interpreter;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class ChangeInfo {
	private Collection<Node> createdNodes;
	private Collection<Node> deletedNodes;
	private Collection<Node> preservedNodes;
	private Collection<Edge> createdEdges;
	private Collection<Edge> deletedEdges;
	private Collection<Attribute> attributeChanges;

	public ChangeInfo(Rule rule) {
		createdNodes = new ArrayList<Node>();
		createdEdges = new ArrayList<Edge>();
		deletedEdges = new ArrayList<Edge>();
		deletedNodes = new ArrayList<Node>();
		attributeChanges = new ArrayList<Attribute>();
		preservedNodes = new ArrayList<Node>();

		for (Node node : rule.getLhs().getNodes()) {
			if (!ModelHelper.isNodeMapped(rule.getMappings(), node)) {
				deletedNodes.add(node);
			}
		}

		for (Node node : rule.getRhs().getNodes()) {
			if (!ModelHelper.isNodeMapped(rule.getMappings(), node)) {
				createdNodes.add(node);
			} else {
				preservedNodes.add(node);
			}

			for (Attribute attribute : node.getAttributes()) {
				attributeChanges.add(attribute);
			}
		}

		for (Edge edge : rule.getLhs().getEdges()) {
			if (!ModelHelper.isEdgeMapped(rule.getMappings(), edge)) {
				deletedEdges.add(edge);
			}
		}

		for (Edge edge : rule.getRhs().getEdges()) {
			if (!ModelHelper.isEdgeMapped(rule.getMappings(), edge)) {
				createdEdges.add(edge);
			}
		}

	}

	/**
	 * @return the createdNodes
	 */
	public Collection<Node> getCreatedNodes() {
		return createdNodes;
	}

	/**
	 * @return the preservedNodes
	 */
	public Collection<Node> getPreservedNodes() {
		return preservedNodes;
	}

	/**
	 * @return the createdEdges
	 */
	public Collection<Edge> getCreatedEdges() {
		return createdEdges;
	}

	/**
	 * @return the deletedEdges
	 */
	public Collection<Edge> getDeletedEdges() {
		return deletedEdges;
	}

	/**
	 * @return the attributeChanges
	 */
	public Collection<Attribute> getAttributeChanges() {
		return attributeChanges;
	}

	/**
	 * @return the deletedNodes
	 */
	public Collection<Node> getDeletedNodes() {
		return deletedNodes;
	}
}
