/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.internal.matching;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.henshin.internal.constraints.AttributeConstraint;
import org.eclipse.emf.henshin.internal.constraints.ParameterConstraint;
import org.eclipse.emf.henshin.internal.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.internal.constraints.TypeConstraint;

public class Variable<TType, TNode> {
	private TypeConstraint<TType, TNode> typeConstraint;
	private Collection<AttributeConstraint<TNode>> attributeConstraints;
	private Collection<ParameterConstraint<TNode>> parameterConstraints;
	private Collection<ReferenceConstraint<TNode>> referenceConstraints;

	public Variable() {
		attributeConstraints = new ArrayList<AttributeConstraint<TNode>>();
		parameterConstraints = new ArrayList<ParameterConstraint<TNode>>();
		referenceConstraints = new ArrayList<ReferenceConstraint<TNode>>();
	}
	

	
	public void setTypeConstraint(TypeConstraint<TType, TNode> typeConstraint) {
		this.typeConstraint = typeConstraint;
	}

	public TypeConstraint<TType, TNode> getTypeConstraint() {
		return typeConstraint;
	}

	public Collection<AttributeConstraint<TNode>> getAttributeConstraints() {
		return attributeConstraints;
	}

	public Collection<ParameterConstraint<TNode>> getParameterConstraints() {
		return parameterConstraints;
	}

	public Collection<ReferenceConstraint<TNode>> getReferenceConstraints() {
		return referenceConstraints;
	}
}
