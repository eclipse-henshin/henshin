/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.henshin.model.Rule;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenRule#isCheckDangling <em>Check Dangling</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenRule#isCheckInverseDangling <em>Check Inverse Dangling</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenRule()
 * @model
 * @generated
 */
public interface GenRule extends GenUnit {
	/**
	 * Returns the value of the '<em><b>Check Dangling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check Dangling</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check Dangling</em>' attribute.
	 * @see #setCheckDangling(boolean)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenRule_CheckDangling()
	 * @model
	 * @generated
	 */
	boolean isCheckDangling();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenRule#isCheckDangling <em>Check Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check Dangling</em>' attribute.
	 * @see #isCheckDangling()
	 * @generated
	 */
	void setCheckDangling(boolean value);

	/**
	 * Returns the value of the '<em><b>Check Inverse Dangling</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Check Inverse Dangling</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Check Inverse Dangling</em>' attribute.
	 * @see #setCheckInverseDangling(boolean)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenRule_CheckInverseDangling()
	 * @model
	 * @generated
	 */
	boolean isCheckInverseDangling();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenRule#isCheckInverseDangling <em>Check Inverse Dangling</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Check Inverse Dangling</em>' attribute.
	 * @see #isCheckInverseDangling()
	 * @generated
	 */
	void setCheckInverseDangling(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	Rule getRule();

} // GenRule
