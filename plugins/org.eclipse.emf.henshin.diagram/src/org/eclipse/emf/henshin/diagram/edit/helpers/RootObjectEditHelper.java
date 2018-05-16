/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.helpers;

import static org.eclipse.emf.henshin.model.Action.Type.PRESERVE;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.NodeDeleteCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Helper class for handling root objects in the 
 * graphical editor.
 * @author Christian Krause
 */
public class RootObjectEditHelper {
	
	/**
	 * Key for root object eAnnotations.
	 */
	public static final String ROOT_OBJECT_EANNOTATION_KEY = "rootObject";
	
	/**
	 * Get the root object for a rule. This evaluates the annotations of
	 * the rule's view and does not check whether the root object is valid.
	 * @param ruleView The view of the rule.
	 * @return The root node if existing.
	 */
	public static Node getRootObject(View ruleView) {
		
		// Check whether it is the correct view:
		if (!RuleEditHelper.isRuleView(ruleView)) {
			return null;
		}
		
		// Get the root object eAnnotation:
		EAnnotation annotation = ruleView.getEAnnotation(ROOT_OBJECT_EANNOTATION_KEY);
		if (annotation==null) {
			return null;
		}
		
		// Find the root node:
		Node root = null;
		Graph lhs = ((Rule) ruleView.getElement()).getLhs();
		for (EObject current : annotation.getReferences()) {
			if (current instanceof Node && lhs.getNodes().contains(current)) {
				root = (Node) current;
				break;
			}
		}
		
		// Done.
		return root;
		
	}

	/**
	 * Set the root object for a node. Rather use {@link #setRootObjectType(View, EClass)}.
	 * @param ruleView The rule view.
	 * @param newRoot The root node or <code>null</code>.
	 */
	public static void setRootObject(View ruleView, Node newRoot) throws ExecutionException {
		
		// We need the editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(ruleView.getElement());
		if (domain==null) {
			throw new RuntimeException();
		}
		
		// Get the root object annotation:
		Rule rule = (Rule) ruleView.getElement();
		EAnnotation annotation = ruleView.getEAnnotation(ROOT_OBJECT_EANNOTATION_KEY);
		
		// Take care of the old root
		Node oldRoot = getRootObject(ruleView);
		if (oldRoot!=null) {
			
			// Same root node?
			if (oldRoot==newRoot) return;
			
			// Otherwise delete the old one:
			NodeDeleteCommand delete = new NodeDeleteCommand(domain, oldRoot);
			delete.execute(new NullProgressMonitor(), null);
			
		}
				
		// Take care of the new root:
		if (newRoot!=null) {
			
			// Create the annotation if necessary:
			if (annotation==null) {
				annotation = EcoreFactory.eINSTANCE.createEAnnotation();
				annotation.setSource(ROOT_OBJECT_EANNOTATION_KEY);
				annotation.setEModelElement(ruleView);
			}
			
			// Set the reference:
			annotation.getReferences().clear();
			annotation.getReferences().add(newRoot);
			
			// Now add the new root node to the LHS and RHS:
			if (!rule.getLhs().getNodes().contains(newRoot)) {
				rule.getLhs().getNodes().add(0,newRoot);
				newRoot.setAction(new Action(PRESERVE));
			}
			
			// Now add the containment references:
			for (Node node : rule.getActionNodes(null)) {
				updateRootContainment(ruleView, node);
			}
			
		} else {
			if (annotation!=null) {
				annotation.setEModelElement(null);
			}
		}
		
	}
	
	/**
	 * Set the root object type for a rule. Before doing this you should make sure that
	 * the root object type is ok using {@link #isPossibleRootType(EClass, Rule)}.
	 * @param ruleView The rule view.
	 * @param type Root object type.
	 * @throws ExecutionException On errors.
	 */
	public static void setRootObjectType(View ruleView, EClass type) throws ExecutionException {
		if (type==null) {
			setRootObject(ruleView, null);
		} else {
			Node oldRoot = getRootObject(ruleView);
			if (oldRoot==null || !type.equals(oldRoot.getType())) {
				Node newRoot = HenshinFactory.eINSTANCE.createNode();
				newRoot.setName("root");
				newRoot.setType(type);
				setRootObject(ruleView, newRoot);
			}
		}
	}
	
