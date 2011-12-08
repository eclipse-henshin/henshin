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
import org.eclipse.emf.henshin.diagram.edit.actions.NodeActionHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinACUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class NodeDeleteCommand extends AbstractTransactionalCommand {

	// Node to be deleted.
	private Node node;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 * @param node Node to be deleted.
	 */
	public NodeDeleteCommand(TransactionalEditingDomain domain, Node node) {
		super(domain, "Delete Node", null);
		this.node = node;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		// Nothing to do?
		if (node.getGraph()==null) {
			return CommandResult.newOKCommandResult();
		}
		
		// Check if there is an action associated:
		if (NodeActionHelper.INSTANCE.getAction(node)==null) {
			node.getGraph().removeNode(node);
			return CommandResult.newWarningCommandResult("Node seems to be illegal. Deleted anyway.", null); // done.
		}

		// We reset the action to DELETE, then we know where the node is:
		NodeActionHelper.INSTANCE.setAction(node, new Action(ActionType.DELETE));
		
		// Check if there are images in multi-rules.
		Rule kernel = node.getGraph().getContainerRule();
		for (Rule multi : kernel.getMultiRules()) {
			Node image = HenshinMappingUtil
					.getNodeImage(node, multi.getLhs(), multi.getMultiMappings());
			if (image!=null) {
				image.getGraph().removeNode(image);
				HenshinMappingUtil.removeMapping(node, image, multi.getMultiMappings());
			}
		}
		
		// Now we can delete the node.
		node.getGraph().removeNode(node);
		
		// Clean up trivial NAC and multi-rules:
		HenshinACUtil.removeTrivialACs(kernel);
		RuleEditHelper.removeTrivialMultiRules(kernel);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
