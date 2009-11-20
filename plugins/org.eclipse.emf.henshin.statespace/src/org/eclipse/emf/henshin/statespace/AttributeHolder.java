/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.statespace;

import org.eclipse.emf.ecore.EObject;

/**
 * @model
 * @generated
 */
public interface AttributeHolder extends EObject {
	
	/**
	 * Returns the value of the '<em><b>Attributes</b></em>' attribute.
	 * @return the value of the '<em>Attributes</em>' attribute.
	 * @see #setAttributes(int[])
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray"
	 * @generated
	 */
	int[] getAttributes();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.AttributeHolder#getAttributes <em>Attributes</em>}' attribute.
	 * @param value the new value of the '<em>Attributes</em>' attribute.
	 * @see #getAttributes()
	 * @generated
	 */
	void setAttributes(int[] value);

}
