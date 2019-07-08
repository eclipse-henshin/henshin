/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.part;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeConditionBodyEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeConditionEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeConditionNameEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeActionEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeTypeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.InvocationNameEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeActionEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeTypeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleNameEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.SymbolEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitNameEditPart;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.structure.DiagramStructure;

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
			if (ModuleEditPart.MODEL_ID.equals(view.getType())) {
				return ModuleEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.getVisualID(view.getType());
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
			if (Boolean.TRUE.toString().equalsIgnoreCase(Platform.getDebugOption(DEBUG_KEY))) {
				HenshinDiagramEditorPlugin.getInstance()
						.logError("Unable to parse view type as a visualID number: " + type);
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
		if (HenshinPackage.eINSTANCE.getModule().isSuperTypeOf(domainElement.eClass())
				&& isDiagram((Module) domainElement)) {
			return ModuleEditPart.VISUAL_ID;
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
		if (!ModuleEditPart.MODEL_ID.equals(containerModelID)) {
			return -1;
		}
		int containerVisualID;
		if (ModuleEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ModuleEditPart.VISUAL_ID;
			} else {
				return -1;
			}
		}
		switch (containerVisualID) {
		case ModuleEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getRule().isSuperTypeOf(domainElement.eClass())) {
				return RuleEditPart.VISUAL_ID;
			}
			if (HenshinPackage.eINSTANCE.getUnit().isSuperTypeOf(domainElement.eClass())) {
				return UnitEditPart.VISUAL_ID;
			}
			break;
		case RuleCompartmentEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getNode().isSuperTypeOf(domainElement.eClass())) {
				return NodeEditPart.VISUAL_ID;
			}
			if (HenshinPackage.eINSTANCE.getAttributeCondition().isSuperTypeOf(domainElement.eClass())) {
				return AttributeConditionEditPart.VISUAL_ID;
			}
			break;
		case NodeCompartmentEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getAttribute().isSuperTypeOf(domainElement.eClass())) {
				return AttributeEditPart.VISUAL_ID;
			}
			break;
		case UnitCompartmentEditPart.VISUAL_ID:
			if (HenshinPackage.eINSTANCE.getUnit().isSuperTypeOf(domainElement.eClass())) {
				return InvocationEditPart.VISUAL_ID;
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
		if (!ModuleEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		int containerVisualID;
		if (ModuleEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = ModuleEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		switch (containerVisualID) {
		case ModuleEditPart.VISUAL_ID:
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
		case AttributeConditionEditPart.VISUAL_ID:
			if (AttributeConditionNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AttributeConditionBodyEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case InvocationEditPart.VISUAL_ID:
			if (InvocationNameEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case RuleCompartmentEditPart.VISUAL_ID:
			if (NodeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (AttributeConditionEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case NodeCompartmentEditPart.VISUAL_ID:
			if (AttributeEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			break;
		case UnitCompartmentEditPart.VISUAL_ID:
			if (SymbolEditPart.VISUAL_ID == nodeVisualID) {
				return true;
			}
			if (InvocationEditPart.VISUAL_ID == nodeVisualID) {
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
		if (HenshinPackage.eINSTANCE.getEdge().isSuperTypeOf(domainElement.eClass())) {
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
	private static boolean isDiagram(Module element) {
		return true;
	}

	/**
	 * @generated
	 */
	public static boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate) {
		if (candidate == -1) {
			//unrecognized id is always bad
			return false;
		}
		int basic = getNodeVisualID(containerView, domainElement);
		return basic == candidate;
	}

	/**
	 * @generated
	 */
	public static boolean isCompartmentVisualID(int visualID) {
		switch (visualID) {
		case RuleCompartmentEditPart.VISUAL_ID:
		case NodeCompartmentEditPart.VISUAL_ID:
		case UnitCompartmentEditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static boolean isSemanticLeafVisualID(int visualID) {
		switch (visualID) {
		case ModuleEditPart.VISUAL_ID:
			return false;
		case AttributeEditPart.VISUAL_ID:
		case InvocationEditPart.VISUAL_ID:
		case SymbolEditPart.VISUAL_ID:
		case AttributeConditionEditPart.VISUAL_ID:
			return true;
		default:
			break;
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static final DiagramStructure TYPED_INSTANCE = new DiagramStructure() {
		/**
		* @generated
		*/
		@Override

		public int getVisualID(View view) {
			return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.getVisualID(view);
		}

		/**
		* @generated
		*/
		@Override

		public String getModelID(View view) {
			return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.getModelID(view);
		}

		/**
		* @generated
		*/
		@Override

		public int getNodeVisualID(View containerView, EObject domainElement) {
			return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.getNodeVisualID(containerView,
					domainElement);
		}

		/**
		* @generated
		*/
		@Override

		public boolean checkNodeVisualID(View containerView, EObject domainElement, int candidate) {
			return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.checkNodeVisualID(containerView,
					domainElement, candidate);
		}

		/**
		* @generated
		*/
		@Override

		public boolean isCompartmentVisualID(int visualID) {
			return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		* @generated
		*/
		@Override

		public boolean isSemanticLeafVisualID(int visualID) {
			return org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};

}
