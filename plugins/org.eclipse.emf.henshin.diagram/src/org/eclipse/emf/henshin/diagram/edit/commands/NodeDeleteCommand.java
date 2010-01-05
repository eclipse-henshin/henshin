package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
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
		
		// Get the rule:
		Rule rule = (Rule) node.getGraph().eContainer();
		
		// Remove mappings and mapped nodes:
		for (int i=0; i<rule.getMappings().size(); i++) {
			
			Mapping mapping = rule.getMappings().get(i);
			Node origin = mapping.getOrigin();
			Node image = mapping.getImage();
			
			if (origin==node || image==node) {
				EcoreUtil.remove(origin);
				EcoreUtil.remove(image);
				EcoreUtil.remove(mapping);
				i--;
			}			
		}
		
		// Remove the node:
		EcoreUtil.remove(node);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
