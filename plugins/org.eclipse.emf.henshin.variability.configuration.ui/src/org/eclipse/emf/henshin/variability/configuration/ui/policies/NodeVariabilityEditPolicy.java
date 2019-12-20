package org.eclipse.emf.henshin.variability.configuration.ui.policies;

import org.eclipse.emf.henshin.diagram.edit.policies.NodeCompartmentItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.variability.configuration.ui.commands.VariabilityAttributeCreateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

import configuration.Configuration;

/**
 * This class helps setting the context in which new variability aware nodes are created.
 * 
 * @author Stefan Schulz
 *
 */
public class NodeVariabilityEditPolicy extends NodeCompartmentItemSemanticEditPolicy {
	private final Configuration configuration;

	public NodeVariabilityEditPolicy(Configuration configuration) {
		this.configuration = configuration;
	}

	public NodeVariabilityEditPolicy() {
		this.configuration = null;
	}
	
	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		if (HenshinElementTypes.Node_3001 == req.getElementType()) {
			return getGEFWrapper(new VariabilityAttributeCreateCommand(req, getHost().getViewer().getControl().getShell(), configuration));
		}
		return super.getCreateCommand(req);
	}
	
}