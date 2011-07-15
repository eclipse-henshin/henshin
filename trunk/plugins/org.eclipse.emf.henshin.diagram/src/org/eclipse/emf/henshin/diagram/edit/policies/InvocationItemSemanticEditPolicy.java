/*
 * Copyright (c) 2010 HPI Potsdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     HPI Potsdam - initial API and implementation
 */
package org.eclipse.emf.henshin.diagram.edit.policies;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.henshin.diagram.edit.commands.InvocationDeleteCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.RuleDeleteCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class InvocationItemSemanticEditPolicy extends
		HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public InvocationItemSemanticEditPolicy() {
		super(HenshinElementTypes.TransformationUnit_3003);
	}

	/**
	 * @generated NOT
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		View invocationView = (View) getHost().getModel();
		View unitView = (View) invocationView.eContainer();
		TransformationUnit unit = (TransformationUnit) unitView.getElement();
		TransformationUnit invocation = (TransformationUnit) invocationView
				.getElement();
		return getGEFWrapper(new InvocationDeleteCommand(getEditingDomain(),
				unit, invocation));
	}

}
