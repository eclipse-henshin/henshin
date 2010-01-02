package org.eclipse.emf.henshin.diagram.edit.policies;

import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

/**
 * @generated
 */
public class EdgeItemSemanticEditPolicy extends
		HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public EdgeItemSemanticEditPolicy() {
		super(HenshinElementTypes.Edge_4001);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		return getGEFWrapper(new DestroyElementCommand(req));
	}

}
