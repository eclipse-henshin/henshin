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
	
	EList<Rule> getRules();

	EList<EPackage> getImports();

	EList<TransformationUnit> getTransformationUnits();
	
	@Deprecated
	EList<Graph> getInstances();

	TransformationUnit getTransformationUnit(String unitName);

	Rule getRule(String ruleName);

} // TransformationSystem
