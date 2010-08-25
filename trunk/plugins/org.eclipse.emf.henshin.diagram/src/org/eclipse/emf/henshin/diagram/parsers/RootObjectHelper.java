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
package org.eclipse.emf.henshin.diagram.parsers;

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
import org.eclipse.emf.henshin.diagram.edit.commands.NodeDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Helper class for root objects.
 * @generated NOT
 * @author Christian Krause
 */
public class RootObjectHelper {
	
	/**
	 * Key for root object eAnnotations.
	 */
	public static final String ROOT_OBJECT_EANNOTATION_KEY = "rootObject";
	
	/**
	 * Get the root object for a rule. This evaluates the annotations of
	 * the rule's view and does not check whether the root object is valid.
	 * @param view The view of the rule.
	 * @return The root node if existing.
	 */
	public static Node getRootObject(View view) {
		
		// Check whether it is the correct view:
		if (!isRuleView(view)) {
			throw new IllegalArgumentException();
		}
		
		// Get the root object eAnnotation:
		EAnnotation annotation = view.getEAnnotation(ROOT_OBJECT_EANNOTATION_KEY);
		if (annotation==null) {
			return null;
		}
		
		// Find the root node:
		Node root = null;
		Graph lhs = ((Rule) view.getElement()).getLhs();
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
	 * @param view The rule view.
	 * @param newRoot The root node or <code>null</code>.
	 */
	public static void setRootObject(View view, Node newRoot) throws ExecutionException {
		
		// We need the editing domain
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(view.getElement());
		if (domain==null) {
			throw new RuntimeException();
		}
		
		// Check whether it is the correct view:
		Rule rule = (Rule) view.getElement();
		if (!isRuleView(view) || (newRoot!=null && !isPossibleRootType(newRoot.getType(), rule))) {
			throw new IllegalArgumentException();
		}
		
		// Get the root object annotation:
		EAnnotation annotation = view.getEAnnotation(ROOT_OBJECT_EANNOTATION_KEY);
		
		// Take care of the old root
		Node oldRoot = getRootObject(view);
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
				annotation.setEModelElement(view);
			}
			
			// Set the reference:
			annotation.getReferences().clear();
			annotation.getReferences().add(newRoot);
			
			// Now add the new root node to the LHS and RHS:
			if (!rule.getLhs().getNodes().contains(newRoot)) {
				rule.getLhs().getNodes().add(0,newRoot);
				NodeActionHelper.INSTANCE.setAction(newRoot, new Action(ActionType.PRESERVE));
			}
			
		} else {
			if (annotation!=null) {
				annotation.setEModelElement(null);
			}
		}
		
	}
	
	public static void setRootObjectType(View view, EClass type) throws ExecutionException {
		if (type==null) {
			setRootObject(view, null);
		} else {
			Rule rule = (Rule) view.getElement();
			if (!RootObjectHelper.isPossibleRootType(type, rule)) {
				throw new IllegalArgumentException();
			}
			Node oldRoot = getRootObject(view);
			if (oldRoot==null || !type.equals(oldRoot.getType().getName())) {
				Node newRoot = HenshinFactory.eINSTANCE.createNode();
				newRoot.setName("root");
				newRoot.setType(type);
				setRootObject(view, newRoot);
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
	
}
