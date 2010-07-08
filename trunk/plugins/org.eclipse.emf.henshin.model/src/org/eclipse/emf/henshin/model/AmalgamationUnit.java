/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Amalgamation Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getKernelRule <em>Kernel Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getMultiRules <em>Multi Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getLhsMappings <em>Lhs Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getRhsMappings <em>Rhs Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit()
 * @model
 * @generated
 */
public interface AmalgamationUnit extends TransformationUnit {
	/**
	 * Returns the value of the '<em><b>Kernel Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kernel Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kernel Rule</em>' reference.
	 * @see #setKernelRule(Rule)
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_KernelRule()
	 * @model required="true"
	 * @generated
	 */
	Rule getKernelRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.AmalgamationUnit#getKernelRule <em>Kernel Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kernel Rule</em>' reference.
	 * @see #getKernelRule()
	 * @generated
	 */
	void setKernelRule(Rule value);

	/**
	 * Returns the value of the '<em><b>Multi Rules</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Rule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multi Rules</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multi Rules</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_MultiRules()
	 * @model
	 * @generated
	 */
	EList<Rule> getMultiRules();

	/**
	 * Returns the value of the '<em><b>Lhs Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_LhsMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getLhsMappings();

	/**
	 * Returns the value of the '<em><b>Rhs Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs Mappings</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamationUnit_RhsMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Mapping> getRhsMappings();

} // AmalgamationUnit
