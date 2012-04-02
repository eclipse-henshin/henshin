/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Parameter#getUnit <em>Unit</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getParameter()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='nameRequired'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL nameRequired='not name.oclIsUndefined() and name.size() > 0' nameRequired.Msg='_Ocl_Msg_Parameter_nameRequired'"
 * @generated
 */
public interface Parameter extends DescribedElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Unit</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.TransformationUnit#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Unit</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Unit</em>' container reference.
	 * @see #setUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getParameter_Unit()
	 * @see org.eclipse.emf.henshin.model.TransformationUnit#getParameters
	 * @model opposite="parameters" required="true" transient="false"
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

} // Parameter
