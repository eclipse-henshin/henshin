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
package org.eclipse.emf.henshin.matching.constraints;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.emf.ecore.EClass;

public class Variable {
	private TypeConstraint typeConstraint;
	private Collection<AttributeConstraint> attributeConstraints;
	private Collection<DanglingConstraint> danglingConstraints;
	private Collection<ReferenceConstraint> referenceConstraints;
	private Collection<ParameterConstraint> parameterConstraints;
	private Collection<ContainmentConstraint> containmentConstraints;
	
	/**
	 * Constructor<br>
	 * Creates the related {@link TypeConstraint} already.
	 * 
	 * @param type
	 */
	public Variable(EClass type) {
		typeConstraint = new TypeConstraint(type);
		
		attributeConstraints = new HashSet<AttributeConstraint>();
		danglingConstraints = new HashSet<DanglingConstraint>();
		referenceConstraints = new HashSet<ReferenceConstraint>();
		parameterConstraints = new HashSet<ParameterConstraint>();
		containmentConstraints = new HashSet<ContainmentConstraint>();
	}
	
	/**
	 * @param constraint
	 */
	public void addConstraint(AttributeConstraint constraint) {
		attributeConstraints.add((AttributeConstraint) constraint);
	}
	
	/**
	 * @param constraint
	 */
	public void addConstraint(DanglingConstraint constraint) {
		danglingConstraints.add((DanglingConstraint) constraint);
	}
	
	/**
	 * @param constraint
	 */
	public void addConstraint(ReferenceConstraint constraint) {
		referenceConstraints.add((ReferenceConstraint) constraint);
	}
	
	/**
	 * @param constraint
	 */
	public void addConstraint(ParameterConstraint constraint) {
		parameterConstraints.add((ParameterConstraint) constraint);
	}
	
	/**
	 * @param constraint
	 */
	public void addConstraint(ContainmentConstraint constraint) {
		containmentConstraints.add((ContainmentConstraint) constraint);
	}
	
	/**
	 * @return
	 */
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
	
	/**
	 * @return the containmentConstraints
	 */
	public Collection<ContainmentConstraint> getContainmentConstraints() {
		return containmentConstraints;
	}
}
