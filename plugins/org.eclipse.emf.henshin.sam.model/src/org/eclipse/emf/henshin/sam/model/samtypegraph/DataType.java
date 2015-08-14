/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtypegraph;

import org.eclipse.emf.henshin.sam.model.samalgebra.signature.Sort;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.sam.model.samtypegraph.DataType#getSort <em>Sort</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getDataType()
 * @model
 * @generated
 */
public interface DataType extends NodeType {
	/**
	 * Returns the value of the '<em><b>Sort</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sort</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sort</em>' reference.
	 * @see #setSort(Sort)
	 * @see org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage#getDataType_Sort()
	 * @model
	 * @generated
	 */
	Sort getSort();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.sam.model.samtypegraph.DataType#getSort <em>Sort</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sort</em>' reference.
	 * @see #getSort()
	 * @generated
	 */
	void setSort(Sort value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Sort lookUpSort(String dataType);

} // DataType
