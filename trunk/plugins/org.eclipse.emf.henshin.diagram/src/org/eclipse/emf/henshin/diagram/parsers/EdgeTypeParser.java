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

/**
 * @generated NOT
 * @author Christian Krause
 */
public class EdgeTypeParser extends AbstractParser {
	
	/**
	 * Default constructor.
	 */
	public EdgeTypeParser() {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		Edge edge = (Edge) element.getAdapter(EObject.class);
		return edge.getType()!=null ? edge.getType().getName() : "?";
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
		
		Rule rule = (Rule) edge.getGraph().eContainer();
		Node source = edge.getSource();
		
		if (source.getType()==null) {
			return CommandResult.newErrorCommandResult("Source type must be set");
		}
		
		EReference type = null;
		for (EReference reference : source.getType().getEReferences()) {
			if (value.equalsIgnoreCase(reference.getName())) {
				type = reference;
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
		
		// Set the type:
		for (Edge current : edges) {
			current.setType(type);
		}
		
		
		// ---
		// Bidirectional edges:
		if (edge.getType().getEOpposite() != null) {
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
		
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature(java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		return (feature==HenshinPackage.eINSTANCE.getEdge_Type());
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
