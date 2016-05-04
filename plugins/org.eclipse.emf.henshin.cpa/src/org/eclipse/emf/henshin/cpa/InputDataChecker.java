/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.cpa;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.Xor;
import org.eclipse.emf.henshin.model.exporters.HenshinAGGExporter;

/**
 * This checker provides a set of checks to ensure that the rules are suitable for the current implementation of the
 * critical pair analysis.
 * 
 * @author Kristopher Born
 *
 */
public class InputDataChecker {

	private static final InputDataChecker instance = new InputDataChecker();

	HenshinAGGExporter exporter = new HenshinAGGExporter();

	/**
	 * Default singleton constructor.
	 */
	private InputDataChecker() {

	}

	public static InputDataChecker getInstance() {
		return instance;
	}

	/**
	 * Checks the list of rules in regard of being suitable for applying this critical pair analysis implementation.
	 * 
	 * @param rules The rules to be checked in regard of being suitable for applying this critical pair analysis
	 *            implementation.
	 * @return <code>true</code> if all of the rules are suitable for applying this critical pair analysis
	 *         implementation.
	 * @throws UnsupportedRuleException On the case of any unsuitable rules.
	 */
	public boolean check(List<org.eclipse.emf.henshin.model.Rule> rules) throws UnsupportedRuleException {

		boolean sameDomainModel = checkDomainModels(rules);
		boolean consistentOptions = consistentDanglingOption(rules);
		boolean allAttributeTypesSupported = checkAttributeTypes(rules);
		boolean noAmalgamation = checkForAmalgamation(rules);
		boolean allApplicationConditionsSupported = checkApplicationConditions(rules);
		// boolean noAttributeValue

		return sameDomainModel && consistentOptions && allAttributeTypesSupported && noAmalgamation
				&& allApplicationConditionsSupported;
	}

	/**
	 * Checks the rules for the same domain model based on the nsUri. This check depends on a reliable nsUri, which is
	 * being adjusted on editing the metamodel. A stronger check could use EMFcompare to check the metamodels.
	 * 
	 * @param rules The rules which are checked regarding a common meta model.
	 * @return <code>true</code> if all of the rules are based on the same meta model.
	 * @throws DifferentDomainException On any occurrence of different meta models within the set of rules.
	 * @throws MultipleDomainImportException On any occurrence of more than one meta model in a rule.
	 */
	private boolean checkDomainModels(List<org.eclipse.emf.henshin.model.Rule> rules) throws UnsupportedRuleException {
		String nsUri = null;
		for (org.eclipse.emf.henshin.model.Rule rule : rules) {
			// take the first EPackage and look at his URI
			if (rule.getModule().getImports().size() == 0)
				throw new UnsupportedRuleException(UnsupportedRuleException.missingDomainImport);
			if (rule.getModule().getImports().size() > 1)
				throw new UnsupportedRuleException(UnsupportedRuleException.multipleDomainImport);
			if (nsUri == null) {
				nsUri = rule.getModule().getImports().get(0).getNsURI();
			} else if (!rule.getModule().getImports().get(0).getNsURI().equals(nsUri)) {
				throw new UnsupportedRuleException(UnsupportedRuleException.differentDomain);
			}
		}
		return true;
	}

	/**
	 * Checks all rules whether they contain any amalgamation.
	 * 
	 * @param rules The rules which are checked for containing amalgamation.
	 * @return <code>true</code> if all of the rules do not contain any amalgamation.
	 * @throws UnsupportedRuleException On any found amalgamation within the set of rules.
	 */
	private boolean checkForAmalgamation(List<org.eclipse.emf.henshin.model.Rule> rules)
			throws UnsupportedRuleException {
		for (org.eclipse.emf.henshin.model.Rule rule : rules) {
			if (rule.getMultiRules().size() > 0)
				throw new UnsupportedRuleException(UnsupportedRuleException.unsupportedAmalgamationRules);
		}
		return true;
	}

