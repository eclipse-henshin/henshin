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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * Helper class for deciding whether two state models are
 * equal and for calculating their hash codes.
 * @model
 * @generated
 */
public interface EqualityHelper extends EObject {
	
	/**
	 * Returns the value of the '<em><b>Check Link Order</b></em>' attribute.
	 * @return the value of the '<em>Check Link Order</em>' attribute.
	 * @see #setCheckLinkOrder(boolean)
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getEqualityHelper_CheckLinkOrder()
	 * @model
	 * @generated
	 */
	boolean isCheckLinkOrder();

	/**
	 * Returns the value of the '<em><b>Ignored Attributes</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EAttribute}.
	 * @return the value of the '<em>Ignored Attributes</em>' reference list.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getEqualityHelper_IgnoredAttributes()
	 * @model required="true" transient="true" changeable="false"
	 * @generated
	 */
	EList<EAttribute> getIgnoredAttributes();

	/**
	 * Returns the value of the '<em><b>Identity Types</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EClass}.
	 * @return the value of the '<em>Identity Types</em>' reference list.
	 * @see org.eclipse.emf.henshin.statespace.StateSpacePackage#getEqualityHelper_IdentityTypes()
	 * @model required="true" transient="true" changeable="false"
	 * @generated
	 */
	EList<EClass> getIdentityTypes();

	/**
	 * @model
	 * @generated
	 */
	void setStateSpace(StateSpace stateSpace);

	/**
	 * Clear all caches.
	 * @model
	 * @generated
	 */
	void clearCache();

	/**
	 * Generate a hash code of a state model.
	 * @model
	 * @generated
	 */
	int hashCode(Model model);

	/**
	 * Check whether two state models are equal.
	 * @model
	 * @generated
	 */
	boolean equals(Model model1, Model model2);
	
}
