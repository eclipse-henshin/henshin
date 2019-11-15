/**
 */
package org.eclipse.emf.henshin.examples.apibasics.boxing;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Item</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item#getIsStoredBy <em>Is Stored By</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getItem()
 * @model
 * @generated
 */
public interface Item extends EObject {
	/**
	 * Returns the value of the '<em><b>Is Stored By</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Box#getStores <em>Stores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Stored By</em>' reference.
	 * @see #setIsStoredBy(Box)
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getItem_IsStoredBy()
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Box#getStores
	 * @model opposite="stores"
	 * @generated
	 */
	Box getIsStoredBy();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item#getIsStoredBy <em>Is Stored By</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Stored By</em>' reference.
	 * @see #getIsStoredBy()
	 * @generated
	 */
	void setIsStoredBy(Box value);

} // Item
