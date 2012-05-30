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
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.helpers.TransformationSystemEditHelper;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.ActionType;
import org.eclipse.emf.henshin.model.actions.HenshinActionHelper;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * Class for parsing, processing and formatting the input of node name labels.
 * This actually comprises the node's typing and parameter information.
 * 
 * @generated NOT
 * @author Christian Krause
 * @author Stefan Jurack (sjurack)
 */
public class NodeTypeParser extends AbstractParser {
	
	/**
	 * Default constructor.
	 */
	public NodeTypeParser() {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString
	 * (org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		
		// Get the node and its name:
		Node node = (Node) element.getAdapter(EObject.class);
		String name = node.getName()!=null ? node.getName().trim() : "";
		
		// For <<preserve>>-nodes we offer an arrow notation:
		Action action = HenshinActionHelper.getAction(node);
		if (action!=null && action.getType()==ActionType.PRESERVE) {
			
			// Get the RHS-node and its name:
			Rule rule = node.getGraph().getContainerRule();
			Node rhsNode = HenshinMappingUtil.getNodeImage(node, rule.getRhs(), rule.getMappings());
			String rhsName = rhsNode.getName()!=null ? rhsNode.getName().trim() : "";
			
			// Adapt the label:
			if (!name.equals(rhsName)) {
				if (name.length()==0) {
					name = "?";
				}
				if (rhsName.length()==0) {
					rhsName = "?";
				}
				name = name + "->" + rhsName;
			}
		}
		
		// Append the type, if available:
		if (node.getType() != null) {
			return name + ":" + node.getType().getName();
		} else {
			return name;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString
	 * (org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand
	 * (org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the node:
		final Node node = (Node) element.getAdapter(EObject.class);
		
		// Get the editing domain:
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(node);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain,
				"Parse Node Name", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				return doParsing(value, node);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a node label value and deduce type and parameter information.
	 */
	private CommandResult doParsing(String value, Node node) {

		// We need the rule and the transformation system:
		Rule rule = node.getGraph().getContainerRule();
		TransformationSystem system = rule.getTransformationSystem();

		// Separate name and type:
		String name, type;
		int colon = value.indexOf(":");
		if (colon>=0) {
			name = value.substring(0, colon).trim();
			type = value.substring(colon+1).trim();
		} else {
			name = value.trim();
			type = "";
		}
		
		// Check if the name contains an arrow:
		int arrow = name.indexOf("->");
		String name2 = name;
		if (arrow>=0) {
			name2 = name.substring(arrow+2).trim();
			name = name.substring(0, arrow).trim();
		}
		
		// Find the node type:
		EClass nodeType = null;
		EClassifier[] classifiers = TransformationSystemEditHelper.findEClassifierByName(system, type);
		for (EClassifier classifier : classifiers) {
			if (classifier instanceof EClass) {
				nodeType = (EClass) classifier;
				break;
			}
		}
		
		// Set the name and the type of the secondary node:
		Action action = HenshinActionHelper.getAction(node);
		if (action!=null && action.getType()==ActionType.PRESERVE) { 
			Node rhsNode = HenshinMappingUtil.getNodeImage(node, rule.getRhs(), rule.getMappings());
			if (rhsNode!=null) {
				if (name2.length()==0 || name2.equals("?")) {
					name2 = null;
				}
				rhsNode.setName(name2);
				if (nodeType!=null) {
					rhsNode.setType(nodeType);
				}
				// We need to make a dummy change to trigger a notification event:
				node.setName(name+"2");
			}
		}
		
		// Set the name and the type of the primary node:
		if (name.length()==0 || name.equals("?")) {
			name = null;
		}
		node.setName(name);
		if (nodeType!=null) {
			node.setType(nodeType);
		}
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature
	 * (java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature == HenshinPackage.eINSTANCE.getNamedElement_Name()) return true;
		if (feature == HenshinPackage.eINSTANCE.getNode_Type()) return true;
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString
	 * (org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}
	
}
