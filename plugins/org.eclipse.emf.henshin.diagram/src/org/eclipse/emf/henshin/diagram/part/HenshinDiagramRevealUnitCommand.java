/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.part;

import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.edit.parts.ModuleEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitEditPart;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CanonicalEditPolicy;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.PlatformUI;

/**
 * @generated
 */
public class HenshinDiagramRevealUnitCommand implements IHandler {

	/**
	 * @generated
	 */
	public void addHandlerListener(IHandlerListener handlerListener) {
	}

	/**
	 * @generated
	 */
	public void dispose() {
	}

	/**
	 * @generated
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService()
				.getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			if (structuredSelection.size() != 1) {
				return null;
			}
			if (structuredSelection.getFirstElement() instanceof EditPart
					&& ((EditPart) structuredSelection.getFirstElement()).getModel() instanceof View) {
				EditPart selected = (EditPart) structuredSelection.getFirstElement();
				EObject modelElement = ((View) ((EditPart) structuredSelection.getFirstElement()).getModel())
						.getElement();

				EditPart target = null;
				if (modelElement instanceof Unit) {
					if (selected.getParent() != null && selected.getParent().getParent() != null
							&& selected.getParent().getParent().getParent() instanceof ModuleEditPart) {
						ModuleEditPart container = (ModuleEditPart) selected.getParent().getParent().getParent();
						for (Object part : container.getChildren()) {
							if (part instanceof EditPart)
								if (((ShapeImpl) ((EditPart) part).getModel()).getElement() == modelElement)
									target = (EditPart) part;

						}
						if (target != null) {
							container.getViewer().reveal(target);
							container.getViewer().select(target);
						}
					}
				}

			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public boolean isEnabled() {
		return true;
	}

	/**
	 * @generated
	 */
	public boolean isHandled() {
		return true;
	}

	/**
	 * @generated
	 */
	public void removeHandlerListener(IHandlerListener handlerListener) {
	}

}
