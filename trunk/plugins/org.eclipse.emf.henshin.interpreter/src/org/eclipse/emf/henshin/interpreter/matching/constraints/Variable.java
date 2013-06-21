/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;

/**
 * Match finder variable. Internal representation of nodes.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class Variable {
	
	// Type constraint:
	public final TypeConstraint typeConstraint;
	
	// Attribute constraints:
	public final List<AttributeConstraint> attributeConstraints;
	
	// Dangling constraints:
	public final List<DanglingConstraint> danglingConstraints;
	
	// Reference constraints:
	public final List<ReferenceConstraint> referenceConstraints;
	
	// Containment constraints:
	public final List<ContainmentConstraint> containmentConstraints;
	
	// User defined constraints:
	public final List<UserConstraint> userConstraints;

	// Whether this variable requires a final re-check:
	public boolean requiresFinalCheck;

	/**
	 * Constructor. Creates the related {@link TypeConstraint} already.
	 * @param type Type of the node to be matched.
	 */
	public Variable(EClass type) {
		this(type, false);
	}
	
	/**
	 * Constructor. Creates the related {@link TypeConstraint} already.
	 * @param type Type of the node to be matched.
	 * @param strictTyping Whether to use strict typing.
	 */
	public Variable(EClass type, boolean strictTyping) {
		typeConstraint = new TypeConstraint(type, strictTyping);
		attributeConstraints = new ArrayList<AttributeConstraint>();
		danglingConstraints = new ArrayList<DanglingConstraint>();
		referenceConstraints = new ArrayList<ReferenceConstraint>();
		containmentConstraints = new ArrayList<ContainmentConstraint>();
		userConstraints = new ArrayList<UserConstraint>();
		requiresFinalCheck = false;		
	}
	
}
