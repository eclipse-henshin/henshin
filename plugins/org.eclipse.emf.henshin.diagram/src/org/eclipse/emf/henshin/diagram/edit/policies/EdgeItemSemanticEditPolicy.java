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

import org.eclipse.emf.henshin.diagram.edit.commands.EdgeDeleteCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;

/**
 * @generated
 */
public class EdgeItemSemanticEditPolicy extends HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public EdgeItemSemanticEditPolicy() {
		super(HenshinElementTypes.Edge_4001);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		if (req.getElementToDestroy() instanceof Edge) {
			return getGEFWrapper(new EdgeDeleteCommand(getEditingDomain(), (Edge) req.getElementToDestroy()));
		} else {
			return null;
		}
	}

}
