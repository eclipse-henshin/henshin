/**
 */
package org.eclipse.emf.henshin.examples.apibasics.boxing;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage
 * @generated
 */
public interface BoxingFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BoxingFactory eINSTANCE = org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Boxing</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boxing</em>'.
	 * @generated
	 */
	Boxing createBoxing();

	/**
	 * Returns a new object of class '<em>Box</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Box</em>'.
	 * @generated
	 */
	Box createBox();

	/**
	 * Returns a new object of class '<em>Item</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Item</em>'.
	 * @generated
	 */
	Item createItem();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	BoxingPackage getBoxingPackage();

} //BoxingFactory
