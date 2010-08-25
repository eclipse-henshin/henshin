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
import org.eclipse.emf.henshin.diagram.edit.actions.Action;
import org.eclipse.emf.henshin.diagram.edit.actions.ActionType;
import org.eclipse.emf.henshin.diagram.edit.actions.EdgeActionHelper;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class EdgeDeleteCommand extends AbstractTransactionalCommand {

	// Edge to be deleted.
	private Edge edge;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 * @param edge Edge to be deleted.
	 */
	public EdgeDeleteCommand(TransactionalEditingDomain domain, Edge edge) {
		super(domain, "Delete Edge", null);
		this.edge = edge;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		// Check for edge images:
		Action action = EdgeActionHelper.INSTANCE.getAction(edge);
		if (action!=null && action.getType()==ActionType.PRESERVE) {
			Rule rule = edge.getGraph().getContainerRule();
			Edge image = HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings());
			image.getGraph().removeEdge(image);
		}
		
		// Now we can remove it safely.
		edge.getGraph().removeEdge(edge);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
