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
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.actions.Action;
import org.eclipse.emf.henshin.diagram.edit.actions.EdgeActionHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.henshin.model.util.HenshinACUtil;
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
		if (edge.getGraph()==null || edge.getGraph().getContainerRule()==null) {
			return CommandResult.newErrorCommandResult("Edge not contained in graph / rule");
		}
		Rule rule = edge.getGraph().getContainerRule();
		
		// Check if there is an action associated:
		Action action = EdgeActionHelper.INSTANCE.getAction(edge);
		if (action==null) {
			edge.getGraph().removeEdge(edge);
			return CommandResult.newWarningCommandResult("Edge seems to be illegal. Deleted anyway.", null); // done.
		}
		
		// Remove the image in the RHS, if existing:
		doRemove(HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings()));
		
		// Remove images in the PACs/NACs:
		for (NestedCondition nac : HenshinACUtil.getAllACs(rule)) {
			doRemove(HenshinMappingUtil.getEdgeImage(edge, nac.getConclusion(), nac.getMappings()));
		}
		
		// Check if there are images in multi-rules:
		for (Rule multiRule : rule.getMultiRules()) {
			doRemove(HenshinMappingUtil
					.getEdgeImage(edge, multiRule.getLhs(), multiRule.getMultiMappings()));
		}
		
		// Now remove the edge.
		doRemove(edge);
		
		// Clean up trivial NAC and multi-rules:
		HenshinACUtil.removeTrivialACs(rule);
		RuleEditHelper.removeTrivialMultiRules(rule);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	private void doRemove(Edge edge) throws ExecutionException {

		// Can be null.
		if (edge==null) return;
		
		// Get the edge properties:
		Node source = edge.getSource();
		Node target = edge.getTarget();
		EReference type = edge.getType();
		Rule rule = edge.getGraph().getContainerRule();

		edge.getGraph().removeEdge(edge);

		// Update the root containment if the edge is containment / container:
		if (type!=null && (type.isContainment() || type.isContainer())) {
			View ruleView = RootObjectEditHelper.findRuleView(rule);
			if (ruleView!=null) {
				RootObjectEditHelper.updateRootContainment(ruleView, source);
				RootObjectEditHelper.updateRootContainment(ruleView, target);
			}
		}

	}
	
}
