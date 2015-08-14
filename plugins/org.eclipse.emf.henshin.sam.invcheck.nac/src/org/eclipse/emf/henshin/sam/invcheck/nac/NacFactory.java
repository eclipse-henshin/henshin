/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage
 * @generated
 */
public interface NacFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	NacFactory eINSTANCE = org.eclipse.emf.henshin.sam.invcheck.nac.impl.NacFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Negative Application Condition</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Negative Application Condition</em>'.
	 * @generated
	 */
	NegativeApplicationCondition createNegativeApplicationCondition();

	/**
	 * Returns a new object of class '<em>Match With Nacs</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Match With Nacs</em>'.
	 * @generated
	 */
	MatchWithNacs createMatchWithNacs();

	/**
	 * Returns a new object of class '<em>Pattern Node</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pattern Node</em>'.
	 * @generated
	 */
	PatternNode createPatternNode();

	/**
	 * Returns a new object of class '<em>Pattern Edge</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Pattern Edge</em>'.
	 * @generated
	 */
	PatternEdge createPatternEdge();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	NacPackage getNacPackage();

} //NacFactory
