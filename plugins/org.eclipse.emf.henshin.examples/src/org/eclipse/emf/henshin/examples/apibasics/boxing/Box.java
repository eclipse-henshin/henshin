/**
 */
package org.eclipse.emf.henshin.examples.apibasics.boxing;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Box</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Box#getStores <em>Stores</em>}</li>
 * </ul>
 *
 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getBox()
 * @model
 * @generated
 */
public interface Box extends EObject {
	/**
	 * Returns the value of the '<em><b>Stores</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item}.
	 * It is bidirectional and its opposite is '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item#getIsStoredBy <em>Is Stored By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stores</em>' reference list.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingPackage#getBox_Stores()
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Item#getIsStoredBy
	 * @model opposite="isStoredBy"
	 * @generated
	 */
	EList<Item> getStores();

} // Box
