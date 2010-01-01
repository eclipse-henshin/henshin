/**
 * <copyright>
 * </copyright>
 *
 * $Id: TransformationUnit.java,v 1.1 2009/10/28 10:38:04 enrico Exp $
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformation Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationUnit#isActivated <em>Activated</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationUnit#getPorts <em>Ports</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit()
 * @model abstract="true"
 * @generated
 */
public interface TransformationUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Activated</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Activated</em>' attribute.
	 * @see #setActivated(boolean)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_Activated()
	 * @model
	 * @generated
	 */
	boolean isActivated();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.TransformationUnit#isActivated <em>Activated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Activated</em>' attribute.
	 * @see #isActivated()
	 * @generated
	 */
	void setActivated(boolean value);

	/**
	 * Returns the value of the '<em><b>Ports</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Port}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Port#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ports</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ports</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationUnit_Ports()
	 * @see org.eclipse.emf.henshin.model.Port#getUnit
	 * @model opposite="unit" containment="true"
	 * @generated
	 */
	EList<Port> getPorts();

} // TransformationUnit
