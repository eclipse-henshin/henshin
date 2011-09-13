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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.helpers.TransformationSystemEditHelper;
import org.eclipse.emf.henshin.diagram.parsers.NodeTypeParserHelper.ParameterDirection;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.util.HenshinRuleAnalysisUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class NodeTypeParser extends AbstractParser {
	
	private static final NodeTypeParserHelper PARSER_HELPER = new NodeTypeParserHelper();
	
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
		Node node = (Node) element.getAdapter(EObject.class);
		
		// Find all mapped nodes:
		List<Node> originNodes = new ArrayList<Node>();
		List<Node> imageNodes = new ArrayList<Node>();
		
		String para1 = null;
		ParameterDirection para1Dir = ParameterDirection.IN;
		String para2 = null;
		ParameterDirection para2Dir = ParameterDirection.OUT;
		
		findMappedNodes(node, originNodes, imageNodes);
		for (Node origNode : originNodes) {
			if (origNode.getName() != null && !origNode.getName().trim().isEmpty()) {
				para1 = origNode.getName().trim();
				break;
			}// if
		}// for
		for (Node imgNode : imageNodes) {
			if (imgNode.getName() != null && !imgNode.getName().trim().isEmpty()) {
				para2 = imgNode.getName().trim();
				break;
			}// if
		}// for
		
		if (para1 == null && para2 != null) {
			para1 = para2;
			para2 = null;
			para1Dir = para2Dir;
			para2Dir = null;
		} else if (para1 != null && para1.equals(para2)) {
			para2 = null;
			para2Dir = null;
			para1Dir = ParameterDirection.INOUT;
		}
		
		String paraString = PARSER_HELPER.getParameterString(para1, para1Dir, para2, para2Dir);
		
		// String name = node.getName() != null ? node.getName() : "";
		if (node.getType() != null) {
			return paraString + ":" + node.getType().getName();
		} else {
			return paraString;
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
	 * Parse a node name and type.
	 */
	private CommandResult doParsing(String value, Node node) {
		
		if (!PARSER_HELPER.parse(value))
			return CommandResult.newErrorCommandResult("Invalid input");
		
		// We need the rule and the TS
		TransformationSystem ts = node.getGraph().getContainerRule().getTransformationSystem();
		
		// Find all mapped nodes:
		List<Node> originNodes = new ArrayList<Node>();
		List<Node> imageNodes = new ArrayList<Node>();
		findMappedNodes(node, originNodes, imageNodes);
		
		// set the typing
		if (PARSER_HELPER.getType() != null) {
			EClass eclass = null;
			final EClassifier[] list = TransformationSystemEditHelper.findEClassifierByName(ts,
					PARSER_HELPER.getType());
			if (list.length > 0) {
				for (EClassifier ec : list) {
					if ((ec instanceof EClass)) {
						eclass = (EClass) ec;
						break;
					}// if
				}// for
			}// if
			
			if (eclass == null) return CommandResult.newErrorCommandResult("No such EClass found");
			
			// set the typing
			for (Node current : originNodes) {
				current.setType(eclass);
			}// for
			for (Node current : imageNodes) {
				current.setType(eclass);
			}// for
		}// if
		
		// now deal with the parameter information
		if (PARSER_HELPER.getParameter1() != null) {
			final String para1 = PARSER_HELPER.getParameter1();
			final ParameterDirection dir1 = PARSER_HELPER.getDirection1();
			for (Node current : imageNodes) {
				current.setName(null);
			}// for
			for (Node current : originNodes) {
				current.setName(null);
			}// for
			if (dir1.equals(NodeTypeParserHelper.ParameterDirection.IN)
					|| dir1.equals(NodeTypeParserHelper.ParameterDirection.INOUT)) {
				for (Node current : originNodes) {
					current.setName(para1);
				}// for
			} // if
			if (dir1.equals(NodeTypeParserHelper.ParameterDirection.OUT)
					|| dir1.equals(NodeTypeParserHelper.ParameterDirection.INOUT)) {
				for (Node current : imageNodes) {
					current.setName(para1);
				}// for
			} // if
			
			if (PARSER_HELPER.getParameter2() != null) {
				final String para2 = PARSER_HELPER.getParameter2();
				final ParameterDirection dir2 = PARSER_HELPER.getDirection2();
				if (dir2.equals(NodeTypeParserHelper.ParameterDirection.IN)) {
					for (Node current : originNodes) {
						current.setName(para2);
					}// for
				}// if
				if (dir2.equals(NodeTypeParserHelper.ParameterDirection.OUT)) {
					for (Node current : imageNodes) {
						current.setName(para2);
					}// for
				}// if
				
			}// if
			
		} else { // remove names if available
			for (Node current : originNodes) {
				current.setName(null);
			}// for
			for (Node current : imageNodes) {
				current.setName(null);
			}// for
		}// if else
		
		return CommandResult.newOKCommandResult();
		
	}// doParsing
	
	/**
	 * @param node
	 * @param originNodes
	 * @param imageNodes
	 */
	private void findMappedNodes(Node node, List<Node> originNodes, List<Node> imageNodes) {
		EObject container = node.getGraph().eContainer();
		boolean isLhsPart = HenshinRuleAnalysisUtil.isLHSPart(node.getGraph());
		if (isLhsPart)
			originNodes.add(node);
		else
			imageNodes.add(node);
		
		if (container instanceof Rule) {
			Rule rule = (Rule) container;
			for (Mapping mapping : rule.getMappings()) {
				if (mapping.getOrigin() == node) imageNodes.add(mapping.getImage());
				if (mapping.getImage() == node) originNodes.add(mapping.getOrigin());
			}// for
		} else if (container instanceof NestedCondition) {
			NestedCondition nc = (NestedCondition) container;
			/*
			 * The mappings within a nested condition run always upwards, i.e.,
			 * the mappings have "remote" origins while all images are nodes in
			 * the nested condition's graph.
			 */
			for (Mapping mapping : nc.getMappings()) {
				if (mapping.getImage() == node) {
					originNodes.add(mapping.getOrigin());
				}// if
			}// for
		}// if else
	}// findMappedNodes
	
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
