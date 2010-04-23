/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.part;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.diagram.actions.AttributeActionUtil;
import org.eclipse.emf.henshin.diagram.actions.EdgeActionUtil;
import org.eclipse.emf.henshin.diagram.actions.NodeActionUtil;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.TransformationSystemEditPart;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class HenshinDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case RuleCompartmentEditPart.VISUAL_ID:
			return getRuleRuleCompartment_7001SemanticChildren(view);
		case NodeCompartmentEditPart.VISUAL_ID:
			return getNodeNodeCompartment_7002SemanticChildren(view);
		case TransformationSystemEditPart.VISUAL_ID:
			return getTransformationSystem_1000SemanticChildren(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public static List<?> getRuleRuleCompartment_7001SemanticChildren(View view) {

		// Check the container:
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}

		// Collect all action nodes:
		Rule rule = (Rule) containerView.getElement();
		List<Node> actionNodes = NodeActionUtil.getActionNodes(rule, null);

		// Wrap them into node descriptors:
		List<HenshinNodeDescriptor> result = new LinkedList<HenshinNodeDescriptor>();
		for (Node node : actionNodes) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view, node);
			if (visualID == NodeEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(node, visualID));
				continue;
			}
		}

		// Done.
		return result;

	}

	/**
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public static List getNodeNodeCompartment_7002SemanticChildren(View view) {
		
		// Make sure the view is ok:
		if (false==view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		
		// Get the node and compute the action attributes:
		Node node = (Node) containerView.getElement();
		List<Attribute> attributes = AttributeActionUtil.getActionAttributes(node, null);
				
		// Wrap them into node descriptors:
		List result = new LinkedList();
		for (Attribute attribute : attributes) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view,attribute);
			if (visualID==AttributeEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(attribute, visualID));
				continue;
			}
		}
		
		return result;
	}

	/**
	 * @generated
	 */
	public static List getTransformationSystem_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		TransformationSystem modelElement = (TransformationSystem) view
				.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getRules().iterator(); it.hasNext();) {
			Rule childElement = (Rule) it.next();
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view,
					childElement);
			if (visualID == RuleEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case TransformationSystemEditPart.VISUAL_ID:
			return getTransformationSystem_1000ContainedLinks(view);
		case RuleEditPart.VISUAL_ID:
			return getRule_2001ContainedLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_3001ContainedLinks(view);
		case AttributeEditPart.VISUAL_ID:
			return getAttribute_3002ContainedLinks(view);
		case EdgeEditPart.VISUAL_ID:
			return getEdge_4001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case RuleEditPart.VISUAL_ID:
			return getRule_2001IncomingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_3001IncomingLinks(view);
		case AttributeEditPart.VISUAL_ID:
			return getAttribute_3002IncomingLinks(view);
		case EdgeEditPart.VISUAL_ID:
			return getEdge_4001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case RuleEditPart.VISUAL_ID:
			return getRule_2001OutgoingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_3001OutgoingLinks(view);
		case AttributeEditPart.VISUAL_ID:
			return getAttribute_3002OutgoingLinks(view);
		case EdgeEditPart.VISUAL_ID:
			return getEdge_4001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getTransformationSystem_1000ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated NOT
	 */
	public static List<HenshinLinkDescriptor> getRule_2001ContainedLinks(
			View view) {

		// Get the action edges:
		Rule rule = (Rule) view.getElement();
		List<Edge> edges = EdgeActionUtil.getActionEdges(rule, null);

		// Wrap them into node descriptors:
		List<HenshinLinkDescriptor> result = new ArrayList<HenshinLinkDescriptor>();
		for (Edge edge : edges) {

			// Get the proper source / target action nodes:
			Node source = NodeActionUtil.getActionNode(edge.getSource());
			Node target = NodeActionUtil.getActionNode(edge.getTarget());

			// Create the descriptor:
			result.add(new HenshinLinkDescriptor(source, target, edge,
					HenshinElementTypes.Edge_4001, EdgeEditPart.VISUAL_ID));
		}

		// Done.
		return result;

	}

	/**
	 * @generated
	 */
	public static List getNode_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getAttribute_3002ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEdge_4001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRule_2001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNode_3001IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource()
				.getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Edge_4001(modelElement,
				crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getAttribute_3002IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getNode_3001OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Edge_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getAttribute_3002OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEdge_4001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRule_2001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getEdge_4001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_Edge_4001(
			Node target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it
					.next();
			if (setting.getEStructuralFeature() != HenshinPackage.eINSTANCE
					.getEdge_Target()
					|| false == setting.getEObject() instanceof Edge) {
				continue;
			}
			Edge link = (Edge) setting.getEObject();
			if (EdgeEditPart.VISUAL_ID != HenshinVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Node src = link.getSource();
			result.add(new HenshinLinkDescriptor(src, target, link,
					HenshinElementTypes.Edge_4001, EdgeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_Edge_4001(
			Node source) {
		Graph container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element
				.eContainer()) {
			if (element instanceof Graph) {
				container = (Graph) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getEdges().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Edge) {
				continue;
			}
			Edge link = (Edge) linkObject;
			if (EdgeEditPart.VISUAL_ID != HenshinVisualIDRegistry
					.getLinkWithClassVisualID(link)) {
				continue;
			}
			Node dst = link.getTarget();
			Node src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new HenshinLinkDescriptor(src, dst, link,
					HenshinElementTypes.Edge_4001, EdgeEditPart.VISUAL_ID));
		}
		return result;
	}

}
