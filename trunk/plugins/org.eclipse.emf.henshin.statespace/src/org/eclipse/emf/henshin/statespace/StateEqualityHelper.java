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

/**
 * Helper class for deciding whether two state models are
 * equal and for calculating their hash codes.
 * @model
 * @generated
 */
public interface StateEqualityHelper extends EObject {
	
	/**
	 * Check whether graph equality should be used.
	 * @return the value of the '<em>Use Graph Equality</em>' attribute.
	 * @see #setUseGraphEquality(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getStateEqualityHelper_UseGraphEquality()
	 * @model default="true"
	 * @generated
	 */
	boolean isUseGraphEquality();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.StateEqualityHelper#isUseGraphEquality <em>Use Graph Equality</em>}' attribute.
	 * @param value the new value of the '<em>Use Graph Equality</em>' attribute.
	 * @see #isUseGraphEquality()
	 * @generated
	 */
	void setUseGraphEquality(boolean value);

	/**
	 * Check whether object keys should be used.
	 * @model default="false"
	 * @generated
	 */
	boolean isUseObjectKeys();

	/**
	 * Set whether object keys should be used.
	 * @param useObjectKeys the new value of the '<em>Use Object Keys</em>' attribute.
	 * @see #isUseObjectKeys()
	 * @generated
	 */
	void setUseObjectKeys(boolean value);

	/**
	 * Check whether this helper uses object attributes.
	 * @return <code>true</code> if it uses attributes.
	 * @see #setUseObjectAttributes(boolean)
	 * @model
	 * @generated
	 */
	boolean isUseObjectAttributes();

	/**
	 * Set the ignore-attributes flag.
	 * @param useObjectAttributes the new value of the '<em>Use Object Attributes</em>' attribute.
	 * @see #isUseObjectAttributes()
	 * @generated
	 */
	void setUseObjectAttributes(boolean value);

	/**
	 * Generate a hash code of a state model.
	 * @model
	 * @generated
	 */
	int hashCode(Model model);

	/**
	 * Clear all caches.
	 * @model
	 * @generated
	 */
	void clearCache();

	/**
	 * Check whether two state models are equal.
	 * @model
	 * @generated
	 */
	boolean equals(Model model1, Model model2);
	
}
