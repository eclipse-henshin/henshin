/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved.
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.diagram.parsers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.henshin.diagram.edit.parts.InvocationEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitCompartmentEditPart;
import org.eclipse.emf.henshin.diagram.edit.parts.UnitEditPart;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Parser for unit invocations.
 * @generated NOT
 * @author Christian Krause
 */
public class InvocationNameParser extends AbstractParser {
	
	/**
	 * Default constructor.
	 */
	public InvocationNameParser() {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getPrintString(IAdaptable element, int flags) {		
		TransformationUnit invocation = (TransformationUnit) element.getAdapter(EObject.class);
		if (invocation==null || invocation.getName()==null) {
			return "null";
		} else {
			return invocation.getName();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand(org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the rule view:
		final View view = (View) element.getAdapter(View.class);
		
		// Get the editing domain:
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(view.getElement());
		if (domain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Parse Invocation Name", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				return doParsing(view, value);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a rule name and the optional root node.
	 */
	protected CommandResult doParsing(View view, String value) throws ExecutionException {
		
		TransformationUnit parent = (TransformationUnit) ((View) view.eContainer().eContainer()).getElement();
		TransformationUnit invocation = (TransformationUnit) view.getElement();
		
		// Compute possible target candidates:
		TransformationSystem system = (TransformationSystem) view.getDiagram().getElement();
		List<TransformationUnit> candidates = new ArrayList<TransformationUnit>();
		candidates.addAll(system.getRules());
		candidates.addAll(system.getTransformationUnits());
		
		value = value.trim();
		for (TransformationUnit unit : candidates) {
			if (value.equals(unit.getName())) {
				
				// Update the parent unit:
				if (parent instanceof SequentialUnit) {
					SequentialUnit sequential = (SequentialUnit) parent;
					
					// First update the views!!!
					updateInvocationViews(sequential, invocation, unit);
					
					// Then the model:
					int index = sequential.getSubUnits().indexOf(invocation);
					sequential.getSubUnits().set(index, unit);
					
				} else {
					// Error.
					return CommandResult.newErrorCommandResult("Unknown transformation unit type: " + parent.eClass().getName());
				}
				
				// Update the view:
				view.setElement(unit);
				
				// Done:
				return CommandResult.newOKCommandResult();
			}
		}
		
		// Error.
		return CommandResult.newErrorCommandResult("Unknown transformation unit: " + value);
		
	}

	/*
	 * Update all views associated to an invocation.
	 */
	private void updateInvocationViews(TransformationUnit parent, 
			TransformationUnit oldInvocation, TransformationUnit newInvocation) {
		
		String unitViewType = String.valueOf(UnitEditPart.VISUAL_ID);
		String unitCompartmentType = String.valueOf(UnitCompartmentEditPart.VISUAL_ID);
		String invocationViewType = String.valueOf(InvocationEditPart.VISUAL_ID);
		
		// Update all resources:
		ResourceSet resourceSet = parent.eResource().getResourceSet();
		for (Resource resource : resourceSet.getResources()) {
			for (EObject root : resource.getContents()) {
				if (root instanceof Diagram) {
					for (Object obj : ((Diagram) root).getChildren()) {
						View parentView = (View) obj;
						if (unitViewType.equals(parentView.getType())) {
							for (Object obj2 : parentView.getChildren()) {
								View compartment = (View) obj2;
								if (unitCompartmentType.equals(compartment.getType())) {
									for (Object obj3 : compartment.getChildren()) {
										View invocationView = (View) obj3;
										if (invocationViewType.equals(invocationView.getType()) &&
												invocationView.getElement()==oldInvocation) {
											invocationView.setElement(newInvocation);
											//System.out.println("updating view for " + oldInvocation.getName());
										}
									}
								}
							}							
						}
					}
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature(java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature==HenshinPackage.eINSTANCE.getNamedElement_Name()) return true;
		if (feature==HenshinPackage.eINSTANCE.getTransformationUnit_Parameters()) return true;
		if (feature==EcorePackage.eINSTANCE.getEModelElement_EAnnotations()) return true;
		if (feature==EcorePackage.eINSTANCE.getEAnnotation_References()) return true;
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString(org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
