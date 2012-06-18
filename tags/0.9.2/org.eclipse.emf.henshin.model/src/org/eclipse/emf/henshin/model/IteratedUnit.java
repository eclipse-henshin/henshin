/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Iterated Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.IteratedUnit#getSubUnit <em>Sub Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.IteratedUnit#getIterations <em>Iterations</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit()
 * @model
 * @generated
 */
public interface IteratedUnit extends TransformationUnit {
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
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit_SubUnit()
	 * @model required="true"
	 * @generated
	 */
	TransformationUnit getSubUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.IteratedUnit#getSubUnit <em>Sub Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sub Unit</em>' reference.
	 * @see #getSubUnit()
	 * @generated
	 */
	void setSubUnit(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Iterations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iterations</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Iterations</em>' attribute.
	 * @see #setIterations(String)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getIteratedUnit_Iterations()
	 * @model
	 * @generated
	 */
	String getIterations();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.IteratedUnit#getIterations <em>Iterations</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Iterations</em>' attribute.
	 * @see #getIterations()
	 * @generated
	 */
	void setIterations(String value);

} // IteratedUnit
