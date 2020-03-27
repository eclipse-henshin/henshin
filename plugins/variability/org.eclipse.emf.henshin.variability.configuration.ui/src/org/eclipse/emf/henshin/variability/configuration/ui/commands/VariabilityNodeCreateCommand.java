package org.eclipse.emf.henshin.variability.configuration.ui.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.diagram.edit.commands.NodeCreateCommand;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.variability.configuration.ui.helpers.VariabilityModelHelper;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

import configuration.Configuration;

/**
 * This class provides a command to create variability enabled {@link Node}s.
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityNodeCreateCommand extends NodeCreateCommand {

	private final Configuration configuration;
	
	public VariabilityNodeCreateCommand(CreateElementRequest req, Configuration configuration) {
		super(req);
		this.configuration = configuration;
	}

	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		CommandResult result = super.doExecuteWithResult(monitor, info);
		Node node = (Node) result.getReturnValue();
		if (node != null) TransactionalVariabilityFactory.INSTANCE.createVariabilityNode(node).setPresenceCondition(VariabilityModelHelper.getPresenceCondition(configuration));
		return result;
	}

	
	/*
	 * Update containment for the new node.
	 * INFO: Was copied and pasted from superclass,
	 * except for setting of the new edge's presence condition.
	 */
	protected void updateContainment(Rule rule, Node newNode) throws ExecutionException {
		
		// Update the root containment for the new node:
		View ruleView = RuleEditHelper.findRuleView(rule);
		RootObjectEditHelper.updateRootContainment(ruleView, newNode);

		// Check if it makes sense to create a containment edge to the new node:		
		if (!newNode.getIncoming().isEmpty()) {
			return;
		}
		for (Node container : rule.getActionNodes(null)) {
			if (container==newNode || container.getType()==null) {
				continue;
			}
			for (EReference ref : container.getType().getEAllContainments()) {
				EClass refType = ref.getEReferenceType();
				if (refType!=null && (refType.isSuperTypeOf(newNode.getType())) 
						|| (newNode.getType()).isSuperTypeOf(refType)) {
					if (rule.canCreateEdge(container, newNode, ref)) {
						Edge edge = rule.createEdge(container, newNode, ref);
						TransactionalVariabilityFactory.INSTANCE.createVariabilityEdge(edge).setPresenceCondition(VariabilityModelHelper.getPresenceCondition(configuration));
						return;
					}
				}
			}
		}
	}
	
}
	