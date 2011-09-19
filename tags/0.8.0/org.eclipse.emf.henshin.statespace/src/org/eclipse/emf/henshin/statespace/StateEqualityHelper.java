/**
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
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
import org.eclipse.emf.henshin.statespace.hashcodes.HashCodeMap;

/**
 * Helper class for deciding whether two state models are
 * equal and for calculating their hash codes.
 * @model
 * @generated
 */
public interface StateEqualityHelper extends EObject {
	
	/**
	 * Whether to use graph equality instead of Ecore equality.
	 * @see #setGraphEquality(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateEqualityHelper_GraphEquality()
	 * @model
	 * @generated
	 */
	boolean isGraphEquality();

	/**
	 * Set whether to use graph equality.
	 * @see #isGraphEquality()
	 * @generated
	 */
	void setGraphEquality(boolean value);

	/**
	 * Check whether node IDs should be ignored.
	 * @see #setIgnoreNodeIDs(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateEqualityHelper_IgnoreNodeIDs()
	 * @model default="false"
	 * @generated
	 */
	boolean isIgnoreNodeIDs();

	/**
	 * Set whether node IDs should be ignored.
	 * @param value the new value of the '<em>Ignore Node IDs</em>' attribute.
	 * @see #isIgnoreNodeIDs()
	 * @generated
	 */
	void setIgnoreNodeIDs(boolean value);

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
	 * Generate a hash code of a state model.
	 * @model
	 * @generated
	 */
	int hashCode(Model model, HashCodeMap map);

	/**
	 * Check whether two state models are equal.
	 * @model
	 * @generated
	 */
	boolean equals(Model model1, HashCodeMap map1, Model model2, HashCodeMap map2);
	
}
