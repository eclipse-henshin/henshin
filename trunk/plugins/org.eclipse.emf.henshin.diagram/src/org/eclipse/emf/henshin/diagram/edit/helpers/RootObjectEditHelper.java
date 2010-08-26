/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved.
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.edit.helpers;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.henshin.diagram.edit.actions.Action;
import org.eclipse.emf.henshin.diagram.edit.actions.ActionType;
import org.eclipse.emf.henshin.diagram.edit.actions.NodeActionHelper;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.NodeDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
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
 * Helper class for root objects.
 * @generated NOT
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
		if (!isRuleView(ruleView)) {
			throw new IllegalArgumentException();
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
	 * Set the root object for a node. 
	 * @param ruleView The rule view.
	 * @param newRoot The root node or <code>null</code>.
	 */
	public static void setRootObject(View ruleView, Node newRoot) throws ExecutionException {
		
		// We need the editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(ruleView.getElement());
		if (domain==null) {
			throw new RuntimeException();
		}
		
		// Check whether it is the correct view:
		Rule rule = (Rule) ruleView.getElement();
		if (!isRuleView(ruleView) || (newRoot!=null && !isPossibleRootType(newRoot.getType(), rule))) {
			throw new IllegalArgumentException();
		}
		
		// Get the root object annotation:
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
				NodeActionHelper.INSTANCE.setAction(newRoot, new Action(ActionType.PRESERVE));
			}
			
			// Now add the containment references:
			for (Node node : NodeActionHelper.INSTANCE.getActionElements(rule, null)) {
				updateRootContainment(ruleView, node);
			}
			
		} else {
			if (annotation!=null) {
				annotation.setEModelElement(null);
			}
		}
		
	}
		
	public static void setRootObjectType(View ruleView, EClass type) throws ExecutionException {
		if (type==null) {
			setRootObject(ruleView, null);
		} else {
			Rule rule = (Rule) ruleView.getElement();
			if (!RootObjectEditHelper.isPossibleRootType(type, rule)) {
				throw new IllegalArgumentException();
			}
			Node oldRoot = getRootObject(ruleView);
			if (oldRoot==null || !type.equals(oldRoot.getType().getName())) {
				Node newRoot = HenshinFactory.eINSTANCE.createNode();
				newRoot.setName("root");
				newRoot.setType(type);
				setRootObject(ruleView, newRoot);
			}
		}
	}
	
	public static boolean isPossibleRootType(EClass type, Rule rule) {
		if (rule.getTransformationSystem().getImports().size()!=1) {
			return false;
		}
		for (EClassifier classifier : rule.getTransformationSystem().getImports().get(0).getEClassifiers()) {
			if (classifier!=type && classifier instanceof EClass) {
				if (getRootContainment(type, (EClass) classifier)==null) return false;
			}
		}
		return true;
	}
	
	public static EReference getRootContainment(EClass rootType, EClass childType) {
		for (EReference reference : rootType.getEAllContainments()) {
			if (reference.getEReferenceType().isSuperTypeOf(childType)) {
				return reference;
			}
		}
		return null;
	}
	
	public static boolean isRuleView(View view) {
		return (view.getElement() instanceof Rule && view.getType().equals(String.valueOf(RuleEditPart.VISUAL_ID)));
	}
	
	public static void updateRootContainment(View ruleView, Node node) throws ExecutionException {

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
			boolean isNormalContained = false;
			for (Edge edge : node.getAllEdges()) {
				Node opposite = (node==edge.getSource()) ? edge.getTarget() : edge.getSource();
				if (opposite!=root && isContainmentEdge(opposite, edge)) {
					isNormalContained = true; break;
				}
			}

			if (isNormalContained) {
				
				// Check if it is also root-contained:
				for (Edge edge : node.getAllEdges()) {
					Node opposite = (node==edge.getSource()) ? edge.getTarget() : edge.getSource();
					if (opposite==root && isContainmentEdge(opposite, edge)) {
						// Delete the root containment:
						new EdgeDeleteCommand(domain, edge).execute(new NullProgressMonitor(), null);
					}
				}
				
			} else {
				
				// The node is not contained yet, so we have to add it to the root:
				EReference containment = getRootContainment(root.getType(), node.getType());
				CreateRelationshipRequest request = new CreateRelationshipRequest(root, node, HenshinElementTypes.Edge_4001);
				request.setParameter(EdgeCreateCommand.TYPE_PARAMETER_KEY, containment);
				new EdgeCreateCommand(request, root, node).execute(new NullProgressMonitor(), null);
				
			}
		}		
	}
	
	private static boolean isContainmentEdge(Node container, Edge edge) {
		if (edge.getSource()==container) {
			if (edge.getSource().getType().getEAllReferences().contains(edge.getType())) {
				return edge.getType().isContainment();
			} else {
				return edge.getType().isContainer();				
			}
		} else if (edge.getTarget()==container) {
			if (edge.getSource().getType().getEAllReferences().contains(edge.getType())) {
				return edge.getType().isContainment();
			} else {
				return edge.getType().isContainer();				
			}			
		}
		return false;
	}
	
}
