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

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Rule#getLhs <em>Lhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Rule#getRhs <em>Rhs</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Rule#getAttributeConditions <em>Attribute Conditions</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Rule#getMappings <em>Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Rule#getTransformationSystem <em>Transformation System</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Rule#getVariables <em>Variables</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule()
 * @model
 * @generated
 */
public interface Rule extends DescribedElement, NamedElement, TransformationUnit {
	/**
	 * Returns the value of the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs</em>' containment reference.
	 * @see #setLhs(Graph)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule_Lhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getLhs();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Rule#getLhs <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhs</em>' containment reference.
	 * @see #getLhs()
	 * @generated
	 */
	void setLhs(Graph value);

	/**
	 * Returns the value of the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs</em>' containment reference.
	 * @see #setRhs(Graph)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule_Rhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Graph getRhs();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Rule#getRhs <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhs</em>' containment reference.
	 * @see #getRhs()
	 * @generated
	 */
	void setRhs(Graph value);

	/**
	 * Returns the value of the '<em><b>Attribute Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.AttributeCondition}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.AttributeCondition#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Conditions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Conditions</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule_AttributeConditions()
	 * @see org.eclipse.emf.henshin.model.AttributeCondition#getRule
	 * @model opposite="rule" containment="true"
	 * @generated
	 */
	EList<AttributeCondition> getAttributeConditions();

	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule_Mappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getMappings();

	/**
	 * Returns the value of the '<em><b>Transformation System</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.TransformationSystem#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformation System</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation System</em>' container reference.
	 * @see #setTransformationSystem(TransformationSystem)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule_TransformationSystem()
	 * @see org.eclipse.emf.henshin.model.TransformationSystem#getRules
	 * @model opposite="rules" required="true" transient="false"
	 * @generated
	 */
	TransformationSystem getTransformationSystem();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.Rule#getTransformationSystem <em>Transformation System</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformation System</em>' container reference.
	 * @see #getTransformationSystem()
	 * @generated
	 */
	void setTransformationSystem(TransformationSystem value);

	/**
	 * Returns the value of the '<em><b>Variables</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Variable}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Variable#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variables</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variables</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getRule_Variables()
	 * @see org.eclipse.emf.henshin.model.Variable#getRule
	 * @model opposite="rule" containment="true"
	 * @generated
	 */
	EList<Variable> getVariables();

	
} // Rule
