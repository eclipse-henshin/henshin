package org.eclipse.emf.henshin.variability.configuration.ui.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.commands.EdgeCreateCommand;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.variability.configuration.ui.helpers.VariabilityModelHelper;
import org.eclipse.emf.henshin.variability.wrapper.TransactionalVariabilityFactory;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import configuration.Configuration;

/**
 * This class provides a command to create variability enabled {@link Edge}s.
 * 
 * @author Stefan Schulz
 *
 */
public class VariabilityEdgeCreateCommand extends EdgeCreateCommand {

	private final Configuration configuration;
	
	public VariabilityEdgeCreateCommand(CreateRelationshipRequest request, EObject source, EObject target, Configuration configuration) {
		super(request, source, target);
		this.configuration = configuration;
	}
	
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		CommandResult result = super.doExecuteWithResult(monitor, info);
		Edge edge = (Edge) result.getReturnValue();
		String pc = VariabilityModelHelper.getPresenceConditionForNewEdge(edge, configuration);
		TransactionalVariabilityFactory.INSTANCE.createVariabilityEdge(edge).setPresenceCondition(pc);
		return result;
	}

}
