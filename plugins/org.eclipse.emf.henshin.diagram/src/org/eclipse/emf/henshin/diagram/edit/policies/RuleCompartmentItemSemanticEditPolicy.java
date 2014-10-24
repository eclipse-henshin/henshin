/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.edit.policies;

import org.eclipse.emf.henshin.diagram.edit.commands.AttributeConditionCreateCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.NodeCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

/**
 * @generated
 */
public class RuleCompartmentItemSemanticEditPolicy extends HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public RuleCompartmentItemSemanticEditPolicy() {
		super(HenshinElementTypes.Rule_2001);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (HenshinElementTypes.Node_3001 == req.getElementType()) {
			return getGEFWrapper(new NodeCreateCommand(req));
		}
		if (HenshinElementTypes.AttributeCondition_3005 == req.getElementType()) {
			return getGEFWrapper(new AttributeConditionCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
