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

/**
 * @deprecated Use {@link Unit} instead.
 */
public interface TransformationUnit extends NamedElement {
	
	boolean isActivated();

	void setActivated(boolean value);

	/**
	 * @deprecated Use getModule()
	 */
	TransformationSystem getTransformationSystem();

	EList<Parameter> getParameters();

	Parameter getParameter(String parameter);

	/**
	 * @deprecated Use {@link #getParameter(String)}
	 */
	Parameter getParameterByName(String parameter);

	EList<ParameterMapping> getParameterMappings();
	
	EList<Unit> getSubUnits(boolean deep);

} // TransformationUnit
