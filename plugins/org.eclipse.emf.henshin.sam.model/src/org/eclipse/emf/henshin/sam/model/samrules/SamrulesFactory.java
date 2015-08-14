/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage
 * @generated
 */
public interface SamrulesFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SamrulesFactory eINSTANCE = org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Graph Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Graph Rule</em>'.
	 * @generated
	 */
	GraphRule createGraphRule();

	/**
	 * Returns a new object of class '<em>Preserved Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Preserved Node</em>'.
	 * @generated
	 */
	PreservedNode createPreservedNode();

	/**
	 * Returns a new object of class '<em>Preserved Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Preserved Edge</em>'.
	 * @generated
	 */
	PreservedEdge createPreservedEdge();

	/**
	 * Returns a new object of class '<em>Rule Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Rule Graph</em>'.
	 * @generated
	 */
	RuleGraph createRuleGraph();

	/**
	 * Returns a new object of class '<em>GTS</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>GTS</em>'.
	 * @generated
	 */
	GTS createGTS();

	/**
	 * Returns a new object of class '<em>Created Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Created Node</em>'.
	 * @generated
	 */
	CreatedNode createCreatedNode();

	/**
	 * Returns a new object of class '<em>Deleted Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deleted Node</em>'.
	 * @generated
	 */
	DeletedNode createDeletedNode();

	/**
	 * Returns a new object of class '<em>Created Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Created Edge</em>'.
	 * @generated
	 */
	CreatedEdge createCreatedEdge();

	/**
	 * Returns a new object of class '<em>Deleted Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Deleted Edge</em>'.
	 * @generated
	 */
	DeletedEdge createDeletedEdge();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SamrulesPackage getSamrulesPackage();

} //SamrulesFactory
