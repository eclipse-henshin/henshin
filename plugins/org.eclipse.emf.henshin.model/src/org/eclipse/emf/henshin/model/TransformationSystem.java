/**
 * <copyright>
 * </copyright>
 *
 * $Id: TransformationSystem.java,v 1.1 2009/10/28 10:38:07 enrico Exp $
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Transformation System</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getRules <em>Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getTransformations <em>Transformations</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getImports <em>Imports</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.TransformationSystem#getInstances <em>Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem()
 * @model
 * @generated
 */
public interface TransformationSystem extends DescribedElement, NamedElement {
	/**
	 * Returns the value of the '<em><b>Rules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Rule#getTransformationSystem <em>Transformation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
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
	 * Returns the value of the '<em><b>Transformations</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Transformation}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.model.Transformation#getTransformationSystem <em>Transformation System</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transformations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformations</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem_Transformations()
	 * @see org.eclipse.emf.henshin.model.Transformation#getTransformationSystem
	 * @model opposite="transformationSystem" containment="true"
	 * @generated
	 */
	EList<Transformation> getTransformations();

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
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
	 * If the meaning of the '<em>Instances</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instances</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getTransformationSystem_Instances()
	 * @model containment="true"
	 * @generated
	 */
	EList<Graph> getInstances();

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * Finds and returns a rule with the given name in the rule-set of this
	 * transformation system. If no appropriate rule is found <code>null</code>
	 * is returned.
	 * </p>
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Rule findRuleByName(String ruleName);

} // TransformationSystem
