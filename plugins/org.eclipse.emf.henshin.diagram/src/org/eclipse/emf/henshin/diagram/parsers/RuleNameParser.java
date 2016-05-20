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

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.henshin.diagram.edit.helpers.RootObjectEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.ModuleEditHelper;
import org.eclipse.emf.henshin.diagram.edit.helpers.RuleEditHelper;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.notation.View;

/**
 * Parser for rule names and root objects.
 * @generated NOT
 * @author Christian Krause
 */
public class RuleNameParser extends UnitNameParser {

	/**
	 * Default constructor.
	 */
	public RuleNameParser(View view) {
		super(view);
		if (!RuleEditHelper.isRuleView(view)) {
			throw new IllegalArgumentException();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.UnitNameParser#isUnitEmpty(org.eclipse.emf.henshin.model.Unit)
	 */
	@Override
	protected boolean isUnitEmpty(Unit unit) {
		if (unit instanceof Rule) {
			Rule rule = (Rule) unit;
			return rule.getLhs().getNodes().isEmpty()
					&& rule.getRhs().getNodes().isEmpty()
					&& rule.getParameters().isEmpty();
		} else {
			return super.isUnitEmpty(unit);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.UnitNameParser#getPrintString(org.eclipse.core.runtime.IAdaptable, int)
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {

		// Compute the root object:
		String root = "";
		Node rootObject = RootObjectEditHelper.getRootObject(unitView);
		if (rootObject != null) {
			root = " @" + rootObject.getType().getName();
		}

		// Compile the title:
		return (super.getPrintString(element, flags) + root);

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.UnitNameParser#doParsing(java.lang.String)
	 */
	@Override
	protected CommandResult doParsing(String value) throws ExecutionException {

		// We need the rule:
		Rule rule = (Rule) unitView.getElement();

		// Parse the input:
		String name, rootType;

		// Separate the root type:
		int at = value.indexOf('@');
		if (at < 0) {
			name = value;
			rootType = null;
		} else {
			name = value.substring(0, at).trim();
			rootType = value.substring(at + 1).trim();
			if (rootType.isEmpty()) {
				rootType = null;
			}
		}

		// Update the name and the parameters:
		super.doParsing(name);

		// Update the root object:
		Node oldRoot = RootObjectEditHelper.getRootObject(unitView);

		// Do we need to set a new root object?
		if (rootType != null
				&& (oldRoot == null || !rootType.equals(oldRoot.getType()
						.getName()))) {

			// First find the proper class and initialize the new root:
			EClass rootClass = null;
			EClassifier[] classifiers = ModuleEditHelper.getEClassifiers(rule.getModule(), rootType);
			for (EClassifier classifier : classifiers) {
				if (classifier instanceof EClass) {
					rootClass = (EClass) classifier;
					break;
				}
			}

			// We change only if the new root type was found:
			if (rootClass != null) {
				RootObjectEditHelper.setRootObjectType(unitView, rootClass);
			}
		}

		// Do we have to erase the current root object?
		if (rootType == null && oldRoot != null) {
			RootObjectEditHelper.setRootObject(unitView, null);
		}

		// Done.
		return CommandResult.newOKCommandResult();

	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.diagram.parsers.UnitNameParser#handleParametersChange(org.eclipse.emf.henshin.model.Unit)
	 */
	@Override
	protected void handleParametersChange(Unit unit) {
		final Rule rule = (Rule) unit;
		for (Rule multiRule : rule.getMultiRules()) {
			while (multiRule.getParameters().size() < rule.getParameters().size()) {
				multiRule.getParameters().add(HenshinFactory.eINSTANCE.createParameter());
			}
			while (multiRule.getParameters().size() > rule.getParameters().size()) {
				multiRule.getParameters().remove(multiRule.getParameters().size()-1);
			}
			for (int i=0; i<rule.getParameters().size(); i++) {
				Parameter s = rule.getParameters().get(i);
				Parameter t = multiRule.getParameters().get(i);
				t.setName(s.getName());
				t.setType(s.getType());
				t.setKind(s.getKind());
				t.setDescription(s.getDescription());
			}
			handleParametersChange(multiRule);
		}
	}

}
