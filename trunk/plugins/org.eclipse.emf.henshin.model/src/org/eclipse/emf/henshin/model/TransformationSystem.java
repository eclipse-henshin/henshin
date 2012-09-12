/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;

/**
 * @deprecated Use {@link Module} instead.
 */
public interface TransformationSystem extends NamedElement {
	
	/**
	 * @deprecated Use getUnits() instead.
	 */
	EList<Rule> getRules();

	EList<EPackage> getImports();

	/**
	 * @deprecated Use getUnits() instead.
	 */
	EList<Unit> getTransformationUnits();
	
	/**
	 * @deprecated Will be removed.
	 */
	EList<Graph> getInstances();

	/**
	 * @deprecated Use getUnit(String name) instead.
	 */
	TransformationUnit getTransformationUnit(String unitName);

	/**
	 * @deprecated Use getUnit(String name) instead and cast the result to a rule.
	 */
	Rule getRule(String ruleName);

	/**
	 * @deprecated Use getUnit(String name) instead and cast the result to a rule.
	 */
	Rule findRuleByName(String ruleName);

} // TransformationSystem
