/**
 * <copyright>
 * Copyright (c) 2010-2016 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.multicda.cpa.criticalpair;

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
 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalpairFactory
 * @model kind="package"
 * @generated
 */
public interface CriticalpairPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "criticalpair";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2016/Henshin/CriticalPair";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "criticalpair";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CriticalpairPackage eINSTANCE = org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalpairPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl <em>Critical Pair</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalpairPackageImpl#getCriticalPair()
	 * @generated
	 */
	int CRITICAL_PAIR = 0;

	/**
	 * The feature id for the '<em><b>First Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__FIRST_RULE = 0;

	/**
	 * The feature id for the '<em><b>Second Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__SECOND_RULE = 1;

	/**
	 * The feature id for the '<em><b>Minimal Model</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR__MINIMAL_MODEL = 2;

	/**
	 * The number of structural features of the '<em>Critical Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Critical Pair</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CRITICAL_PAIR_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair <em>Critical Pair</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Critical Pair</em>'.
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair
	 * @generated
	 */
	EClass getCriticalPair();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getFirstRule <em>First Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>First Rule</em>'.
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getFirstRule()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_FirstRule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getSecondRule <em>Second Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Second Rule</em>'.
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getSecondRule()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_SecondRule();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getMinimalModel <em>Minimal Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Minimal Model</em>'.
	 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.CriticalPair#getMinimalModel()
	 * @see #getCriticalPair()
	 * @generated
	 */
	EReference getCriticalPair_MinimalModel();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CriticalpairFactory getCriticalpairFactory();

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
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl <em>Critical Pair</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalPairImpl
		 * @see org.eclipse.emf.henshin.multicda.cpa.criticalpair.impl.CriticalpairPackageImpl#getCriticalPair()
		 * @generated
		 */
		EClass CRITICAL_PAIR = eINSTANCE.getCriticalPair();

		/**
		 * The meta object literal for the '<em><b>First Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__FIRST_RULE = eINSTANCE.getCriticalPair_FirstRule();

		/**
		 * The meta object literal for the '<em><b>Second Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__SECOND_RULE = eINSTANCE.getCriticalPair_SecondRule();

		/**
		 * The meta object literal for the '<em><b>Minimal Model</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CRITICAL_PAIR__MINIMAL_MODEL = eINSTANCE.getCriticalPair_MinimalModel();

	}

} //CriticalpairPackage
