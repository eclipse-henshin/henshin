/**
 * <copyright>
 * </copyright>
 *
 * $Id: AmalgamatedUnit.java,v 1.1 2009/10/28 10:38:04 enrico Exp $
 */
package org.eclipse.emf.henshin.model;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Amalgamated Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getKernelRule <em>Kernel Rule</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getMultiRules <em>Multi Rules</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getLhsMappings <em>Lhs Mappings</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getRhsMappings <em>Rhs Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamatedUnit()
 * @model
 * @generated
 */
public interface AmalgamatedUnit extends TransformationUnit {
	
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
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamatedUnit_KernelRule()
	 * @model
	 * @generated
	 */
	Rule getKernelRule();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.model.AmalgamatedUnit#getKernelRule <em>Kernel Rule</em>}' reference.
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
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamatedUnit_MultiRules()
	 * @model
	 * @generated
	 */
	EList<Rule> getMultiRules();

	/**
	 * Returns the value of the '<em><b>Lhs Mappings</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs Mappings</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamatedUnit_LhsMappings()
	 * @model
	 * @generated
	 */
	EList<Mapping> getLhsMappings();

	/**
	 * Returns the value of the '<em><b>Rhs Mappings</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.model.Mapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs Mappings</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs Mappings</em>' reference list.
	 * @see org.eclipse.emf.henshin.model.HenshinPackage#getAmalgamatedUnit_RhsMappings()
	 * @model
	 * @generated
	 */
	EList<Mapping> getRhsMappings();

} // AmalgamatedUnit
