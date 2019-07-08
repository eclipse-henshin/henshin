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
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeConditionEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.RuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitEditPart;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.update.DiagramUpdater;

/**
 * @generated
 */
public class HenshinDiagramUpdater {

	/**
	 * @generated
	 */
	public static List<HenshinNodeDescriptor> getSemanticChildren(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case ModuleEditPart.VISUAL_ID:
			return getModule_1000SemanticChildren(view);
		case RuleCompartmentEditPart.VISUAL_ID:
			return getRuleRuleCompartment_7001SemanticChildren(view);
		case NodeCompartmentEditPart.VISUAL_ID:
			return getNodeNodeCompartment_7002SemanticChildren(view);
		case UnitCompartmentEditPart.VISUAL_ID:
			return getUnitUnitCompartment_7003SemanticChildren(view);
		}
		return Collections.emptyList();
	}

	/**
	 * Get the semantic children of a rule compartment.
	 * @generated NOT
	 */
	public static List<HenshinNodeDescriptor> getRuleRuleCompartment_7001SemanticChildren(View view) {

		// Check the container:
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}

		// Collect all action nodes:
		Rule rule = (Rule) containerView.getElement();
		List<Node> actionNodes = new ArrayList<Node>(rule.getActionNodes(null));

		// Check if we should exclude a root object:
		Node root = RootObjectEditHelper.getRootObject(containerView);
		if (root != null) {
			actionNodes.remove(root);
		}

		// Wrap them into node descriptors:
		List<HenshinNodeDescriptor> result = new LinkedList<HenshinNodeDescriptor>();
		for (Node node : actionNodes) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view, node);
			if (visualID == NodeEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(node, visualID));
				continue;
			}
		}

		// Get attribute conditions:
		for (AttributeCondition condition : rule.getAttributeConditions()) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view, condition);
			if (visualID == AttributeConditionEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(condition, visualID));
				continue;
			}
		}

		// Done.
		return result;
	}

	/**
	 * Get the semantic children of a node compartment.
	 * @generated NOT
	 */
	public static List<HenshinNodeDescriptor> getNodeNodeCompartment_7002SemanticChildren(View view) {

		// Make sure the view is ok:
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}

		// Get the node and compute the action attributes:
		Node node = (Node) containerView.getElement();
		List<Attribute> attributes = node.getActionAttributes(null);

		// Wrap them into node descriptors:
		List<HenshinNodeDescriptor> result = new LinkedList<HenshinNodeDescriptor>();
		for (Attribute attribute : attributes) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view, attribute);
			if (visualID == AttributeEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(attribute, visualID));
				continue;
			}
		}

		// Done.
		return result;
	}

	/**
	 * Get the semantic children of a unit compartment.
	 * @generated NOT
	 */
	public static List<HenshinNodeDescriptor> getUnitUnitCompartment_7003SemanticChildren(View view) {

		// Make sure the container view is set:
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}

		// Collect all invocations:
		Unit unit = (Unit) containerView.getElement();
		LinkedList<HenshinNodeDescriptor> result = new LinkedList<HenshinNodeDescriptor>();

		// All subUnits get an invocation view, and we added the required symbol views as well.
		for (Unit subUnit : unit.getSubUnits(false)) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view, subUnit);
			if (visualID == InvocationEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(subUnit, visualID));
			}
		}

		//Done.
		return result;
	}

	/**
	 * Get the semantic children of a module diagram.
	 * @generated NOT
	 */
	public static List<HenshinNodeDescriptor> getModule_1000SemanticChildren(View view) {

		// Make sure the view is ok:
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}

		// Get the module:
		Module module = (Module) view.getElement();
		LinkedList<HenshinNodeDescriptor> result = new LinkedList<HenshinNodeDescriptor>();

		// Iterate over all units:
		for (Unit unit : module.getUnits()) {
			int visualID = HenshinVisualIDRegistry.getNodeVisualID(view, unit);
			if (visualID == UnitEditPart.VISUAL_ID || visualID == RuleEditPart.VISUAL_ID) {
				result.add(new HenshinNodeDescriptor(unit, visualID));
			}
		}

		// Done.
		return result;
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getContainedLinks(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case ModuleEditPart.VISUAL_ID:
			return getModule_1000ContainedLinks(view);
		case RuleEditPart.VISUAL_ID:
			return getRule_2001ContainedLinks(view);
		case UnitEditPart.VISUAL_ID:
			return getUnit_2002ContainedLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_3001ContainedLinks(view);
		case AttributeEditPart.VISUAL_ID:
			return getAttribute_3002ContainedLinks(view);
		case AttributeConditionEditPart.VISUAL_ID:
			return getAttributeCondition_3005ContainedLinks(view);
		case InvocationEditPart.VISUAL_ID:
			return getUnit_3003ContainedLinks(view);
		case EdgeEditPart.VISUAL_ID:
			return getEdge_4001ContainedLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getIncomingLinks(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case RuleEditPart.VISUAL_ID:
			return getRule_2001IncomingLinks(view);
		case UnitEditPart.VISUAL_ID:
			return getUnit_2002IncomingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_3001IncomingLinks(view);
		case AttributeEditPart.VISUAL_ID:
			return getAttribute_3002IncomingLinks(view);
		case AttributeConditionEditPart.VISUAL_ID:
			return getAttributeCondition_3005IncomingLinks(view);
		case InvocationEditPart.VISUAL_ID:
			return getUnit_3003IncomingLinks(view);
		case EdgeEditPart.VISUAL_ID:
			return getEdge_4001IncomingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getOutgoingLinks(View view) {
		switch (HenshinVisualIDRegistry.getVisualID(view)) {
		case RuleEditPart.VISUAL_ID:
			return getRule_2001OutgoingLinks(view);
		case UnitEditPart.VISUAL_ID:
			return getUnit_2002OutgoingLinks(view);
		case NodeEditPart.VISUAL_ID:
			return getNode_3001OutgoingLinks(view);
		case AttributeEditPart.VISUAL_ID:
			return getAttribute_3002OutgoingLinks(view);
		case AttributeConditionEditPart.VISUAL_ID:
			return getAttributeCondition_3005OutgoingLinks(view);
		case InvocationEditPart.VISUAL_ID:
			return getUnit_3003OutgoingLinks(view);
		case EdgeEditPart.VISUAL_ID:
			return getEdge_4001OutgoingLinks(view);
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getModule_1000ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * Get the edges in a rule.
	 * @generated NOT
	 */
	public static List<HenshinLinkDescriptor> getRule_2001ContainedLinks(View view) {

		// Get the action edges:
		Rule rule = (Rule) view.getElement();
		List<Edge> edges = rule.getActionEdges(null);

		// Check if we should exclude a root object:
		Node root = RootObjectEditHelper.getRootObject(view);

		// Wrap them into link descriptors:
		List<HenshinLinkDescriptor> result = new ArrayList<HenshinLinkDescriptor>();
		for (Edge edge : edges) {

			// Must be a valid edge:
			if (edge.getSource() == null || edge.getTarget() == null) {
				continue;
			}

			// Get the proper source / target action nodes:
			Node source = edge.getSource().getActionNode();
			Node target = edge.getTarget().getActionNode();

			// Create the descriptor if the edge does not link to the root:
			if (source != root && target != root) {
				result.add(new HenshinLinkDescriptor(source, target, edge, HenshinElementTypes.Edge_4001,
						EdgeEditPart.VISUAL_ID));
			}
		}

		// Done.
		return result;
	}

	/**
	 * @generated NOT
	 */
	public static List<HenshinLinkDescriptor> getUnit_2002ContainedLinks(View view) {

		// Ideally, we want to return a list of all expected links
		// inside the unit view here. Since this is not really nice
		// to implement, we update the links only when something
		// changed or when the unit view is being created.
		return Collections.emptyList();

	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getNode_3001ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getAttribute_3002ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getAttributeCondition_3005ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getUnit_3003ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getEdge_4001ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getRule_2001IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getUnit_2002IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getNode_3001IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences = EcoreUtil.CrossReferencer
				.find(view.eResource().getResourceSet().getResources());
		LinkedList<HenshinLinkDescriptor> result = new LinkedList<HenshinLinkDescriptor>();
		result.addAll(getIncomingTypeModelFacetLinks_Edge_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getAttribute_3002IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getAttributeCondition_3005IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getUnit_3003IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getNode_3001OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<HenshinLinkDescriptor> result = new LinkedList<HenshinLinkDescriptor>();
		result.addAll(getOutgoingTypeModelFacetLinks_Edge_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getAttribute_3002OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getAttributeCondition_3005OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getUnit_3003OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getEdge_4001IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getRule_2001OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getUnit_2002OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public static List<HenshinLinkDescriptor> getEdge_4001OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	private static Collection<HenshinLinkDescriptor> getIncomingTypeModelFacetLinks_Edge_4001(Node target,
			Map<EObject, Collection<EStructuralFeature.Setting>> crossReferences) {
		LinkedList<HenshinLinkDescriptor> result = new LinkedList<HenshinLinkDescriptor>();
		Collection<EStructuralFeature.Setting> settings = crossReferences.get(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != HenshinPackage.eINSTANCE.getEdge_Target()
					|| false == setting.getEObject() instanceof Edge) {
				continue;
			}
			Edge link = (Edge) setting.getEObject();
			if (EdgeEditPart.VISUAL_ID != HenshinVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			Node src = link.getSource();
			result.add(new HenshinLinkDescriptor(src, target, link, HenshinElementTypes.Edge_4001,
					EdgeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection<HenshinLinkDescriptor> getOutgoingTypeModelFacetLinks_Edge_4001(Node source) {
		Graph container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Graph) {
				container = (Graph) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<HenshinLinkDescriptor> result = new LinkedList<HenshinLinkDescriptor>();
		for (Iterator<?> links = container.getEdges().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Edge) {
				continue;
			}
			Edge link = (Edge) linkObject;
			if (EdgeEditPart.VISUAL_ID != HenshinVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			Node dst = link.getTarget();
			Node src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(
					new HenshinLinkDescriptor(src, dst, link, HenshinElementTypes.Edge_4001, EdgeEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static final DiagramUpdater TYPED_INSTANCE = new DiagramUpdater() {
		/**
		* @generated
		*/
		@Override

		public List<HenshinNodeDescriptor> getSemanticChildren(View view) {
			return HenshinDiagramUpdater.getSemanticChildren(view);
		}

		/**
		* @generated
		*/
		@Override

		public List<HenshinLinkDescriptor> getContainedLinks(View view) {
			return HenshinDiagramUpdater.getContainedLinks(view);
		}

		/**
		* @generated
		*/
		@Override

		public List<HenshinLinkDescriptor> getIncomingLinks(View view) {
			return HenshinDiagramUpdater.getIncomingLinks(view);
		}

		/**
		* @generated
		*/
		@Override

		public List<HenshinLinkDescriptor> getOutgoingLinks(View view) {
			return HenshinDiagramUpdater.getOutgoingLinks(view);
		}
	};

}
