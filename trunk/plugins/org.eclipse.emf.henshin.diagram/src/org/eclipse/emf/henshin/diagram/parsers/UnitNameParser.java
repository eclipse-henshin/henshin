/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.diagram.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
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

/**
 * Parser for unit names including its parameters. If a list of parameters names
 * is given together with the unit name, the real parameter list is synchronized
 * with the given list, i.e., new parameters may be created, existing parameters
 * may be removed, and existing parameters may be moved in the list.
 * 
 * 
 * @generated NOT
 * @author Christian Krause
 * @author Stefan Jurack (sjurack)
 */
public class UnitNameParser extends AbstractParser {
	
	/**
	 * If this pattern matches, a unit name is given, which may be followed by a
	 * comma-separated list of parameter names in brackets. Empty brackets are
	 * allowed as well and spaces may be between all names, brackets and commas.
	 * E.g., the following unit names are allowed (separated by semicolon):
	 * unit; unit(); unit(x); unit ( x ); unit(x, y) etc.
	 * 
	 * 
	 * The indices are as follows:<br>
	 * 1: unit name <br>
	 * 2: comma-separated list of parameter names (optional)
	 */
	private static final Pattern UNIT_NAME_PATTERN = Pattern
			.compile("^\\s*(\\w*?)\\s*(?:\\s*\\(([\\w,\\s]*)\\s*\\))?$");
	
	private static final String PARAMETER_SEPARATOR = ",";
	
	// View of the unit to be edited:
	protected View unitView;
	
	/**
	 * Default constructor.
	 */
	public UnitNameParser(View view) {
		super(new EAttribute[] { HenshinPackage.eINSTANCE.getNamedElement_Name() });
		this.unitView = view;
	}
	
