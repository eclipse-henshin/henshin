package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.actions.Action;
import org.eclipse.emf.henshin.model.actions.ActionType;
import org.eclipse.emf.henshin.model.actions.HenshinActionHelper;
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
		
		// Check for attribute images:
		Action action = HenshinActionHelper.getAction(attribute);
		if (action.getType()==ActionType.PRESERVE) {
			Rule rule = attribute.getNode().getGraph().getContainerRule();
			Attribute image = rule.getMappings().getImage(attribute, rule.getRhs());
			image.getNode().getAttributes().remove(image);
		}
		
		// Now we can remove it safely.
		attribute.getNode().getAttributes().remove(attribute);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
