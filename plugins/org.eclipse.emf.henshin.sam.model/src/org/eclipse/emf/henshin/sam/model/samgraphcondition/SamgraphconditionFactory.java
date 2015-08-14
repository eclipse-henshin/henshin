/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage
 * @generated
 */
public interface SamgraphconditionFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamgraphconditionFactory eINSTANCE = org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Proxy Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Proxy Node</em>'.
	 * @generated
	 */
	ProxyNode createProxyNode();

	/**
	 * Returns a new object of class '<em>Logical GC Coupling</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Logical GC Coupling</em>'.
	 * @generated
	 */
	LogicalGCCoupling createLogicalGCCoupling();

	/**
	 * Returns a new object of class '<em>Quantification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Quantification</em>'.
	 * @generated
	 */
	Quantification createQuantification();

	/**
	 * Returns a new object of class '<em>Termination Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Termination Condition</em>'.
	 * @generated
	 */
	TerminationCondition createTerminationCondition();

	/**
	 * Returns a new object of class '<em>Negated Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Negated Condition</em>'.
	 * @generated
	 */
	NegatedCondition createNegatedCondition();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SamgraphconditionPackage getSamgraphconditionPackage();

} //SamgraphconditionFactory
