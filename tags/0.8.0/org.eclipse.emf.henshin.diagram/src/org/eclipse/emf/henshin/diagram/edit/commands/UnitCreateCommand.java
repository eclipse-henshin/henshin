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
package org.eclipse.emf.henshin.diagram.edit.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.diagram.part.HenshinPaletteTools.EClassNodeTool;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;

/**
 * This command creates a new transformation unit.
 * @generated NOT
 */
public class UnitCreateCommand extends EditElementCommand {

	/**
	 * Default constructor.
	 * @generated
	 */
	public UnitCreateCommand(CreateElementRequest request) {
		super(request.getLabel(), null, request);
	}

	/**
	 * FIXME: replace with setElementToEdit()
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest())
				.getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	public boolean canExecute() {
		return true;

	}

	/**
	 * We need to ask the user what kind of transformation unit should be created.
	 * @generated NOT
	 */
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor,
			IAdaptable info) throws ExecutionException {
		
		// Get the unit type:
		EClass unitType = HenshinPackage.eINSTANCE.getSequentialUnit();
		CreateElementRequest request = (CreateElementRequest) getRequest();
		if (request.getParameter(EClassNodeTool.TYPE_PARAMETER_KEY) instanceof EClass) {
			unitType = (EClass) request.getParameter(EClassNodeTool.TYPE_PARAMETER_KEY);
		}

		// Create the transformation unit:
		TransformationUnit unit = (TransformationUnit) HenshinFactory.eINSTANCE
				.create(unitType);

		// Add it to the transformation system:
		TransformationSystem system = (TransformationSystem) getElementToEdit();
		system.getTransformationUnits().add(unit);

		// Configure the unit:
		doConfigure(unit, monitor, info);

		// Done.
		((CreateElementRequest) getRequest()).setNewElement(unit);
		return CommandResult.newOKCommandResult(unit);

	}

	/**
	 * @generated
	 */
	protected void doConfigure(TransformationUnit newElement,
			IProgressMonitor monitor, IAdaptable info)
			throws ExecutionException {
		IElementType elementType = ((CreateElementRequest) getRequest())
				.getElementType();
		ConfigureRequest configureRequest = new ConfigureRequest(
				getEditingDomain(), newElement, elementType);
		configureRequest.setClientContext(((CreateElementRequest) getRequest())
				.getClientContext());
		configureRequest.addParameters(getRequest().getParameters());
		ICommand configureCommand = elementType
				.getEditCommand(configureRequest);
		if (configureCommand != null && configureCommand.canExecute()) {
			configureCommand.execute(monitor, info);
		}
	}

}
