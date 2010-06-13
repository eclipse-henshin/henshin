/**
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     CWI Amsterdam - initial API and implementation
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Helper class for deciding whether two state models are
 * equal and for calculating their hash codes.
 * @model
 * @generated
 */
public interface StateEqualityHelper extends EObject {
	
	/**
	 * Equality type <code>ECORE</code>.
	 * @generated NOT
	 */
	public static final int ECORE_EQUALITY = 0;
	
	/**
	 * Equality type <code>GRAPH</code>.
	 * @generated NOT
	 */
	public static final int GRAPH_EQUALITY = 1;
	
	/**
	 * Get the equality type used by this helper.
	 * @return the equality type.
	 * @see #setEqualityType(int)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateEqualityHelper_EqualityType()
	 * @model
	 * @generated
	 */
	int getEqualityType();

	/**
	 * Set the equality type used by this helper.
	 * @param value the equality type.
	 * @see #getEqualityType()
	 * @generated
	 */
	void setEqualityType(int value);

	/**
	 * Check whether this helper ignores node attributes.
	 * @return <code>true</code> if it ignores attributes.
	 * @see #setIgnoreAttributes(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateEqualityHelper_IgnoreAttributes()
	 * @model
	 * @generated
	 */
	boolean isIgnoreAttributes();

	/**
	 * Set the ignore-attributes flag.
	 * @param value Flag determining whether attributes should be ignored.
	 * @see #isIgnoreAttributes()
	 * @generated
	 */
	void setIgnoreAttributes(boolean value);

	/**
	 * Check whether two state models are equal.
	 * @model
	 * @generated
	 */
	boolean equals(Resource model1, Resource model2);

	/**
	 * Compute the hash code for a given state model.
	 * @model
	 * @generated
	 */
	int hashCode(Resource model);

}
