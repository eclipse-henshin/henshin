/**
 */
package org.eclipse.emf.henshin.examples.apibasics.boxing;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boxing</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing#getBoxes <em>Boxes</em>}</li>
 *   <li>{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing#getItems <em>Items</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getBoxing()
 * @model
 * @generated
 */
public interface Boxing extends EObject {
	/**
	 * Returns the value of the '<em><b>Boxes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.apibasics.boxing.Box}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boxes</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getBoxing_Boxes()
	 * @model containment="true"
	 * @generated
	 */
	EList<Box> getBoxes();

	/**
	 * Returns the value of the '<em><b>Items</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Items</em>' containment reference list.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getBoxing_Items()
	 * @model containment="true"
	 * @generated
	 */
	EList<Item> getItems();

} // Boxing
