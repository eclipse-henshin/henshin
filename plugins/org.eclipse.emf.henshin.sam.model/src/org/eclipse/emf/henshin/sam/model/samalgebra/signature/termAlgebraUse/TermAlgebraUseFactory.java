/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage
 * @generated
 */
public interface TermAlgebraUseFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TermAlgebraUseFactory eINSTANCE = org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUseFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Attribute Type Use</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Attribute Type Use</em>'.
	 * @generated
	 */
	AttributeTypeUse createAttributeTypeUse();

	/**
	 * Returns a new object of class '<em>Operation Term Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Term Parameter</em>'.
	 * @generated
	 */
	OperationTermParameter createOperationTermParameter();

	/**
	 * Returns a new object of class '<em>Operation Symbol Use</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Operation Symbol Use</em>'.
	 * @generated
	 */
	OperationSymbolUse createOperationSymbolUse();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TermAlgebraUsePackage getTermAlgebraUsePackage();

} //TermAlgebraUseFactory
