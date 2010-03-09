/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University of Berlin, 
 * University of Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University of Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Transformation#getMainUnit <em>Main Unit</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Transformation#getTransformationSystem <em>Transformation System</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformation()
 * @model
 * @generated
 */
public interface Transformation extends DescribedElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Main Unit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Main Unit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Main Unit</em>' containment reference.
	 * @see #setMainUnit(TransformationUnit)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformation_MainUnit()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TransformationUnit getMainUnit();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Transformation#getMainUnit <em>Main Unit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Main Unit</em>' containment reference.
	 * @see #getMainUnit()
	 * @generated
	 */
	void setMainUnit(TransformationUnit value);

	/**
	 * Returns the value of the '<em><b>Transformation System</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.TransformationSystem#getTransformations <em>Transformations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformation System</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation System</em>' container reference.
	 * @see #setTransformationSystem(TransformationSystem)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformation_TransformationSystem()
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getTransformations
	 * @model opposite="transformations" required="true" transient="false"
	 * @generated
	 */
	TransformationSystem getTransformationSystem();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Transformation#getTransformationSystem <em>Transformation System</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformation System</em>' container reference.
	 * @see #getTransformationSystem()
	 * @generated
	 */
	void setTransformationSystem(TransformationSystem value);

} // Transformation
