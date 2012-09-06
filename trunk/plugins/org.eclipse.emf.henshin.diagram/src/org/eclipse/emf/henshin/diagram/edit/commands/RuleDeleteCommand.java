/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * Command for deleting rules.
 * @author Christian Krause
 * @generated NOT
 */
public class RuleDeleteCommand extends AbstractTransactionalCommand {

	// Rule to be deleted.
	private Rule rule;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 * @param rule Rule to be deleted.
	 */
	public RuleDeleteCommand(TransactionalEditingDomain domain, Rule rule) {
		super(domain, "Delete Rule", null);
		this.rule = rule;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		// Nothing to do?
		if (rule.getModule()==null) {
			return CommandResult.newOKCommandResult();
		}
		
		// Delete the rule.
		EcoreUtil.delete(rule);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
