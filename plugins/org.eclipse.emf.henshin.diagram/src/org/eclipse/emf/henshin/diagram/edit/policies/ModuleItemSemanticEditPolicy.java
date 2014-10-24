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

import org.eclipse.emf.henshin.diagram.edit.commands.RuleCreateCommand;
import org.eclipse.emf.henshin.diagram.edit.commands.UnitCreateCommand;
import org.eclipse.emf.henshin.diagram.providers.HenshinElementTypes;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.swt.widgets.Shell;

/**
 * @generated
 */
public class ModuleItemSemanticEditPolicy extends HenshinBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	public ModuleItemSemanticEditPolicy() {
		super(HenshinElementTypes.Module_1000);
	}

	/**
	 * @generated
	 */
	protected Command getCreateCommandGen(CreateElementRequest req) {
		if (HenshinElementTypes.Rule_2001 == req.getElementType()) {
			return getGEFWrapper(new RuleCreateCommand(req));
		}
		if (HenshinElementTypes.Unit_2002 == req.getElementType()) {
			return getGEFWrapper(new UnitCreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated NOT
	 */
	protected Command getCreateCommand(CreateElementRequest req) {

		// We need to override the default implementation because
		// the unit create command needs a shell:
		if (HenshinElementTypes.Unit_2002 == req.getElementType()) {
			Shell shell = getHost().getViewer().getControl().getShell();
			return getGEFWrapper(new UnitCreateCommand(req, shell));
		} else {
			return getCreateCommandGen(req);
		}
	}

	/**
	 * @generated
	 */
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends DuplicateEObjectsCommand {

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(TransactionalEditingDomain editingDomain, DuplicateElementsRequest req) {
			super(editingDomain, req.getLabel(), req.getElementsToBeDuplicated(), req.getAllDuplicatedElementsMap());
		}

	}

}
