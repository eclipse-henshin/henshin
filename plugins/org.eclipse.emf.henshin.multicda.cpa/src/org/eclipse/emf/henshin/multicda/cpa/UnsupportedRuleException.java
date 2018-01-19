/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;

/**
 * Unsupported rule exception class. For any kind of unsupported input data.
 * 
 * @author Kristopher Born
 *
 */
public class UnsupportedRuleException extends Exception {

	private static final long serialVersionUID = -5179504051296705955L;

	private String detailMessage = "";

	public final static String unsupportedAttributeType = "Unsupported attribute type";
	public final static String differentDomain = "The provided rules are based on different domains";
	public final static String missingDomainImport = "Modules with missind imports are not supported";
	public final static String multipleDomainImport = "Multiple imports in the module are not supported.";
	public final static String unsupportedJavaScriptExpression = "JavaScript expressions are not supported by the CPA!";
	public final static String inconsistentInjectiveMatchingOptions = "The provided rules have inconsistent injective matching options. Only homogenous rule sets, which all have 'injective matching' activated or deactivated are supported.";
	public final static String inconsistentDanglingOptions = "The provided rules have inconsistent Dangling options. Only homogenous rule sets, which all have 'Check Dangling' activated or deactivated, are supported.";
	public final static String unsupportedAmalgamationRules = "The rules contain nested multi-rules (called amalgamation) which are unsupported yet.";
	public final static String unsupportedUnits = "Units are not supported yet.";
	public final static String unsupportedApplicationCondition_noSubformulasOfNOT = "Unsupported application condition. Only ‘Nested Conditions’ are supported as sub formulas of ‘Not’.";
	public final static String unsupportedApplicationCondition_noBinaryFormulaOfKindOR = "Unsupported application condition. BinaryFormulas of type ‘Or’ are not supported. Hint: try to reformulate using De Morgan’s law.";
	public final static String unsupportedApplicationCondition_noBinaryFormulaOfKindXOR = "Unsupported application condition. BinaryFormulas of type ‘Or’ are not supported.";
	public final static String noInputRule = "The list of rules to analyze is not allowed to be empty!";
	public final static String exportFailed = "Error occurred while exporting the metamodel and the rules to AGG.";
	

	/**
	 * Default constructor.
	 * 
	 * @param message The feedback message.
	 */
	UnsupportedRuleException(String message) {
		super(message);
	};

	/**
	 * Extended constructor for unsupported data types of attributes.
	 * 
	 * @param rule The <code>rule</rule> in which the unsupported attribute occurs.
	 * @param attribute The attribute which is not supported.
	 */
	UnsupportedRuleException(Rule rule, EAttribute attribute) {
		super(unsupportedAttributeType);
		String dataType = attribute.getName();
		String attributeName = attribute.toString();
		String ruleName = rule.getName();
		detailMessage = "The data type >>" + dataType + "<< of attribute >>" + attributeName + "<< in rule >>"
				+ ruleName + "<< is not supported by the CPA!";
	};

	/**
	 * Extended constructor for unsupported JavaScript expressions.
	 * 
	 * @param rule The <code>rule</rule> in which the JavaScript expression occurs.
	 * @param attribute The attribute with the JavaScript expression.
	 */
	UnsupportedRuleException(Rule rule, Attribute attribute) {
		super(unsupportedJavaScriptExpression);
		String attributeName = attribute.toString();
		String ruleName = rule.getName();
		detailMessage = "It seems the Attribute >>" + attributeName + "<< in rule >>" + ruleName
				+ "<< is a JavaScript expression";
	};

	/**
	 * Extended constructor for unsupported multiple imports per module.
	 * 
	 * @param rule The <code>rule</rule> in which the multiple imports occur.
	 */
	public UnsupportedRuleException(Rule rule) {
		super(multipleDomainImport);
		String moduleName = rule.getModule().getName();
		String ruleName = rule.getName();
		detailMessage = "The module >>" + moduleName + "<< of rule >>" + ruleName + "<< contains multiple imports";
	}

	/**
	 * Returns a <code>String</code> with the detailed exception message.
	 * 
	 * @return A <code>String</code> with the detailed exception message.
	 */
	public String getDetailedMessage() {
		return getMessage() + " " + detailMessage;
	}

}
