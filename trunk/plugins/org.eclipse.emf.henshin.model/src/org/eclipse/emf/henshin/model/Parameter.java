/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.ecore.EClassifier;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Parameter#getUnit <em>Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Parameter#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getParameter()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='nameRequired'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL nameRequired='not name.oclIsUndefined() and name.size() > 0' nameRequired.Msg='_Ocl_Msg_Parameter_nameRequired'"
 * @generated
 */
public interface Parameter extends NamedElement {
	
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.TransformationUnit#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' container reference.
	 * @see #setUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getParameter_Unit()
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#getParameters
	 * @model opposite="parameters" transient="false"
	 * @generated
	 */
	TransformationUnit getUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Parameter#getUnit <em>Unit</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Unit</em>' container reference.
	 * @see #getUnit()
	 * @generated
	 */
	void setUnit(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(EClassifier)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getParameter_Type()
	 * @model
	 * @generated
	 */
	EClassifier getType();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Parameter#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(EClassifier value);

} // Parameter
