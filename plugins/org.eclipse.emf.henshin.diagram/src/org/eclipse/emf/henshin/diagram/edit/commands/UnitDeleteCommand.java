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
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * Command for deleting transformation units.
 * @author Christian Krause
 * @generated NOT
 */
public class UnitDeleteCommand extends AbstractTransactionalCommand {

	// Unit to be deleted.
	private Unit unit;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 * @param unit Unit to be deleted.
	 */
	public UnitDeleteCommand(TransactionalEditingDomain domain, Unit unit) {
		super(domain, "Delete Unit", null);
		this.unit = unit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		// Nothing to do?
		if (unit.getModule()==null) {
			return CommandResult.newOKCommandResult();
		}
		
		// Delete the unit.
		unit.getModule().getUnits().remove(unit);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
