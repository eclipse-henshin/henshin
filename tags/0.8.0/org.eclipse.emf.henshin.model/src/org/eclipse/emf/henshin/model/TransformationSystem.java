/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Technical University Berlin - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> A representation of the model object '
 * <em><b>Transformation System</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getInstances <em>Instances</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getTransformationUnits <em>Transformation Units</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='uniqueUnitNames noCyclicUnits parameterNamesNotTypeName'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL uniqueUnitNames='transformationUnits->forAll(unit1,unit2:TransformationUnit | unit1 <> unit2 implies unit1.name <> unit2.name)' uniqueUnitNames.Msg='_Ocl_Msg_TransformationSystem_uniqueUnitNames'"
 * @generated
 */
public interface TransformationSystem extends DescribedElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Rule#getTransformationSystem <em>Transformation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem_Rules()
	 * @see org.eclipse.emf.henshin.model.Rule#getTransformationSystem
	 * @model opposite="transformationSystem" containment="true"
	 * @generated
	 */
	EList<Rule> getRules();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list. The
	 * list contents are of type {@link org.eclipse.emf.ecore.EPackage}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Imports</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem_Imports()
	 * @model
	 * @generated
	 */
	EList<EPackage> getImports();

	/**
	 * Returns the value of the '<em><b>Instances</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Graph}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' containment reference list
	 * isn't clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem_Instances()
	 * @model containment="true"
	 * @generated
	 */
	EList<Graph> getInstances();

	/**
	 * Returns the value of the '<em><b>Transformation Units</b></em>'
	 * containment reference list. The list contents are of type
	 * {@link org.eclipse.emf.henshin.model.TransformationUnit}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformation Units</em>' containment
	 * reference list isn't clear, there really should be more of a description
	 * here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Transformation Units</em>' containment
	 *         reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem_TransformationUnits()
	 * @model containment="true"
	 * @generated
	 */
	EList<TransformationUnit> getTransformationUnits();
	
	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Finds and returns the first occurrence of a transformation unit with the
	 * given name. If <code>deep</code> is <code>false</code>, the name is
	 * searched within all directly contained units of the transformation system
	 * only. Otherwise, all subunits are also considered. If no appropriate unit
	 * is found <code>null</code> is returned.<br>
	 * Please note, while rules are transformations units as well, please use
	 * {@link #findRuleByName(String)} for such cases.
	 * </p>
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	TransformationUnit findUnitByName(String unitName);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Finds and returns the first occurrence of a rule with the given name in the rule-set of this
	 * transformation system. If no appropriate rule is found <code>null</code>
	 * is returned.
	 * </p>
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Rule findRuleByName(String ruleName);

} // TransformationSystem
