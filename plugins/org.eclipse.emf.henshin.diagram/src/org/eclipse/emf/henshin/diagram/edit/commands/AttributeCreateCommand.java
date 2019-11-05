/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.menus.PopupMenu;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * @generated
 */
public class AttributeCreateCommand extends EditElementCommand {

	// Parent shell to be used for displaying the menu.
	protected Shell shell;

	/**
	 * @generated
	 */
	public AttributeCreateCommand(CreateElementRequest req) {
		super(req.getLabel(), null, req);
	}

	/**
	 * @generated NOT
	 */
	public AttributeCreateCommand(CreateElementRequest req, Shell shell) {
		this(req);
		this.shell = shell;
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest()).getContainer();
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
		if (node.getType() == null || getCandidateAttributes(node).isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {

		// The node and the rule:
		Node node = (Node) getElementToEdit();
		Rule rule = node.getGraph().getRule();

		List<EAttribute> attributes = getCandidateAttributes(node);
		EAttribute type = null;

		// Display the pop-up menu:
		if (shell != null && attributes.size() > 1) {
			PopupMenu menu = getPopupMenu(attributes);
			if (menu.show(shell) == false) {
				monitor.setCanceled(true);
				return CommandResult.newCancelledCommandResult();
			}
			type = (EAttribute) menu.getResult();
		} else {
			type = attributes.get(0);
		}

		// Create the attribute.
		Attribute attribute = HenshinFactory.eINSTANCE.createAttribute();
		attribute.setType(type);
		Parameter param = null;
		for (Parameter p : rule.getParameters()) {
			if (p.getType() == null || p.getType() == type.getEAttributeType()) {
				param = p;
				break;
			}
		}
		attribute.setValue(param != null ? param.getName() : String.valueOf(type.getDefaultValue()));
		node.getAttributes().add(attribute);

		// and to all mapped nodes...
		Node lhsNode = getLhsNode(node);
		if (lhsNode != null) {
			Node rhsNode = rule.getMappings().getImage(lhsNode, rule.getRhs());
			if (rhsNode != null) {
				addAttribute(rhsNode, (Attribute) EcoreUtil.copy(attribute));
			}
			for (NestedCondition ac : rule.getLhs().getNestedConditions()) {
				Node acNode = ac.getMappings().getImage(lhsNode, ac.getConclusion());
				if (acNode != null) {
					addAttribute(acNode, (Attribute) EcoreUtil.copy(attribute));
				}
			}
		}

		// Clean up:
		HenshinModelCleaner.cleanRule(rule.getRootRule());

		doConfigure(attribute, monitor, info);
		((CreateElementRequest) getRequest()).setNewElement(attribute);
		return CommandResult.newOKCommandResult(attribute);
	}

	protected List<EAttribute> getCandidateAttributes(Node node) {
		List<EAttribute> attrs = new ArrayList<EAttribute>();
		for (EAttribute attr : node.getType().getEAllAttributes()) {
			if (node.getAttribute(attr) == null) {
				attrs.add(attr);
			}
		}
		Collections.sort(attrs, new Comparator<EAttribute>() {
			@Override
			public int compare(EAttribute a1, EAttribute a2) {
				return String.valueOf(a1.getName()).compareTo(String.valueOf(a2.getName()));
			}
		});
		return attrs;
	}

	/**
	 * Create a pop-up menu for choosing the unit type.
	 * @return Pop-up menu instance.
	 * @generated NOT
	 */
	protected PopupMenu getPopupMenu(List<EAttribute> attributes) {
		ILabelProvider labelProvider = new org.eclipse.jface.viewers.LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return HenshinElementTypes.getImage(HenshinElementTypes.Attribute_3002);
			}

			@Override
			public String getText(Object element) {
				return ((EAttribute) element).getName();
			}
		};
		return new PopupMenu(attributes, labelProvider);
	}

	/**
	 * @generated NOT
	 */
	protected void addAttribute(Node node, Attribute attribute) {
		Attribute old = node.getAttribute(attribute.getType());
		if (old != null) {
			node.getAttributes().set(node.getAttributes().indexOf(old), attribute);
		} else {
			node.getAttributes().add(attribute);
		}
	}

	/*
	 * For an arbitrary node in a rule graph, find the corresponding Lhs node.
	 */
	public Node getLhsNode(Node node) {
		Graph lhs = node.getGraph().getRule().getLhs();
		if (node.getGraph() == lhs) {
			return node;
		}
		Node opposite = node.getActionNode();
		if (opposite.getGraph() == lhs) {
			return opposite;
		}
		// No corresponding Lhs node:
		return null;
	}

	/**
	 * @generated
	 */
	protected void doConfigure(Attribute newElement, IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest()).getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest()).getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
