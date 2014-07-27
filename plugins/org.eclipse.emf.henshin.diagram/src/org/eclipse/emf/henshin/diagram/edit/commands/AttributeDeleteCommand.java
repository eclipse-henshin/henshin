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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * Command for deleting attributes.
 * @generated NOT
 * @author Christian Krause
 */
public class AttributeDeleteCommand extends AbstractTransactionalCommand {

	// Attribute to be deleted.
	private Attribute attribute;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 * @param edge Edge to be deleted.
	 */
	public AttributeDeleteCommand(TransactionalEditingDomain domain, Attribute attribute) {
		super(domain, "Delete Attribute", null);
		this.attribute = attribute;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		// Nothing to do?
		if (attribute.getNode()==null) {
			return CommandResult.newOKCommandResult();
		}
		Rule rule = attribute.getNode().getGraph().getRule().getRootRule();
		
		// Remove the attribute:
		rule.removeAttribute(attribute, true);
				
		// Clean up:
		HenshinModelCleaner.cleanRule(rule);

		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
