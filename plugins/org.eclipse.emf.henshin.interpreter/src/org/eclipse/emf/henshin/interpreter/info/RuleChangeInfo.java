/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.info;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Rule;

public class RuleChangeInfo {
	
	private final List<Node> createdNodes;
	private final List<Node> deletedNodes;
	private final List<Attribute> deletedAttributes;
	private final List<Node> preservedNodes;
	private final List<Edge> createdEdges;
	private final List<Edge> deletedEdges;
	private final List<Attribute> attributeChanges;
	private final List<Edge> indexChanges;
	
	public RuleChangeInfo(Rule rule) {
		createdNodes = new ArrayList<Node>();
		deletedNodes = new ArrayList<Node>();
		deletedAttributes = new ArrayList<Attribute>();
		preservedNodes = new ArrayList<Node>();		
		createdEdges = new ArrayList<Edge>();
		deletedEdges = new ArrayList<Edge>();
		attributeChanges = new ArrayList<Attribute>();
		indexChanges = new ArrayList<Edge>();
		
		// Deleted nodes; deleted attributes:
		for (Node node : rule.getLhs().getNodes()) {
			if (rule.getMultiMappings().getOrigin(node)!=null) {
				continue;
			}
			Node image = rule.getMappings().getImage(node, rule.getRhs());
			if (image==null) {
				deletedNodes.add(node);
			} else {
				for (Attribute attribute : node.getAttributes()) {
					boolean attributeDeleted = image.getAttribute(attribute.getType()) == null;
					if (attributeDeleted) {
						if (attribute.getType().isUnsettable()
								&& isAttributeChangeable(attribute)) {
							deletedAttributes.add(attribute);
						} else {
							throw new IllegalStateException("Cannot delete attribute which is unsetable"
									+ " or not changeable.");
						}
					}
				}
			}
		}
		
		// Created, preserved nodes; attribute changes:
		for (Node node : rule.getRhs().getNodes()) {
			if (rule.getMultiMappings().getOrigin(node)!=null) {
				continue;
			}
			Node origin = rule.getMappings().getOrigin(node);
			
			if (origin==null) {
				createdNodes.add(node);
			} else {
				preservedNodes.add(node);
			}			
			for (Attribute attribute : node.getAttributes()) {
				boolean attributeChanged = hasAttributeChanged(rule, origin, attribute);
				if (attributeChanged) {
					if (isAttributeChangeable(attribute)) {
						attributeChanges.add(attribute);					
					} else {
						throw new IllegalStateException("Cannot assign a value to derived or "
								+ "unchangeable attribute.");
					}
				}
			}
		}
		
		// Deleted edges:
		for (Edge edge : rule.getLhs().getEdges()) {
			if (rule.getMultiMappings().getOrigin(edge)!=null) {
				continue;
			}
			if (rule.getMappings().getImage(edge, rule.getRhs())==null) {
				deletedEdges.add(edge);
			}
		}
		
		// Created edges and index changes:
		for (Edge edge : rule.getRhs().getEdges()) {
			if (rule.getMultiMappings().getOrigin(edge)!=null) {
				continue;
			}
			if (rule.getMappings().getOrigin(edge)==null) {
				createdEdges.add(edge);
			}
			if (edge.getIndex()!=null && edge.getIndex().trim().length()>0) {
				indexChanges.add(edge);
			}
		}		
	}
	
	/**
	 * Returns whether the value of the given attribute has changed.  
	 * @param rule rule containing the attribute
	 * @param origin the LHS node mapped to the RHS node containing the given attribute
	 * @param attribute attribute of an RHS node 
	 * @return true if the attribute value has changed - false otherwise
	 */
	private boolean hasAttributeChanged(Rule rule, Node origin, Attribute attribute) {
		boolean changed;
		// Attribute has been created explicitly; value assignment expected
		if (origin == null) {
			changed = true;
		//Attribute preserved but value changed
		} else {
			Attribute originAttribute = origin.getAttribute(attribute.getType());
			changed =!( originAttribute != null 
					&& originAttribute.getValue().equals(attribute.getValue()));
		}
		return changed;
	}
	
	private boolean isAttributeChangeable(Attribute attribute) {
		return !attribute.getType().isDerived() && attribute.getType().isChangeable();
	}
	
	/**
	 * @return the createdNodes
	 */
	public List<Node> getCreatedNodes() {
		return createdNodes;
	}
	
	/**
	 * @return the preservedNodes
	 */
	public List<Node> getPreservedNodes() {
		return preservedNodes;
	}
	
	/**
	 * @return the createdEdges
	 */
	public List<Edge> getCreatedEdges() {
		return createdEdges;
	}
	
	/**
	 * @return the deletedEdges
	 */
	public List<Edge> getDeletedEdges() {
		return deletedEdges;
	}
	
	/**
	 * @return the deletedAttributes
	 */
	public List<Attribute> getDeletedAttributes() {
		return deletedAttributes;
	}
	
	/**
	 * @return the attributeChanges
	 */
	public List<Attribute> getAttributeChanges() {
		return attributeChanges;
	}
	
	/**
	 * @return the deletedNodes
	 */
	public List<Node> getDeletedNodes() {
		return deletedNodes;
	}
		
	/**
	 * @return the index changes
	 */
	public List<Edge> getIndexChanges() {
		return indexChanges;
	}
	
}
