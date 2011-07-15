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
package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class InvocationDeleteCommand extends AbstractTransactionalCommand {

	private TransformationUnit unit;
	private TransformationUnit invocation;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 */
	public InvocationDeleteCommand(TransactionalEditingDomain domain, TransformationUnit unit, TransformationUnit invocation) {
		super(domain, "Delete Invocation", null);
		this.unit = unit;
		this.invocation = invocation;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.core.commands.operations.AbstractOperation#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (unit==null || invocation==null) {
			return false;
		}
		if (!unit.getSubUnits(false).contains(invocation)) {
			return false;
		}
		return true;
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		if (unit instanceof SequentialUnit) {
			((SequentialUnit) unit).getSubUnits().remove(invocation);
		}
		
		return CommandResult.newOKCommandResult();
	}
	
}
