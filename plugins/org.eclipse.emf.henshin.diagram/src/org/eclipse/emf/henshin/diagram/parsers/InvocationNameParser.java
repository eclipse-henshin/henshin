/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.parsers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.diagram.edit.helpers.UnitEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.UnitEditHelper.InvocationViewKey;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.parsers.AbstractAttributeParser;

/**
 * Parser for unit invocations.
 * @generated NOT
 * @author Christian Krause
 */
public class InvocationNameParser extends AbstractAttributeParser {
	
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
		
		// Get the invoked unit:
		Unit invocation = (Unit) element.getAdapter(EObject.class);
		if (invocation==null || invocation.getName()==null) {
			return "null";
		}
		
		// We use the name of the target unit:
		String result = invocation.getName();
		
		// Any parameters?
		if (!invocation.getParameters().isEmpty() && element.getAdapter(View.class)!=null) {
			
			// Get the parent transformation unit:
			View view = (View) element.getAdapter(View.class);
			View invocationView = (View) view.eContainer();
			View compartmentView = (View) invocationView.eContainer();
			View unitView = (View) compartmentView.eContainer();
			Unit unit = (Unit) unitView.getElement();
			
			result = result + "(";
			boolean first = true;
			for (Parameter param1 : invocation.getParameters()) {
				boolean found1 = false, found2 = false;
				Parameter param2 = null;
				for (ParameterMapping m : unit.getParameterMappings()) {
					if (m.getSource()==param1 && m.getTarget()!=null && m.getTarget().getUnit()==unit) {
						found1 = true;
						param2 = m.getTarget();
					}
					if (m.getTarget()==param1 && m.getSource()!=null && m.getSource().getUnit()==unit) {
						found2 = true;
						param2 = m.getSource();
					}					
				}
				if (!first) {
					result = result + ", ";
				}
				if (found1 || found2) {
					result = result + param2.getName();
				} else {
					result = result + "?";
				}
				first = false;
			}
			result = result + ")";
		}
		
		return result;
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
	 * Parse an invocation name and parameters.
	 */
	protected CommandResult doParsing(View nameView, String value) throws ExecutionException {

		// Do the parsing:
		String[] parsed = parseNameAndParams(value);

		// It is the label, but we need the node:
		View invocationView = (View) nameView.eContainer();
		View compartmentView = (View) invocationView.eContainer();
		View unitView = (View) compartmentView.eContainer();
		
		// Get the transformation unit:
		Unit unit = (Unit) unitView.getElement();
		
		// Locate the position of the current invocation view:
		int position = UnitEditHelper.getInvocationViews(unitView, true).indexOf(invocationView);
		if (position<0) {
			return CommandResult.newErrorCommandResult("Error locating position of invocation");
		}
		
		// Compute possible target candidates:
		Module module = (Module) nameView.getDiagram().getElement();
		List<Unit> candidates = new ArrayList<Unit>();
		candidates.addAll(module.getUnits());

		// Find the right one:
		for (Unit target : candidates) {
			if (parsed[0].equals(target.getName())) {
				
				// Update the parent unit:
				if (unit instanceof SequentialUnit) {
					((SequentialUnit) unit).getSubUnits().set(position, target);
				}
				else if (unit instanceof PriorityUnit) {
					((PriorityUnit) unit).getSubUnits().set(position, target);
				}
				else if (unit instanceof IndependentUnit) {
					((IndependentUnit) unit).getSubUnits().set(position, target);
				}
				else if (unit instanceof LoopUnit) {
					((LoopUnit) unit).setSubUnit(target);
				}
				else if (unit instanceof IteratedUnit) {
					((IteratedUnit) unit).setSubUnit(target);
				}
				else if (unit instanceof ConditionalUnit) {
					if (invocationView==UnitEditHelper
							.getInvocationView(unitView, InvocationViewKey.IF)) {
						((ConditionalUnit) unit).setIf(target);
					}
					else if (invocationView==UnitEditHelper
							.getInvocationView(unitView, InvocationViewKey.THEN)) {
						((ConditionalUnit) unit).setThen(target);
					}
					else if (invocationView==UnitEditHelper
							.getInvocationView(unitView, InvocationViewKey.ELSE)) {
						((ConditionalUnit) unit).setElse(target);
					}
				}
				else {
					// Error.
					return CommandResult.newErrorCommandResult("Unknown transformation unit type: " + unit.eClass().getName());
				}
				
				// Update the views:
				invocationView.setElement(target);
				nameView.setElement(target);
				
				// Update the parameter mappings:
				for (int i=1; i<parsed.length; i++) {
					if (target.getParameters().size()<=(i-1)) {
						break;
					}
					Parameter targetParam = target.getParameters().get(i-1);
					
					// Remove all old parameter mappings:
					ParameterMapping mapping;
					Iterator<ParameterMapping> mappings = unit.getParameterMappings().iterator();
					while (mappings.hasNext()) {
						mapping = mappings.next();
						if (mapping.getSource()==targetParam || mapping.getTarget()==targetParam) {
							mappings.remove();
						}
					}
					
					// Find the used parameter in the parent unit:
					Parameter unitParam = unit.getParameter(parsed[i]);
					if (unitParam!=null) {
						
						// Now add the right parameter mappings:
						mapping = HenshinFactory.eINSTANCE.createParameterMapping();
						mapping.setSource(unitParam);
						mapping.setTarget(targetParam);
						unit.getParameterMappings().add(mapping);
						
						mapping = HenshinFactory.eINSTANCE.createParameterMapping();
						mapping.setSource(targetParam);
						mapping.setTarget(unitParam);
						unit.getParameterMappings().add(mapping);
						
					}
				}
				
				// Delete all parameter mappings with orphaned parameters:
				Iterator<ParameterMapping> mappings = unit.getParameterMappings().iterator();
				while (mappings.hasNext()) {
					ParameterMapping mapping = mappings.next();
					if (mapping.getSource().getUnit()==null || mapping.getTarget().getUnit()==null) {
						mappings.remove();
					}
				}		
				
				// Done:
				return CommandResult.newOKCommandResult();
			}
		}
		
		// Error.
		return CommandResult.newErrorCommandResult("Unknown transformation unit: " + value);
		
	}
	
	/*
	 * Parse names and parameters.
	 */
	private String[] parseNameAndParams(String value) throws ExecutionException {
		String name;
		String[] params;
		int openParen = value.indexOf('(');
		if (openParen>=0) {
			int closeParen = value.indexOf(')');
			if (closeParen<openParen) {
				throw new ExecutionException("Syntax error");
			}
			name = value.substring(0, openParen);
			params = value.substring(openParen+1, closeParen).split(",");
			for (int i=0; i<params.length; i++) {
				params[i] = params[i].trim();
			}
		} else {
			name = value.trim();
			params = new String[0];
		}
		String[] result = new String[params.length+1];
		result[0] = name;
		System.arraycopy(params, 0, result, 1, params.length);
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature(java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature==HenshinPackage.eINSTANCE.getNamedElement_Name()) return true;
		if (feature==HenshinPackage.eINSTANCE.getUnit_Parameters()) return true;
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