	/*
	 * Helper method to check whether a unit is empty.
	 */
	protected boolean isUnitEmpty(Unit unit) {
		return unit.getSubUnits(false).isEmpty() && unit.getParameters().isEmpty();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {
		Unit unit = (Unit) unitView.getElement();
		
		final StringBuilder sb = new StringBuilder();
		
		// Compute the display name:
		final String name = unit.getName();
		sb.append((name == null) ? "" : name);
		
		// Compute the parameters:
		final List<Parameter> params = unit.getParameters();
		final int paramCount = params.size();
		if (paramCount > 0) {
			sb.append("(");
			for (int i=0; i<paramCount; i++) {
				if (i>0) sb.append(", ");
				sb.append(params.get(i).getName());
				if (params.get(i).getType()!=null) {
					EClassifier type = params.get(i).getType();
					sb.append(":" + type.getName());
				}
			}
			sb.append(")");
		}
		
		if (unit instanceof IteratedUnit) {
			String it = ((IteratedUnit) unit).getIterations();
			if (it==null || it.trim().length()==0) it = "?"; 
			sb.append(" [" + it + "]");
		}
		
		// Compile the title:
		return sb.toString();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {
		Unit unit = (Unit) unitView.getElement();
		return unit.eClass().getName() + " " + getEditString(element, flags);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand
	 * (org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
	 */
	public ICommand getParseCommand(IAdaptable element, final String value, int flags) {
		
		// Resolve the rule view:
		final View view = (View) element.getAdapter(View.class);
		
		// Get the editing domain:
		final TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(view
				.getElement());
		if (domain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		
		// Create parse command:
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain,
				"Parse Unit Name", null) {
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info)
					throws ExecutionException {
				return doParsing(value);
			}
		};
		return command;
		
	}
	
	/*
	 * Parse a rule name and the optional root node.
	 */
	protected CommandResult doParsing(String value) throws ExecutionException {
		
		// We need the unit:
		Unit unit = (Unit) unitView.getElement();
		
		// Iterated unit? Try to parse the number of iterations:
		if (unit instanceof IteratedUnit) {
			int index = value.indexOf('[');
			if (index>=0) {
				String it = value.substring(index+1);
				value = value.substring(0, index);
				index = it.indexOf(']');
				if (index>=0) {
					it = it.substring(0, index).trim();
					if (it.length()==0 || it.equals("?")) it = null;
					((IteratedUnit) unit).setIterations(it);
				}
			}
		}
		
		// TODO Support parsing of parameter types
		
		value = value.trim();
		final Matcher matcher = UNIT_NAME_PATTERN.matcher(value);
		
		if (!matcher.matches()) // check for valid type and parameters
			return CommandResult.newErrorCommandResult("Error parsing unit name");
		
		// We can be sure that a name exists!
		String name = matcher.group(1).trim();
		
		String paraString = matcher.group(2);
		
		if (paraString != null && !paraString.trim().isEmpty()) {
			String[] paraStrings = paraString.split(PARAMETER_SEPARATOR);
			
			// remove duplicates and clean the names from leading and trailing
			// whitespace
			List<String> paramStringList = new ArrayList<String>(paraStrings.length);
			for (String s : paraStrings) {
				final String st = s.trim();
				if (!paramStringList.contains(st)) paramStringList.add(st);
			}// for
			paraStrings = paramStringList.toArray(new String[paramStringList.size()]);
			
			// clean the names of actual parameters as well
			final List<Parameter> currentParameters = unit.getParameters();
			for (Parameter p : currentParameters) {
				// remove leading and trailing whitespace
				if (p.getName() != null) p.setName(p.getName().trim());
			}// for
			
			/*
			 * Note, if the name of an existing parameter appears in the
			 * parameter list of the given string, the actual parameter is NOT
			 * deleted but moved! Therefore, the algorithm below works as
			 * follows: (1) Remove obsolete parameters, (2) Create new
			 * parameters, (3) Adjust the parameters order.
			 */

			// remove parameters not occurring in the new list
			final Iterator<Parameter> it = currentParameters.iterator();
			while (it.hasNext()) {
				final Parameter p = it.next();
				if (!paramStringList.contains(p.getName()))
					it.remove();
				else
					paramStringList.remove(p.getName());
			}// while
			
			// add new parameters
			for (String s : paramStringList) {
				final Parameter p = HenshinFactory.eINSTANCE.createParameter();
				p.setName(s);
				currentParameters.add(p);
			}// for
			
			// At this point, the actual parameter list contains all necessary
			// parameters having unique names.
			
			paramStringList = Arrays.asList(paraStrings);
			/*
			 * Correct the order of parameters. Not that beautiful algorithm but
			 * pretty simple ;-)
			 */
			for (int i = 0; i < paraStrings.length; i++) {
				final Parameter p = currentParameters.get(i);
				int correctIndex = paramStringList.indexOf(p.getName());
				if (correctIndex != i) {
					currentParameters.remove(p);
					currentParameters.add(correctIndex, p);
					i = 0;
				}// if
			}// for
			
		} else {
			unit.getParameters().clear();
		}
		
		// Delete all parameter mappings with orphaned parameters:
		Iterator<ParameterMapping> mappings = unit.getParameterMappings().iterator();
		while (mappings.hasNext()) {
			ParameterMapping mapping = mappings.next();
			if (mapping.getSource().getUnit()==null || mapping.getTarget().getUnit()==null) {
				mappings.remove();
			}
		}
		
		// Now set the name:
		unit.setName(name);
		
		// Done.
		return CommandResult.newOKCommandResult();
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature
	 * (java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature == HenshinPackage.eINSTANCE.getNamedElement_Name()) return true;
		if (feature == HenshinPackage.eINSTANCE.getUnit_Parameters()) return true;
		if (feature == HenshinPackage.eINSTANCE.getIteratedUnit_Iterations()) return true;
		if (feature == EcorePackage.eINSTANCE.getEModelElement_EAnnotations()) return true;
		if (feature == EcorePackage.eINSTANCE.getEAnnotation_References()) return true;
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#isValidEditString
	 * (org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}
	
}
