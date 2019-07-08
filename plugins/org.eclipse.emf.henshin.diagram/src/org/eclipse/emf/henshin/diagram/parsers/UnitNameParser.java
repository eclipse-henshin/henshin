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
import org.eclipse.emf.henshin.diagram.edit.helpers.ModuleEditHelper;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IteratedUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterKind;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.util.HenshinModelCleaner;
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
 * Parser for unit names including its parameters. If a list of parameters names
 * is given together with the unit name, the real parameter list is synchronized
 * with the given list, i.e., new parameters may be created, existing parameters
 * may be removed, and existing parameters may be moved in the list.
 * 
 * @generated NOT
 * @author Christian Krause
 * @author Stefan Jurack (sjurack)
 */
public class UnitNameParser extends AbstractAttributeParser {

	/**
	 * If this pattern matches, a unit name is given, which may be followed by a
	 * comma-separated list of parameter names in brackets. Empty brackets are
	 * allowed as well and spaces may be between all names, brackets and commas.
	 * E.g., the following unit names are allowed (separated by semicolon):
	 * unit; unit(); unit(x); unit ( x ); unit(x, y) etc. where x and y are
	 * parameters
	 * 
	 * The indices are as follows:<br>
	 * 1: unit name <br>
	 * 2: comma-separated list of parameter names (optional)
	 */
	private static final Pattern UNIT_NAME_PATTERN = Pattern.compile(
			"^\\s*(\\w*?)\\s*(?:\\s*\\(([" + ParameterKind.getValidStringItemsRegex() + "\\w,\\:\\s]*)\\s*\\))?$");

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
	 * 
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getEditString(
	 * org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getEditString(IAdaptable element, int flags) {

		// We use getPrintString():
		Unit unit = (Unit) unitView.getElement();
		String result = getPrintString(element, flags);

		// We just need to remove the type of the unit in the beginning:
		if (result.startsWith(unit.eClass().getName())) {
			result = result.replaceFirst(unit.eClass().getName(), "").trim();
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getPrintString(
	 * org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {

		// The actual pretty-printing is done already in the unit
		// implementation:
		Unit unit = (Unit) unitView.getElement();
		return unit.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gmf.runtime.common.ui.services.parser.IParser#getParseCommand
	 * (org.eclipse.core.runtime.IAdaptable, java.lang.String, int)
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
		AbstractTransactionalCommand command = new AbstractTransactionalCommand(domain, "Parse Unit Name", null) {
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
			if (index >= 0) {
				String it = value.substring(index + 1);
				value = value.substring(0, index);
				index = it.indexOf(']');
				if (index >= 0) {
					it = it.substring(0, index).trim();
					if (it.length() == 0 || it.equals("?"))
						it = null;
					((IteratedUnit) unit).setIterations(it);
				}
			}
		}

		// Parse using the regular expression:
		value = value.trim();
		final Matcher matcher = UNIT_NAME_PATTERN.matcher(value);
		if (!matcher.matches()) {
			return CommandResult.newErrorCommandResult("Error parsing unit name");
		}

		// We can be sure that a name exists!
		String name = matcher.group(1).trim();

		// Parameters:
		String parametersString = matcher.group(2);
		if (parametersString != null && !parametersString.trim().isEmpty()) {

			// Split up the parameters:
			String[] paramStrings = parametersString.split(PARAMETER_SEPARATOR);
			// Separate names, types and kinds:
			String[] names = new String[paramStrings.length];
			String[] types = new String[paramStrings.length];
			ParameterKind[] kinds = new ParameterKind[paramStrings.length];

			// Make sure the parameter strings are correct (<kind>
			// <name>:<type>)
			for (int i = 0; i < paramStrings.length; i++) {
				String[] splitKind = paramStrings[i].trim().split("\\s+");
				String[] splitType = paramStrings[i].trim().split("\\:");
				String[] splitTypeAndKind = paramStrings[i].trim().split("[\\:\\s+]");
				int parameterNameIndex = 0;

				if (splitKind.length >= 2) {
					kinds[i] = ParameterKind.getByString(splitKind[0].trim());
					if (kinds[i] != null) {
						parameterNameIndex += 1;
					}
				}
				if (splitType.length >= 2) {
					types[i] = splitType[1].trim();
				}

				names[i] = splitTypeAndKind[parameterNameIndex];
			}

			// Remove duplicate names (and the corresponding kinds and types):
			for (int i = 1; i < names.length; i++) {
				for (int j = 0; j < i; j++) {
					if (names[i] != null && names[j] != null && names[i].equals(names[j])) {
						kinds[i] = null;
						names[i] = null;
						types[i] = null;
						break;
					}
				}
			}

			// The parameters:
			final List<Parameter> params = unit.getParameters();

			// Delete parameters without names, and remove trailing spaces from
			// the others:
			Iterator<Parameter> it = params.iterator();
			while (it.hasNext()) {
				Parameter p = it.next();
				if (p.getName() == null) {
					it.remove();
				} else {
					p.setName(p.getName().trim());
				}
			}

			// Now make sure that there are no parameters with the same names:
			for (int i = 1; i < params.size(); i++) {
				Parameter p = params.get(i);
				for (int j = 0; j < i; j++) {
					if (p.getName().equals(params.get(j).getName())) {
						params.remove(i--);
						break;
					}
				}
			}

			/*
			 * Note, if the name of an existing parameter appears in the
			 * parameter list of the given string, the actual parameter is NOT
			 * deleted but moved! We do this as follows: 
			 * (1) Remove obsolete parameters
			 * (2) Create new parameters
			 * (3) Adjust the parameters
			 * order.
			 */

			// Remove parameters not occurring in the new list:
			it = params.iterator();
			while (it.hasNext()) {
				String n = it.next().getName();
				boolean found = false;
				for (int i = 0; i < names.length; i++) {
					if (names[i] != null && names[i].equals(n)) {
						found = true;
						break;
					}
				}
				if (!found) {
					it.remove();
				}
			}

			// Add the new parameters:
			for (int i = 0; i < names.length; i++) {
				if (names[i] != null && unit.getParameter(names[i]) == null) {
					unit.getParameters().add(HenshinFactory.eINSTANCE.createParameter(names[i]));
				}
			}

			/*
			 * At this point, all parameters exist and have their correct names
			 */

			// Now sort the parameters:
			int index = 0;
			for (int i = 0; i < names.length; i++) {
				if (names[i] != null) {
					Parameter p = unit.getParameter(names[i]);
					unit.getParameters().move(index++, p);
				}
			}

			// Now we only need to set their kinds and types:
			Module module = unit.getModule();
			for (int i = 0; i < names.length; i++) {
				if (names[i] != null) {
					if (types[i] != null) {
						EClassifier type = null;
						EClassifier[] classifiers = ModuleEditHelper.getEClassifiers(module, types[i]);

						if (classifiers != null && classifiers.length > 0) {
							type = classifiers[0];
						}
						unit.getParameter(names[i]).setType(type);
					}

					ParameterKind kind = kinds[i];
					unit.getParameter(names[i]).setKind(kind);
				}
			}

		} else {

			// Otherwise reset the parameters list.
			unit.getParameters().clear();

		}

		// Handle parameters change:
		handleParametersChange(unit);

		// Clean up the parameter mappings (for all units):
		HenshinModelCleaner.cleanModule(unit.getModule());

		// Now set the name (make a dummy change first so that we definitely get
		// a notification):
		unit.setName(name + "_");
		unit.setName(name);

		// Done.
		return CommandResult.newOKCommandResult();

	}

	protected void handleParametersChange(Unit unit) {
		// nothing to do
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.emf.henshin.diagram.parsers.AbstractParser#isAffectingFeature
	 * (java.lang.Object)
	 */
	@Override
	protected boolean isAffectingFeature(Object feature) {
		if (feature == HenshinPackage.eINSTANCE.getNamedElement_Name())
			return true;
		if (feature == HenshinPackage.eINSTANCE.getUnit_Parameters())
			return true;
		if (feature == HenshinPackage.eINSTANCE.getParameter_Type())
			return true;
		if (feature == HenshinPackage.eINSTANCE.getIteratedUnit_Iterations())
			return true;
		if (feature == EcorePackage.eINSTANCE.getEModelElement_EAnnotations())
			return true;
		if (feature == EcorePackage.eINSTANCE.getEAnnotation_References())
			return true;
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gmf.runtime.common.ui.services.parser.IParser#
	 * isValidEditString (org.eclipse.core.runtime.IAdaptable, java.lang.String)
	 */
	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

}
