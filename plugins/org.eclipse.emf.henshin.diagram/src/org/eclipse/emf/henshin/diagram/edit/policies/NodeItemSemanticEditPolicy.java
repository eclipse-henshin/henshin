/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeReorientCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.NodeDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.parts.AttributeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.EdgeEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.NodeCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.part.HenshinVisualIDRegistry;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class NodeItemSemanticEditPolicy extends HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public NodeItemSemanticEditPolicy() {
		super(HenshinElementTypes.Node_3001);
	}

	/**
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	protected Command getDestroyElementCommand(DestroyElementRequest request) {

		// Get the view first and the node:
		View view = (View) getHost().getModel();
		Node node = (Node) request.getElementToDestroy();

		// Create a composite command:
		CompositeTransactionalCommand composite = new CompositeTransactionalCommand(getEditingDomain(), null);
		composite.setTransactionNestingEnabled(false);

		// Delete coinciding edges:
		List<Edge> edges = new ArrayList<Edge>();
		edges.addAll(view.getSourceEdges());
		edges.addAll(view.getTargetEdges());
		for (Edge edge : edges) {
			if (HenshinVisualIDRegistry.getVisualID(edge) == EdgeEditPart.VISUAL_ID) {
				composite.add(new EdgeDeleteCommand(getEditingDomain(),
						(org.eclipse.emf.henshin.model.Edge) edge.getElement()));
				composite.add(new DeleteCommand(getEditingDomain(), edge));
			}
		}

		// Check if it is a shortcut:
		EAnnotation annotation = view.getEAnnotation("Shortcut");
		if (annotation == null) {
			addDestroyShortcutsCommand(composite, view);
			composite.add(new NodeDeleteCommand(getEditingDomain(), node));
		} else {
			composite.add(new DeleteCommand(getEditingDomain(), view));
		}

		// Done.
		return getGEFWrapper(composite.reduce());

	}

	/**
	 * @generated
	 */
	private void addDestroyChildNodesCommand(ICompositeCommand cmd) {
		View view = (View) getHost().getModel();
		for (Iterator<?> nit = view.getChildren().iterator(); nit.hasNext();) {
			org.eclipse.gmf.runtime.notation.Node node = (org.eclipse.gmf.runtime.notation.Node) nit.next();
			switch (HenshinVisualIDRegistry.getVisualID(node)) {
			case NodeCompartmentEditPart.VISUAL_ID:
				for (Iterator<?> cit = node.getChildren().iterator(); cit.hasNext();) {
					org.eclipse.gmf.runtime.notation.Node cnode = (org.eclipse.gmf.runtime.notation.Node) cit.next();
					switch (HenshinVisualIDRegistry.getVisualID(cnode)) {
					case AttributeEditPart.VISUAL_ID:
						cmd.add(new DestroyElementCommand(
								new DestroyElementRequest(getEditingDomain(), cnode.getElement(), false))); // directlyOwned: true
						// don't need explicit deletion of cnode as parent's view deletion would clean child views as well 
						// cmd.add(new org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand(getEditingDomain(), cnode));
						break;
					}
				}
				break;
			}
		}
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req)
				: getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (HenshinElementTypes.Edge_4001 == req.getElementType()) {
			return getGEFWrapper(new EdgeCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (HenshinElementTypes.Edge_4001 == req.getElementType()) {
			return getGEFWrapper(new EdgeCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case EdgeEditPart.VISUAL_ID:
			return getGEFWrapper(new EdgeReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
