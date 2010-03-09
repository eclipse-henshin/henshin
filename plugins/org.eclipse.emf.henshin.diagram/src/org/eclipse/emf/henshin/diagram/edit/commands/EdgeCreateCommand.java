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
package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.policies.HenshinBaseItemSemanticEditPolicy;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

/**
 * @generated
 */
public class EdgeCreateCommand extends EditElementCommand {

	/**
	 * Key for the node type parameter in creation requests.
	 * @generated NOT
	 */
	public static final String TYPE_PARAMETER_KEY = "henshin_edge_type";

	/**
	 * @generated
	 */
	private final EObject source;

	/**
	 * @generated
	 */
	private final EObject target;

	/**
	 * @generated
	 */
	private final Graph container;

	/**
	 * @generated
	 */
	public EdgeCreateCommand(CreateRelationshipRequest request, EObject source,
			EObject target) {
		super(request.getLabel(), null, request);
		this.source = source;
		this.target = target;
		container = deduceContainer(source, target);
	}

	/**
	 * @generated
	 */
	public boolean canExecuteGen() {
		if (source == null && target == null) {
			return false;
		}
		if (source != null && false == source instanceof Node) {
			return false;
		}
		if (target != null && false == target instanceof Node) {
			return false;
		}
		if (getSource() == null) {
			return true; // link creation is in progress; source is not defined yet
		}
		// target may be null here but it's possible to check constraint
		if (getContainer() == null) {
			return false;
		}
		return HenshinBaseItemSemanticEditPolicy.LinkConstraints
				.canCreateEdge_4001(getContainer(), getSource(), getTarget());
	}

	/**
	 * @generated NOT
	 */
	public boolean canExecute() {
		if (!canExecuteGen()) {
			return false;
		}
		if (source != null && target != null) {
			EReference type = (EReference) getRequest().getParameter(
					TYPE_PARAMETER_KEY);
			return canCreateEdge(getSource(), getTarget(), type);
		}
		return true;
	}

	/**
	 * Check if an edge can be created.
	 * @generated NOT
	 */
	public static boolean canCreateEdge(Node source, Node target,
			EReference edgeType) {

		EClass targetType = target.getType();
		EClass sourceType = source.getType();

		// Everything must be set.
		if (source == null || target == null || sourceType == null
				|| targetType == null || edgeType == null) {
			return false;
		}

		// Reference must be owned by source.
		if (!sourceType.getEReferences().contains(edgeType)) {
			return false;
		}

		// Target type must be ok:
		if (!targetType.isSuperTypeOf(edgeType.getEReferenceType())) {
			return false;
		}

		/*
		 * Check for source/target consistency i.e. an edge between [delete]node
		 * and [create]node is forbidden
		 */
		Graph sourceGraph = source.getGraph();
		Graph targetGraph = target.getGraph();
		Rule rule = sourceGraph.getContainerRule();

		if (rule == null) {
			// if no rule is given, we require same graphs at least
			if (sourceGraph != targetGraph) {
				return false;
			}// if

		} else {

			if (isCreate(rule, source) && isDelete(rule, target)) {
				return false;
			}// if

			if (isDelete(rule, source) && isCreate(rule, target)) {
				return false;
			}// if

			//TODO: Appropriate edge creation depending on <<create>>/<<delete>> nodes

		}// if else

		return true;
	}// canCreateEdge

	/**
	 * Return true if the given element indicates a deletion by rule application
	 * i.e. the given node occurs only at the LHS without mapping to RHS.
	 * 
	 * TODO: Diese Methode sollten noch in die Modellklasse Rule
	 * 
	 * @param rule
	 * @param node
	 * @return
	 */
	private static boolean isDelete(Rule rule, Node node) {

		if ((rule.getLhs() == node.getGraph())) {
			Node sourceImage = HenshinMappingUtil.getNodeImage(node, rule
					.getRhs(), rule.getMappings());
			return sourceImage == null;
		}// if 
		return false;
	}// isDelete

	/**
	 * Return true if the given element indicates a creation by rule application
	 * i.e. the given node occurs only at the RHS without being mapped by LHS.
	 * 
	 * TODO: Diese Methode sollten noch in die Modellklasse Rule
	 * @param rule
	 * @param node
	 * @return
	 */
	private static boolean isCreate(Rule rule, Node node) {

		if ((rule.getRhs() == node.getGraph())) {
			Node sourceImage = HenshinMappingUtil.getNodeOrigin(node, rule
					.getMappings());
			return sourceImage == null;
		}// if 
		return false;
	}// isCreate

	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}

		// Get the edge type:
		EReference type = (EReference) getRequest().getParameter(
				TYPE_PARAMETER_KEY);

		// Create the new edge:
		Rule rule = getSource().getGraph().getContainerRule();
		Edge edge = HenshinFactory.eINSTANCE.createEdge(getSource(),
				getTarget(), type);

		// Check if we need to create a copy in the RHS:
		Node sourceImage = HenshinMappingUtil.getNodeImage(getSource(), rule
				.getRhs(), rule.getMappings());
		Node targetImage = HenshinMappingUtil.getNodeImage(getTarget(), rule
				.getRhs(), rule.getMappings());

		if (sourceImage != null && targetImage != null) {
			HenshinFactory.eINSTANCE.createEdge(sourceImage, targetImage, type);
		}

		// Configure and return:
		doConfigure(edge, monitor, info);
		((CreateElementRequest) getRequest()).setNewElement(edge);
		return CommandResult.newOKCommandResult(edge);

	}

	/**
	 * @generated
	 */
	protected void doConfigure(Edge newElement, IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		configureRequest.setParameter(CreateRelationshipRequest.SOURCE,
				getSource());
		configureRequest.setParameter(CreateRelationshipRequest.TARGET,
				getTarget());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

	/**
	 * @generated
	 */
	protected void setElementToEdit(EObject element) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @generated
	 */
	protected Node getSource() {
		return (Node) source;
	}

	/**
	 * @generated
	 */
	protected Node getTarget() {
		return (Node) target;
	}

	/**
	 * @generated
	 */
	public Graph getContainer() {
		return container;
	}

	/**
	 * Default approach is to traverse ancestors of the source to find instance of container.
	 * Modify with appropriate logic.
	 * @generated
	 */
	private static Graph deduceContainer(EObject source, EObject target) {
		// Find container element for the new link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null; element = element
				.eContainer()) {
			if (element instanceof Graph) {
				return (Graph) element;
			}
		}
		return null;
	}

}
