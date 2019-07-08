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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.diagram.part.HenshinDiagramEditorPlugin;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Node;
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
 * @generated NOT
 * @author Christian Krause
 */
public class NodeActionParser extends AbstractAttributeParser {
	
	public static final char ACTION_QUOTE_LEFT = ((char) 171);
	public static final char ACTION_QUOTE_RIGHT = ((char) 187);
	
	/**
	 * Default constructor.
	 */
	public NodeActionParser() {
		super(new EAttribute[] {});
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		Node node = (Node) element.getAdapter(EObject.class);
		Action action = node.getAction();
		return (action!=null) ? action.toString() : "unknown";
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {
		return addActionQuotes(getEditString(element, flags));
	}

	/*
	 * Add quotes around an action.
	 */
	public static String addActionQuotes(String string) {
		return ACTION_QUOTE_LEFT + string + ACTION_QUOTE_RIGHT;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
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
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(editingDomain, "Parse Node Action", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(value, node);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a node action.
	 */
	private CommandResult doParsing(String value, Node node) {
		try {
			
			// Parse the action:
			Action action = Action.parse(value);
			
			// Set the node action:
			node.setAction(action);
			
			// Take this action as the new default action for the rule:
			RuleEditHelper.setDefaultAction(node.getGraph().getRule(), action);
			
			return CommandResult.newOKCommandResult();
		}
		catch (Throwable t) {
			HenshinDiagramEditorPlugin.getInstance().logError("Error occurred when trying to set a node action", t);
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
