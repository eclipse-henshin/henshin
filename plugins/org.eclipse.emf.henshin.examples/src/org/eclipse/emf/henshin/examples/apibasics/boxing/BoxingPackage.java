/**
 */
package org.eclipse.emf.henshin.examples.apibasics.boxing;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.BoxingFactory
 * @model kind="package"
 * @generated
 */
public interface BoxingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "boxing";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "www.boxing.com";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "boxing";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BoxingPackage eINSTANCE = org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingImpl <em>Boxing</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingImpl
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl#getBoxing()
	 * @generated
	 */
	int BOXING = 0;

	/**
	 * The feature id for the '<em><b>Boxes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOXING__BOXES = 0;

	/**
	 * The feature id for the '<em><b>Items</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOXING__ITEMS = 1;

	/**
	 * The number of structural features of the '<em>Boxing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOXING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Boxing</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOXING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxImpl <em>Box</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxImpl
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl#getBox()
	 * @generated
	 */
	int BOX = 1;

	/**
	 * The feature id for the '<em><b>Stores</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOX__STORES = 0;

	/**
	 * The number of structural features of the '<em>Box</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOX_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Box</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOX_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.ItemImpl <em>Item</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.ItemImpl
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl#getItem()
	 * @generated
	 */
	int ITEM = 2;

	/**
	 * The feature id for the '<em><b>Is Stored By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM__IS_STORED_BY = 0;

	/**
	 * The number of structural features of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Item</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITEM_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing <em>Boxing</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boxing</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing
	 * @generated
	 */
	EClass getBoxing();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing#getBoxes <em>Boxes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Boxes</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing#getBoxes()
	 * @see #getBoxing()
	 * @generated
	 */
	EReference getBoxing_Boxes();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing#getItems <em>Items</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Items</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Boxing#getItems()
	 * @see #getBoxing()
	 * @generated
	 */
	EReference getBoxing_Items();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Box <em>Box</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Box</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Box
	 * @generated
	 */
	EClass getBox();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Box#getStores <em>Stores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Stores</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Box#getStores()
	 * @see #getBox()
	 * @generated
	 */
	EReference getBox_Stores();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item <em>Item</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Item</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Item
	 * @generated
	 */
	EClass getItem();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.Item#getIsStoredBy <em>Is Stored By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Is Stored By</em>'.
	 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.Item#getIsStoredBy()
	 * @see #getItem()
	 * @generated
	 */
	EReference getItem_IsStoredBy();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BoxingFactory getBoxingFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingImpl <em>Boxing</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingImpl
		 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl#getBoxing()
		 * @generated
		 */
		EClass BOXING = eINSTANCE.getBoxing();

		/**
		 * The meta object literal for the '<em><b>Boxes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOXING__BOXES = eINSTANCE.getBoxing_Boxes();

		/**
		 * The meta object literal for the '<em><b>Items</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOXING__ITEMS = eINSTANCE.getBoxing_Items();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxImpl <em>Box</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxImpl
		 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl#getBox()
		 * @generated
		 */
		EClass BOX = eINSTANCE.getBox();

		/**
		 * The meta object literal for the '<em><b>Stores</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BOX__STORES = eINSTANCE.getBox_Stores();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.apibasics.boxing.impl.ItemImpl <em>Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.ItemImpl
		 * @see org.eclipse.emf.henshin.examples.apibasics.boxing.impl.BoxingPackageImpl#getItem()
		 * @generated
		 */
		EClass ITEM = eINSTANCE.getItem();

		/**
		 * The meta object literal for the '<em><b>Is Stored By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITEM__IS_STORED_BY = eINSTANCE.getItem_IsStoredBy();

	}

} //BoxingPackage
