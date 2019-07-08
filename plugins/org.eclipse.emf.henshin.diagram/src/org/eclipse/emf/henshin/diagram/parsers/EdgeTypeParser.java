/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers.AbstractAttributeParser;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class EdgeTypeParser extends AbstractAttributeParser {
	
	/**
	 * Default constructor.
	 */
	public EdgeTypeParser() {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getEdge_Index() });
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		Edge edge = (Edge) element.getAdapter(EObject.class);
		Rule rule = edge.getGraph().getRule();
		String type = (edge.getType()!=null) ? edge.getType().getName() : "?";
		String lhsIndex = edge.getIndex();
		String rhsIndex = lhsIndex;
		if (edge.getGraph().isLhs()) {
			Edge rhsEdge = rule.getMappings().getImage(edge, rule.getRhs());
			if (rhsEdge!=null) {
				rhsIndex = rhsEdge.getIndex();
			}
		}
		else if (edge.getGraph().isRhs()) {
			Edge lhsEdge = rule.getMappings().getOrigin(edge);
			if (lhsEdge!=null) {
				lhsIndex = lhsEdge.getIndex();
			}
		}
		lhsIndex = lhsIndex==null ? "" : lhsIndex.trim(); 
		rhsIndex = rhsIndex==null ? "" : rhsIndex.trim(); 
		String index;
		if (rhsIndex.length()==0) {
			index = lhsIndex;
		} else if (lhsIndex.length()==0) {
			index = rhsIndex;
		} else if (!lhsIndex.equals(rhsIndex)) {
			index = lhsIndex + "->" + rhsIndex;
		} else {
			index = lhsIndex;
		}
		if (index.length()>0) {
			return type + "[" + index.trim() + "]";
		}
		return type;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the edge:
		final Edge edge = (Edge) element.getAdapter(EObject.class);
		
		// Get the editing domain:
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(edge);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Parse Edge Type", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value, edge);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a edge type.
	 */
	private CommandResult doParsing(String value, Edge edge) {
		
		Rule rule = (Rule) edge.getGraph().getRule();
		Node source = edge.getSource();
		
		if (source.getType()==null) {
			return CommandResult.newErrorCommandResult("Source type must be set");
		}
		
		// Separate type name and index:
		String typeName = value;
		String lhsIndex = null;
		String rhsIndex = null;
		if (value.indexOf('[')>=0) {
			typeName = value.substring(0, value.indexOf('['));
			lhsIndex = value.substring(value.indexOf('[')+1, value.indexOf(']'));
			rhsIndex = lhsIndex;
		}
		if (lhsIndex!=null && lhsIndex.indexOf("->")>=0) {
			rhsIndex = lhsIndex.substring(lhsIndex.indexOf("->")+2);
			lhsIndex = lhsIndex.substring(0, lhsIndex.indexOf("->"));
		}
		
		// Find the corresponding EReference:
		EReference type = null;
		for (EReference reference : source.getType().getEAllReferences()) {
			if (typeName.equalsIgnoreCase(reference.getName())) {
				type = reference;
				break;
			}
		}
		if (type==null) {
			return CommandResult.newErrorCommandResult("Unknown reference: " + value);
		}
		
		// Find all mapped edge:
		List<Edge> edges = new ArrayList<Edge>();
		edges.add(edge);
		
		Edge image = rule.getMappings().getImage(edge, rule.getRhs());
		if (image!=null) edges.add(image);
		
		Edge origin = rule.getMappings().getOrigin(edge);
		if (origin!=null) edges.add(origin);
		
		// Make a dummy change:
		String oldIndex = edge.getIndex();
		edge.setIndex("xyz123");
		edge.setIndex(oldIndex);
		
		// Update the edges:
		for (Edge current : edges) {
			current.setType(type);
			if (current.getGraph().isRhs()) {
				current.setIndex(rhsIndex);
			} else {
				current.setIndex(lhsIndex);
			}
		}
		
		// ---
		// Bidirectional edges:
		/*if (edge.getType().getEOpposite() != null) {
			List<Edge> oppositeEdges = new ArrayList<Edge>();
			
			for (Edge imageTargetOutgoingEdge : image.getTarget().getOutgoing()) {
				if (imageTargetOutgoingEdge.getType().equals(image.getType()) &&
					imageTargetOutgoingEdge.getTarget().equals(image.getSource())) {
					// found the opposite edge
					oppositeEdges.add(imageTargetOutgoingEdge);
				}
			}
			
			for (Edge originTargetOutgoingEdge : origin.getTarget().getOutgoing()) {
				if (originTargetOutgoingEdge.getType().equals(origin.getType()) &&
					originTargetOutgoingEdge.getTarget().equals(origin.getSource())) {
					// found the opposite edge
					oppositeEdges.add(originTargetOutgoingEdge);
				}
			}
		}
		*/
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature(java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature==HenshinPackage.eINSTANCE.getEdge_Type()) return true;
		if (feature==HenshinPackage.eINSTANCE.getEdge_Index()) return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
