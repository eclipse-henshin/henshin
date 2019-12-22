package org.eclipse.emf.henshin.variability.configuration.ui.policies;

import org.eclipse.emf.henshin.diagram.edit.policies.RuleCompartmentItemSemanticEditPolicy;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.variability.configuration.ui.commands.VariabilityNodeCreateCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import configuration.Configuration;

/**
 * This class helps setting the context in which new variability aware rules are created.
 * 
 * @author Stefan Schulz
 *
 */
public class RuleVariabilityEditPolicy extends RuleCompartmentItemSemanticEditPolicy {

	private final Configuration configuration;

	public RuleVariabilityEditPolicy(Configuration configuration) {
		this.configuration = configuration;
	}


	public RuleVariabilityEditPolicy() {
		this.configuration = null;
	}
	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		if (HenshinElementTypes.Node_3001 == req.getElementType()) {

			return getGEFWrapper(new VariabilityNodeCreateCommand(req, configuration));
		}
		return super.getCreateCommand(req);
	}
	
}
