package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.util.HenshinGraphUtil;
import org.eclipse.emf.henshin.model.util.HenshinMappingUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;

/**
 * @generated NOT
 * @author Christian Krause
 */
public class EdgeDeleteCommand  extends AbstractTransactionalCommand {

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
		
		// Get the graph and rule:
		Graph graph = edge.getGraph();
		Rule rule = (Rule) graph.eContainer();
		
		// Delete mapped edges:
		if (graph==rule.getLhs()) {
			Edge image = HenshinMappingUtil.getEdgeImage(edge, rule.getRhs(), rule.getMappings());
			if (image!=null) HenshinGraphUtil.deleteEdge(image);
		} else {
			Edge origin = HenshinMappingUtil.getEdgeOrigin(edge, rule.getMappings());
			if (origin!=null) HenshinGraphUtil.deleteEdge(origin);
		}
		
		// Delete the edge:
		HenshinGraphUtil.deleteEdge(edge);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
}
