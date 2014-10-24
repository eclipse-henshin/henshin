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

import org.eclipse.emf.henshin.diagram.edit.commands.InvocationCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;

/**
 * @generated
 */
public class UnitCompartmentItemSemanticEditPolicy extends HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public UnitCompartmentItemSemanticEditPolicy() {
		super(HenshinElementTypes.Unit_2002);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (HenshinElementTypes.Unit_3003 == req.getElementType()) {
			return getGEFWrapper(new InvocationCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
