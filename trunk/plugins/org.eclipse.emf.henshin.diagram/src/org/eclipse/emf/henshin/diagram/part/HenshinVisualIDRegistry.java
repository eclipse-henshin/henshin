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
package org.eclipse.emf.henshin.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeActionEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeTypeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeActionEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeTypeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleNameEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.TransformationSystemEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitNameEditPart;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 * 
 * @generated
 */
public class HenshinVisualIDRegistry {

	/**
	 * @generated
	 */
	private static final String DEBUG_KEY = "org.eclipse.emf.henshin.diagram/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static int getVisualID(View view) {
		if (view instanceof Diagram) {
			if (TransformationSystemEditPart.MODEL_ID.equals(view.getType())) {
				return TransformationSystemEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry
				.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return (String) annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static int getVisualID(String type) {
		try {
			return Integer.parseInt(type);
		} catch (NumberFormatException e) {
			if (Boolean.TRUE.toString().equalsIgnoreCase(
					Platform.getDebugOption(DEBUG_KEY))) {
				HenshinDiagramEditorPlugin.getInstance().logError(
						"Unable to parse view type as a visualID number: "
								+ type);
			}
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static String getType(int visualID) {
		return Integer.toString(visualID);
	}

	/**
	 * @generated
	 */
	public static int getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (HenshinPackage.eINSTANCE.getTransformationSystem().isSuperTypeOf(
				domainElement.eClass())
				&& isDiagram((TransformationSystem) domainElement)) {
			return TransformationSystemEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static int getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		String containerModelID = org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry
				.getModelID(containerView);
		if (!TransformationSystemEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (TransformationSystemEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = TransformationSystemEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case TransformationSystemEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getRule().isSuperTypeOf(
					domainElement.eClass())) {
				return RuleEditPart.VISUAL_ID;
			}
			if (HenshinPackage.eINSTANCE.getTransformationUnit().isSuperTypeOf(
					domainElement.eClass())) {
				return UnitEditPart.VISUAL_ID;
			}
			break;
		case RuleCompartmentEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getNode().isSuperTypeOf(
					domainElement.eClass())) {
				return NodeEditPart.VISUAL_ID;
			}
			break;
		case NodeCompartmentEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getAttribute().isSuperTypeOf(
					domainElement.eClass())) {
				return AttributeEditPart.VISUAL_ID;
			}
			break;
		}
		return -1;
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, int nodeVisualID) {
		String containerModelID = org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry
				.getModelID(containerView);
		if (!TransformationSystemEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (TransformationSystemEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry
					.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = TransformationSystemEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case TransformationSystemEditPart.VISUAL_ID:
			if (RuleEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (UnitEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case RuleEditPart.VISUAL_ID:
			if (RuleNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (RuleCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case UnitEditPart.VISUAL_ID:
			if (UnitNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (UnitCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case NodeEditPart.VISUAL_ID:
			if (NodeTypeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (NodeActionEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (NodeCompartmentEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case RuleCompartmentEditPart.VISUAL_ID:
			if (NodeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case NodeCompartmentEditPart.VISUAL_ID:
			if (AttributeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case EdgeEditPart.VISUAL_ID:
			if (EdgeTypeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (EdgeActionEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static int getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return -1;
		}
		if (HenshinPackage.eINSTANCE.getEdge().isSuperTypeOf(
				domainElement.eClass())) {
			return EdgeEditPart.VISUAL_ID;
		}
		return -1;
	}

	/**
	 * User can change implementation of this method to handle some specific
	 * situations not covered by default logic.
	 * 
	 * @generated
	 */
	private static boolean isDiagram(TransformationSystem element) {
		return true;
	}

}
