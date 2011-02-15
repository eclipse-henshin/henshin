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
package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.actions.Action;
import org.eclipse.emf.henshin.diagram.edit.actions.ActionType;
import org.eclipse.emf.henshin.diagram.edit.actions.NodeActionHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.maps.NodeMapEditor;
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
import org.eclipse.gmf.runtime.notation.View;

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
		return HenshinBaseItemSemanticEditPolicy.getLinkConstraints()
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
			// Check the type as well:
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

		// Get the source and target type.
		EClass targetType = target.getType();
		EClass sourceType = source.getType();

		// Everything must be set.
		if (source == null || target == null || sourceType == null
				|| targetType == null || edgeType == null) {
			return false;
		}

		// Reference must be owned by source.
		if (!sourceType.getEAllReferences().contains(edgeType)) {
			return false;
		}

		// Target type must be ok:
		if (!edgeType.getEReferenceType().isSuperTypeOf(targetType)) {
			return false;
		}

		// Check for source/target consistency.
		Graph sourceGraph = source.getGraph();
		Graph targetGraph = target.getGraph();

		// Make sure the rule is found and that it is the same:
		if (sourceGraph.getContainerRule() == null
				|| sourceGraph.getContainerRule() != targetGraph
						.getContainerRule()) {
			return false;
		}

		// Get the node actions:
		Action action1 = NodeActionHelper.INSTANCE.getAction(source);
		Action action2 = NodeActionHelper.INSTANCE.getAction(target);

		// Sanity check:
		if (action1==null || action2==null) {
			return false;
		}
		
		// Different actions are only allowed if one is a preserve action:
		if (!action1.equals(action2)
				&& action1.getType() != ActionType.PRESERVE
				&& action2.getType() != ActionType.PRESERVE) {
			return false;
		}

		// Ok.
		return true;

	}

	/**
	 * @generated NOT
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		// Check again whether we can really execute this command:
		if (!canExecute()) {
			throw new ExecutionException(
					"Invalid arguments in create link command"); //$NON-NLS-1$
		}

		// Get the type parameter and the rule:
		EReference type = (EReference) getRequest().getParameter(
				TYPE_PARAMETER_KEY);
		Rule rule = getSource().getGraph().getContainerRule();

		// Source, target and the edge to be created:
		Node source = getSource();
		Node target = getTarget();
		Edge edge = null;

		// Get the node actions:
		Action srcAction = NodeActionHelper.INSTANCE.getAction(source);
		Action trgAction = NodeActionHelper.INSTANCE.getAction(target);

		// Check if the actions are the same:
		if (srcAction.equals(trgAction)) {

			// Create the new edge (we know the nodes are in the same graph):
			edge = HenshinFactory.eINSTANCE.createEdge(source, target, type);

			// For PRESERVE actions we need to create an image in the RHS as well:
			if (srcAction.getType() == ActionType.PRESERVE) {
				Node srcImage = HenshinMappingUtil.getNodeImage(getSource(),
						rule.getRhs(), rule.getMappings());
				Node trgImage = HenshinMappingUtil.getNodeImage(getTarget(),
						rule.getRhs(), rule.getMappings());
				HenshinFactory.eINSTANCE.createEdge(srcImage, trgImage, type);
			}

		} else {

			/* 
			 * We know one of the action is of type PRESERVE, the other one is not.
			 * We look for the image of the PRESERVE node and use it to create the edge.
			 * If the image does not exist yet (for a NAC for instance), we copy the node.
			 */
			if (srcAction.getType() == ActionType.PRESERVE) {
				if (trgAction.getType() == ActionType.CREATE
						|| trgAction.getType() == ActionType.FORBID) {
					source = new NodeMapEditor(target.getGraph()).copy(source);
				}
			} else {
				if (srcAction.getType() == ActionType.CREATE
						|| srcAction.getType() == ActionType.FORBID) {
					target = new NodeMapEditor(source.getGraph()).copy(target);
				}
			}

			// Now we can safely create the edge:
			edge = HenshinFactory.eINSTANCE.createEdge(source, target, type);

		}

		// Update the root containment is the edge is containment / container:
		if (type.isContainment() || type.isContainer()) {
			View ruleView = RootObjectEditHelper.findRuleView(rule);
			RootObjectEditHelper.updateRootContainment(ruleView, source);
			RootObjectEditHelper.updateRootContainment(ruleView, target);
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
