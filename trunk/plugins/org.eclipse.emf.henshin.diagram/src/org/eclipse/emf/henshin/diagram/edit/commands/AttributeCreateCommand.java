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
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.HenshinActionHelper;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinACUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class AttributeCreateCommand extends EditElementCommand {

	/**
	 * @generated
	 */
	public AttributeCreateCommand(CreateElementRequest req) {
		super(req.getLabel(), null, req);
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated NOT
	 */
	public boolean canExecute() {
		Node node = (Node) getElementToEdit();
		// The type must be set and there must be at least one attribute type.
		if (node.getType() == null
				|| node.getType().getEAllAttributes().isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {

		// The node:
		Node node = (Node) getElementToEdit();
		Rule rule = node.getGraph().getContainerRule();

		// Find the corresponding LHS node:
		Node lhsNode = HenshinActionHelper.getLhsNode(node);

		// Create the attribute.
		Attribute attribute = HenshinFactory.eINSTANCE.createAttribute();

		// Initialize the attribute type and value (guessing):
		if (node.getType() != null) {
			for (EAttribute type : node.getType().getEAllAttributes()) {
				if (node.findAttributeByType(type) == null) {
					attribute.setType(type);
					attribute.setValue(String.valueOf(type.getDefaultValue()));
					break;
				}
			}
		}

		// Add the attribute to the node:
		node.getAttributes().add(attribute);

		// and to all mapped nodes...
		if (lhsNode != null) {
			Node rhsNode = HenshinMappingUtil.getNodeImage(lhsNode,
					rule.getRhs(), rule.getMappings());
			if (rhsNode != null) {
				addAttribute(rhsNode, (Attribute) EcoreUtil.copy(attribute));
			}
			for (NestedCondition ac : HenshinACUtil.getAllACs(rule)) {
				Node acNode = HenshinMappingUtil.getNodeImage(lhsNode,
						ac.getConclusion(), ac.getMappings());
				if (acNode != null) {
					addAttribute(acNode, (Attribute) EcoreUtil.copy(attribute));
				}
			}
		}

		doConfigure(attribute, monitor, info);

		((CreateElementRequest) getRequest()).setNewElement(attribute);
		return CommandResult.newOKCommandResult(attribute);
	}

	private void addAttribute(Node node, Attribute attribute) {
		Attribute old = node.findAttributeByType(attribute.getType());
		if (old != null) {
			node.getAttributes().set(node.getAttributes().indexOf(old),
					attribute);
		} else {
			node.getAttributes().add(attribute);
		}
	}

	/**
	 * @generated
	 */
	protected void doConfigure(Attribute newElement, IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
