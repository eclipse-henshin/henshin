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
package org.eclipse.emf.henshin.internal.matching;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.internal.constraints.Constraint;

public class Variable {
	private TypeConstraint typeConstraint;
	private Collection<AttributeConstraint> attributeConstraints;
	private Collection<DanglingConstraint> danglingConstraints;
	private Collection<ReferenceConstraint> referenceConstraints;
	private Collection<ParameterConstraint> parameterConstraints;

	public Variable(EClass type) {
		typeConstraint = new TypeConstraint(type);
		
		attributeConstraints = new HashSet<AttributeConstraint>();
		danglingConstraints = new HashSet<DanglingConstraint>();
		referenceConstraints = new HashSet<ReferenceConstraint>();
		parameterConstraints = new HashSet<ParameterConstraint>();
	}
	
	public void addConstraint(Constraint constraint) {
		if (constraint instanceof AttributeConstraint) {
			attributeConstraints.add((AttributeConstraint) constraint);
		} else if (constraint instanceof DanglingConstraint) {
			danglingConstraints.add((DanglingConstraint) constraint);
		} else if (constraint instanceof ReferenceConstraint) {
			referenceConstraints.add((ReferenceConstraint) constraint);
		} else if (constraint instanceof ParameterConstraint) {
			parameterConstraints.add((ParameterConstraint) constraint);
		} else {
			throw new IllegalArgumentException("Unknown constraint");
		}
	}
	
	public TypeConstraint getTypeConstraint() {
		return typeConstraint;
	}

	/**
	 * @return the attributeConstraints
	 */
	public Collection<AttributeConstraint> getAttributeConstraints() {
		return attributeConstraints;
	}

	/**
	 * @return the danglingConstraints
	 */
	public Collection<DanglingConstraint> getDanglingConstraints() {
		return danglingConstraints;
	}

	/**
	 * @return the referenceConstraints
	 */
	public Collection<ReferenceConstraint> getReferenceConstraints() {
		return referenceConstraints;
	}

	/**
	 * @return the parameterConstraints
	 */
	public Collection<ParameterConstraint> getParameterConstraints() {
		return parameterConstraints;
	}
}
