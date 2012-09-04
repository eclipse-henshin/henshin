/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Module</b></em>'.
 * @extends TransformationSystem
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.Module#getSubModules <em>Sub Modules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Module#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Module#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Module#getTransformationUnits <em>Transformation Units</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.Module#getInstances <em>Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getModule()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='uniqueUnitNames noCyclicUnits parameterNamesNotTypeName'"
 *        annotation="http://www.eclipse.org/emf/2010/Henshin/OCL uniqueUnitNames='transformationUnits->forAll(unit1,unit2:TransformationUnit | unit1 <> unit2 implies unit1.name <> unit2.name)' uniqueUnitNames.Msg='_Ocl_Msg_TransformationSystem_uniqueUnitNames'"
 * @generated
 */
public interface Module extends NamedElement, TransformationSystem {
	
	/**
	 * Returns the value of the '<em><b>Sub Modules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Module}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sub Modules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sub Modules</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getModule_SubModules()
	 * @model containment="true"
	 * @generated
	 */
	EList<Module> getSubModules();

	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rules</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getModule_Rules()
	 * @model containment="true"
	 * @generated
	 */
	EList<Rule> getRules();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Imports</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getModule_Imports()
	 * @model
	 * @generated
	 */
	EList<EPackage> getImports();

	/**
	 * Returns the value of the '<em><b>Transformation Units</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.TransformationUnit}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation Units</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getModule_TransformationUnits()
	 * @model containment="true"
	 * @generated
	 */
	EList<TransformationUnit> getTransformationUnits();

	/**
	 * Returns the value of the '<em><b>Instances</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Graph}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getModule_Instances()
	 * @model containment="true"
	 * @generated
	 */
	EList<Graph> getInstances();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	TransformationUnit getTransformationUnit(String unitName);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Rule getRule(String ruleName);

} // Module
