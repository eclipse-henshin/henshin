/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Loop Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.LoopUnit#getSubUnit <em>Sub Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getLoopUnit()
 * @model
 * @generated
 */
public interface LoopUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>Sub Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Unit</em>' reference.
	 * @see #setSubUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getLoopUnit_SubUnit()
	 * @model required="true"
	 * @generated
	 */
	TransformationUnit getSubUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.LoopUnit#getSubUnit <em>Sub Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Unit</em>' reference.
	 * @see #getSubUnit()
	 * @generated
	 */
	void setSubUnit(TransformationUnit value);

} // LoopUnit