	/**
	 * Get the canonical containment reference for a root object containment.
	 * Returns <code>null</code> if no containment reference was found.
	 * @param rootType The type of the root object.
	 * @param childType The type of the child.
	 * @return The containment reference.
	 */
	public static EReference getRootContainment(EClass rootType, EClass childType) {
		for (EReference reference : rootType.getEAllContainments()) {
			// Upper bound must be -1 and the type must be ok.
			if (reference.getUpperBound()<0 && reference.getEReferenceType().isSuperTypeOf(childType)) {
				return reference;
			}
		}
		return null;
	}
	
	/**
	 * Update the root containment for a given action node.
	 * If the node is not an action node, nothing happens. 
	 * @param ruleView Rule view.
	 * @param node The node.
	 * @throws ExecutionException On errors.
	 */
	public static void updateRootContainment(View ruleView, Node node) throws ExecutionException {
		
		// Make sure we really have a node in a rule:
		if (node==null || node.getGraph()==null || node.getGraph().getRule()==null) {
			return;
		}
		
		// First make sure we have an action node:
		node = node.getActionNode();
		if (node==null) return;
		
		// Get the root node first, must be not null:
		Node root = getRootObject(ruleView);
		if (root==null) return;

		// We need the editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(ruleView.getElement());
		if (domain==null) {
			throw new RuntimeException();
		}
		
		// No containment for the root node itself:
		if (node==root) {
			for (Edge edge : root.getAllEdges()) {
				if (!isContainmentEdge(root, edge)) {
					new EdgeDeleteCommand(domain, edge).execute(new NullProgressMonitor(), null);
				}
			}
		} else {
			
			// Check if there is a incoming containment edge:
			List<Edge> normalContainments = new ArrayList<Edge>();
			List<Edge> rootContainments = new ArrayList<Edge>();
			for (Edge edge : node.getAllEdges()) {
				Node opposite = (node==edge.getSource()) ? edge.getTarget() : edge.getSource();
				if (isContainmentEdge(opposite, edge)) {
					if (opposite==root) {
						rootContainments.add(edge);
					} else {
						normalContainments.add(edge);						
					}
				}
			}
			
			if (!normalContainments.isEmpty()) {
				
				// Remove all root containments then:
				for (Edge edge : rootContainments) {
					new EdgeDeleteCommand(domain, edge).execute(new NullProgressMonitor(), null);
				}
			
			} else if (rootContainments.isEmpty()) {
				
				// The node is not contained yet, so we have to add it to the root:
				EReference containment = getRootContainment(root.getType(), node.getType());
				if (containment==null) {
					throw new ExecutionException("No containment reference for " + node.getType().getName() + " found in root object type " + root.getType().getName());
				}
				CreateRelationshipRequest request = new CreateRelationshipRequest(root, node, HenshinElementTypes.Edge_4001);
				request.setParameter(EdgeCreateCommand.TYPE_PARAMETER_KEY, containment);
				new EdgeCreateCommand(request, root, node).execute(new NullProgressMonitor(), null);
				
			}
			
			// If there is more than one root containment, delete the extra ones (invalid).
			for (int i=1; i<rootContainments.size(); i++) {
				new EdgeDeleteCommand(domain, rootContainments.get(i)).execute(new NullProgressMonitor(), null);				
			}
			
		}
	}
	
	/**
	 * Check whether an edge is containment.
	 * @param container Container node.
	 * @param edge Outgoing or incoming edge.
	 * @return <code>true</code> if the edge is containment.
	 */
	private static boolean isContainmentEdge(Node container, Edge edge) {
		if (edge.getSource()==container) {
			if (edge.getSource().getType().getEAllReferences().contains(edge.getType())) {
				return edge.getType().isContainment();
			} else {
				return edge.getType().isContainer();				
			}
		} else if (edge.getTarget()==container) {
			if (edge.getSource().getType().getEAllReferences().contains(edge.getType())) {
				return edge.getType().isContainer();
			} else {
				return edge.getType().isContainment();				
			}			
		}
		return false;
	}
	
}