	/**
	 * Checks whether the passed rules contain unsupported data types.
	 * 
	 * @param rulesToBeChecked The rules, of which the contained attributes are checked regarding their data types.
	 * @return <code>true</code> if all the contained attributes are supported.
	 * @throws UnsupportedRuleException On found data types which are not supported.
	 */
	private boolean checkAttributeTypes(List<org.eclipse.emf.henshin.model.Rule> rulesToBeChecked)
			throws UnsupportedRuleException {

		for (final org.eclipse.emf.henshin.model.Rule rule : rulesToBeChecked) {
			ArrayList<Graph> lhsAndRhsGraph = new ArrayList<Graph>() {
				{
					add(rule.getLhs());
					add(rule.getRhs());
				}
			};

			for (Graph graph : lhsAndRhsGraph) {
				for (Node node : graph.getNodes()) {
					for (Attribute attribute : node.getAttributes()) {
						if (!HenshinAGGExporter.isSupportedPrimitiveType(attribute.getType().getEType())) {
							throw new UnsupportedRuleException(rule, attribute.getType());
						}
						// handling havaScript expressions
						Parameter param = rule.getParameter(attribute.getValue());
						if (attribute.getType().getEType() == EcorePackage.eINSTANCE.getEString()
								&& attribute.getConstant() == null && param == null) {
							throw new UnsupportedRuleException(rule, attribute);
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks whether all of the passed rules have a common setting of their 'dangling option'.
	 * 
	 * @param rulesToBeChecked The rules, which are checked regarding a common setting of the 'dangling option'.
	 * @return <code>true</code> if the dangling option of all provided rules are the same.
	 * @throws UnsupportedRuleException On inconsistent dangling options within the rule set.
	 */
	private boolean consistentDanglingOption(List<org.eclipse.emf.henshin.model.Rule> rulesToBeChecked)
			throws UnsupportedRuleException {

		boolean checkDanglingActivated = false;
		boolean checkDanglingDeactivated = false;
		boolean checkDanglingConsistent = true;

		boolean checkInjectiveMatchingActivated = false;
		boolean checkInjectiveMatchingDeactivated = false;
		boolean injectiveMatchingConsistent = true;

		for (org.eclipse.emf.henshin.model.Rule rule : rulesToBeChecked) {
			if (checkDanglingConsistent) {
				if (rule.isCheckDangling())
					checkDanglingActivated = true;
				else
					checkDanglingDeactivated = true;
				if (checkDanglingActivated && checkDanglingDeactivated) {
					checkDanglingConsistent = false;
					throw new UnsupportedRuleException(UnsupportedRuleException.inconsistentDanglingOptions);
				}
			}
			if (injectiveMatchingConsistent) {
				if (rule.isInjectiveMatching())
					checkInjectiveMatchingActivated = true;
				else
					checkInjectiveMatchingDeactivated = true;
				if (checkInjectiveMatchingActivated && checkInjectiveMatchingDeactivated) {
					injectiveMatchingConsistent = false;
					throw new UnsupportedRuleException(UnsupportedRuleException.inconsistentInjectiveMatchingOptions);
				}
			}
		}
		return checkDanglingConsistent && injectiveMatchingConsistent;
	}

	/**
	 * @throws UnsupportedRuleException
	 * 
	 */
	private boolean checkApplicationConditions(
			List<org.eclipse.emf.henshin.model.Rule> rulesToBeChecked)
			throws UnsupportedRuleException {
		/**
		 * TODO: - zuerst nur eine einzelne NAC und PAC zulassen - dann
		 * Konjunktionen von NACs und PACs zulassen. (Disjunktionen
		 * ausschließen) - später mit "DeMorgenschen Gesetzen"-Refactoring auch
		 * Disjunktionen zulassen, sofern diese zu Konjuktionen umgestellt
		 * werden können (oder wenn AGG Disjunktionen beherscht - für die CPA)
		 * 
		 * !! jeweils Beispiele finden und testen. ! die Beispiele für die
		 * folgestufen könenn bei der jeweils vorherigen Stufe genutzt werden,
		 * dass der Check diese erkennt.
		 */
		boolean rulesOK = true;
		for (org.eclipse.emf.henshin.model.Rule rule : rulesToBeChecked) {
			rulesOK &= checkFormula(rule.getLhs().getFormula());
			checkFormula(rule.getRhs().getFormula());
		}
		return rulesOK;
	}

	private boolean checkFormula(Formula formula)
			throws UnsupportedRuleException {
		// nur "AND"s und optional final "NOT" - nachfolgend eine
		// NestedCondition (alles andere wäre eine andere unäre oder binäre
		// Formel, die nicht unterstützt wird).
		if (formula instanceof BinaryFormula) {
			if (formula instanceof And) {
				return checkFormula(((BinaryFormula) formula).getLeft())
						&& checkFormula(((BinaryFormula) formula).getRight());
			} else if (formula instanceof Or) {
				// nicht unterstützt
				throw new UnsupportedRuleException(
						UnsupportedRuleException.unsupportedApplicationCondition_noBinaryFormulaOfKindOR);

			} else if (formula instanceof Xor) {
				// nicht unterstützt
				throw new UnsupportedRuleException(
						UnsupportedRuleException.unsupportedApplicationCondition_noBinaryFormulaOfKindXOR);
			}
		} else if (formula instanceof UnaryFormula) {
			if (formula instanceof Not) {
				// nach einem 'Not' darf nur noch eine nested condition folgen
				if (((UnaryFormula) formula).getChild() instanceof NestedCondition) {
					return true;
				} else {
					// es werden keine unter-formeln von "NOT" unterstützt durch
					// die CPA, da nicht äquivalent in AGG exportierbar
					throw new UnsupportedRuleException(
							UnsupportedRuleException.unsupportedApplicationCondition_noSubformulasOfNOT);
					// return false;
				}
			}
		}
		if (formula instanceof NestedCondition) {
			return true;
		}
		return false; // default
	}
}
