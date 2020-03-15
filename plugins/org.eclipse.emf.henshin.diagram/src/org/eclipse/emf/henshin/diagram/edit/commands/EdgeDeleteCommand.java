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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.EdgeActionHelper;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;

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
		
		// If the edge is not inside of a graph there is nothing to do.
		if (edge.getGraph()==null || edge.getGraph().getRule()==null) {
			return CommandResult.newErrorCommandResult("Edge not contained in graph / rule");
		}
		Rule rule = edge.getGraph().getRule();
		
		//update the ACs in the rule
	    EdgeActionHelper.INSTANCE.updateACsAndSubrules(rule,edge,edge.getAction(),null);
		
		// Remove the edge.
		doRemove(edge);
		
		
		// Clean up:
		HenshinModelCleaner.cleanRule(rule.getRootRule());

		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	/*
	 * Remove the edge.
	 */
	private void doRemove(Edge edge) throws ExecutionException {

		// Can be null.
		if (edge==null) return;
		
		// Get the edge properties:
		Node source = edge.getSource();
		Node target = edge.getTarget();
		EReference type = edge.getType();
		Rule rule = edge.getGraph().getRule();

		rule.removeEdge(edge, true);

		// Update the root containment if the edge is containment / container:
		if (type!=null && (type.isContainment() || type.isContainer())) {
			View ruleView = RuleEditHelper.findRuleView(rule);
			if (ruleView!=null) {
				RootObjectEditHelper.updateRootContainment(ruleView, source);
				RootObjectEditHelper.updateRootContainment(ruleView, target);
			}
		}

	}
	
}
