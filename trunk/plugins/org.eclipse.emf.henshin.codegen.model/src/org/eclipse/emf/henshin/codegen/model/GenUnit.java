/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.henshin.model.TransformationUnit;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Gen Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getUnit <em>Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getMethod <em>Method</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.codegen.model.GenUnit#isPublic <em>Public</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit()
 * @model
 * @generated
 */
public interface GenUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' reference.
	 * @see #setUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_Unit()
	 * @model
	 * @generated
	 */
	TransformationUnit getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getUnit <em>Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Method</em>' attribute.
	 * @see #setMethod(String)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_Method()
	 * @model
	 * @generated
	 */
	String getMethod();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#getMethod <em>Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Method</em>' attribute.
	 * @see #getMethod()
	 * @generated
	 */
	void setMethod(String value);

	/**
	 * Returns the value of the '<em><b>Public</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public</em>' attribute.
	 * @see #setPublic(boolean)
	 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage#getGenUnit_Public()
	 * @model
	 * @generated
	 */
	boolean isPublic();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.codegen.model.GenUnit#isPublic <em>Public</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public</em>' attribute.
	 * @see #isPublic()
	 * @generated
	 */
	void setPublic(boolean value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getMethodFormatted();

} // GenUnit
