/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConditionalUnit.java,v 1.1 2009/10/28 10:38:09 enrico Exp $
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Conditional Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.ConditionalUnit#getIf <em>If</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.ConditionalUnit#getThen <em>Then</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.ConditionalUnit#getElse <em>Else</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit()
 * @model
 * @generated
 */
public interface ConditionalUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>If</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>If</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>If</em>' containment reference.
	 * @see #setIf(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit_If()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TransformationUnit getIf();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getIf <em>If</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>If</em>' containment reference.
	 * @see #getIf()
	 * @generated
	 */
	void setIf(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Then</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Then</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Then</em>' containment reference.
	 * @see #setThen(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit_Then()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TransformationUnit getThen();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getThen <em>Then</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Then</em>' containment reference.
	 * @see #getThen()
	 * @generated
	 */
	void setThen(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Else</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Else</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Else</em>' containment reference.
	 * @see #setElse(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getConditionalUnit_Else()
	 * @model containment="true"
	 * @generated
	 */
	TransformationUnit getElse();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.ConditionalUnit#getElse <em>Else</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Else</em>' containment reference.
	 * @see #getElse()
	 * @generated
	 */
	void setElse(TransformationUnit value);

} // ConditionalUnit
