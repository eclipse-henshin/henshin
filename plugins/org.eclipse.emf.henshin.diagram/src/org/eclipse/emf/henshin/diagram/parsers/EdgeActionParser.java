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

import static org.eclipse.emf.henshin.model.Action.Type.PRESERVE;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers.AbstractAttributeParser;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;

/**
 * Parser for edge action labels.
 * @author Christian Krause
 * @generated NOT
 */
public class EdgeActionParser extends AbstractAttributeParser {
	
	/**
	 * Default constructor.
	 */
	public EdgeActionParser() {
		super(new EAttribute[] {});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		Edge edge = (Edge) element.getAdapter(EObject.class);
		Action action = edge.getAction();
		return (action!=null) ? action.toString() : "unknown";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		return NodeActionParser.addActionQuotes(getEditString(element, flags));
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
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Parse Edge Action", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value, edge);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse an edge action.
	 */
	private CommandResult doParsing(String value, Edge edge) {
		try {
			// Parse the action:
			Action action = Action.parse(value);
			
			// Make sure the action is compatible with the source and target actions:
			Node src = edge.getSource().getActionNode();
			Node trg = edge.getTarget().getActionNode();
			Action srcAction = src.getAction();
			Action trgAction = trg.getAction();
			
			// The source and the target node must have either the same
			// action as the edge, or PRESERVE.			
			if (!srcAction.equals(action) && 
				!(srcAction.getType()==PRESERVE)) {
				src.setAction(action);
			}
			if (!trgAction.equals(action) && 
				!(trgAction.getType()==PRESERVE)) {
				trg.setAction(action);
			}
			
			// Now we can safely set the action:
			edge.setAction(action);
			
			HenshinModelCleaner.cleanRule(edge.getGraph().getRule());
			return CommandResult.newOKCommandResult();
			
		}
		catch (Throwable t) {
			HenshinDiagramEditorPlugin.getInstance().logError("Error setting edge action", t);
			return CommandResult.newErrorCommandResult(t);
		}		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#getCompletionProcessor(org.eclipse.core.runtime.IAdaptable)
	 */
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		//return new ActionCompletionProcessor();
		return null;
	}
	
}
