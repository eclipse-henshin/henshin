package org.eclipse.emf.henshin.variability.configuration.ui.policies;

import org.eclipse.emf.henshin.diagram.edit.policies.NodeItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.variability.configuration.ui.commands.VariabilityEdgeCreateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;

import configuration.Configuration;

/**
 * This class helps setting the context in which new variability aware edges are created.
 * 
 * @author Stefan Schulz
 *
 */
public class NodeVariabilityItemSemanticEditPolicy extends NodeItemSemanticEditPolicy {
	Configuration configuration;
	
	public NodeVariabilityItemSemanticEditPolicy(Configuration configuration) {
		super();
		this.configuration = configuration;
	}
	
	@Override
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (HenshinElementTypes.Edge_4001 == req.getElementType()) {
			return getGEFWrapper(new VariabilityEdgeCreateCommand(req, req.getSource(), req.getTarget(), configuration));
		}
		return super.getCompleteCreateRelationshipCommand(req);
	}

}
