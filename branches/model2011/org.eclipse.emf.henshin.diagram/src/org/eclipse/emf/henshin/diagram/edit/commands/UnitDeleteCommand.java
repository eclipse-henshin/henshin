package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * Command for deleting transformation units.
 * @author Christian Krause
 */
public class UnitDeleteCommand extends AbstractTransactionalCommand {

	// Unit to be deleted.
	private TransformationUnit unit;
	
	/**
	 * Default constructor.
	 * @param domain Editing domain.
	 * @param unit Unit to be deleted.
	 */
	public UnitDeleteCommand(TransactionalEditingDomain domain, TransformationUnit unit) {
		super(domain, "Delete Unit", null);
		this.unit = unit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		
		// Delete the unit:
		EcoreUtil.delete(unit);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
