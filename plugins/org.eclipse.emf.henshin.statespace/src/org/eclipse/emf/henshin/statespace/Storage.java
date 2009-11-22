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
public interface Storage extends EObject {
	
	/**
	 * Returns the value of the '<em><b>Data</b></em>' attribute.
	 * @return the value of the '<em>Data</em>' attribute.
	 * @see #setData(int[])
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray"
	 * @generated
	 */
	int[] getData();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.statespace.Storage#getData <em>Data</em>}' attribute.
	 * @param value the new value of the '<em>Data</em>' attribute.
	 * @see #getData()
	 * @generated
	 */
	void setData(int[] value);

	/**
	 * @model
	 * @generated
	 */
	int getData(int index);

	/**
	 * @model
	 * @generated
	 */
	void setData(int index, int value);

	/**
	 * @model dataType="org.eclipse.emf.henshin.statespace.IntegerArray"
	 * @generated
	 */
	int[] getData(int beginIndex, int endIndex);

	/**
	 * @model valueDataType="org.eclipse.emf.henshin.statespace.IntegerArray"
	 * @generated
	 */
	void setData(int beginIndex, int[] value);

}
