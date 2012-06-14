/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

/**
 * Match finder variables.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class Variable {
	
	public final TypeConstraint typeConstraint;
	public final List<AttributeConstraint> attributeConstraints;
	public final List<DanglingConstraint> danglingConstraints;
	public final List<ReferenceConstraint> referenceConstraints;
	public final List<ParameterConstraint> parameterConstraints;
	public final List<ContainmentConstraint> containmentConstraints;
	
	/**
	 * Constructor<br>
	 * Creates the related {@link TypeConstraint} already.
	 * 
	 * @param type
	 */
	public Variable(EClass type) {
		this(type, false);
	}
	
	/**
	 * Constructor<br>
	 * Creates the related {@link TypeConstraint} already.
	 * 
	 * @param type
	 */
	public Variable(EClass type, boolean strictTyping) {
		typeConstraint = new TypeConstraint(type, strictTyping);
		attributeConstraints = new ArrayList<AttributeConstraint>();
		danglingConstraints = new ArrayList<DanglingConstraint>();
		referenceConstraints = new ArrayList<ReferenceConstraint>();
		parameterConstraints = new ArrayList<ParameterConstraint>();
		containmentConstraints = new ArrayList<ContainmentConstraint>();
	}
	
}
