/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.codegen.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.codegen.model.GenHenshinPackage
 * @generated
 */
public interface GenHenshinFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	GenHenshinFactory eINSTANCE = org.eclipse.emf.henshin.codegen.model.impl.GenHenshinFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Gen Henshin</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Gen Henshin</em>'.
	 * @generated
	 */
	GenHenshin createGenHenshin();

	/**
	 * Returns a new object of class '<em>Gen Transformation</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Gen Transformation</em>'.
	 * @generated
	 */
	GenTransformation createGenTransformation();

	/**
	 * Returns a new object of class '<em>Gen Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Gen Unit</em>'.
	 * @generated
	 */
	GenUnit createGenUnit();

	/**
	 * Returns a new object of class '<em>Gen Rule</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Gen Rule</em>'.
	 * @generated
	 */
	GenRule createGenRule();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	GenHenshinPackage getGenHenshinPackage();

} //GenHenshinFactory
