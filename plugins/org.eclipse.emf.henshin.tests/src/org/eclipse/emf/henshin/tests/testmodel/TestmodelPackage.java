/*******************************************************************************
 * Copyright (c) 2010 CWI Amsterdam, Technical University Berlin, 
 * Philipps-University Marburg and others. All rights reserved. 
 * This program and the accompanying materials are made 
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Philipps-University Marburg - initial API and implementation
 *******************************************************************************/
package org.eclipse.emf.henshin.tests.testmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelFactory
 * @model kind="package"
 * @generated
 */
public interface TestmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "testmodel";
	
	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2010/Henshin/Tests";
	
	/**
	 * The package namespace name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "testmodel";
	
	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	TestmodelPackage eINSTANCE = org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl.init();
	
	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.tests.testmodel.impl.contImpl <em>cont</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.tests.testmodel.impl.contImpl
	 * @see org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl#getcont()
	 * @generated
	 */
	int CONT = 0;
	
	/**
	 * The feature id for the '<em><b>Contains Node</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONT__CONTAINS_NODE = 0;
	
	/**
	 * The feature id for the '<em><b>Contains Val</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONT__CONTAINS_VAL = 1;
	
	/**
	 * The number of structural features of the '<em>cont</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int CONT_FEATURE_COUNT = 2;
	
	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.tests.testmodel.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.tests.testmodel.impl.NodeImpl
	 * @see org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 1;
	
	/**
	 * The feature id for the '<em><b>Has Vals</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__HAS_VALS = 0;
	
	/**
	 * The feature id for the '<em><b>Parent Node</b></em>' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__PARENT_NODE = 1;
	
	/**
	 * The feature id for the '<em><b>Child Nodes</b></em>' reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__CHILD_NODES = 2;
	
	/**
	 * The feature id for the '<em><b>Nodename</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE__NODENAME = 3;
	
	/**
	 * The number of structural features of the '<em>Node</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 4;
	
	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.tests.testmodel.impl.ValImpl <em>Val</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.tests.testmodel.impl.ValImpl
	 * @see org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl#getVal()
	 * @generated
	 */
	int VAL = 2;
	
	/**
	 * The feature id for the '<em><b>Intvl</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAL__INTVL = 0;
	
	/**
	 * The feature id for the '<em><b>Valname</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAL__VALNAME = 1;
	
	/**
	 * The feature id for the '<em><b>Intlist</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VAL__INTLIST = 2;

	/**
	 * The number of structural features of the '<em>Val</em>' class. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int VAL_FEATURE_COUNT = 3;
	
	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.henshin.tests.testmodel.cont <em>cont</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>cont</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.cont
	 * @generated
	 */
	EClass getcont();
	
	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.tests.testmodel.cont#getContainsNode <em>Contains Node</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contains Node</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.cont#getContainsNode()
	 * @see #getcont()
	 * @generated
	 */
	EReference getcont_ContainsNode();
	
	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.tests.testmodel.cont#getContainsVal <em>Contains Val</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contains Val</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.cont#getContainsVal()
	 * @see #getcont()
	 * @generated
	 */
	EReference getcont_ContainsVal();
	
	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.henshin.tests.testmodel.Node <em>Node</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Node</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node
	 * @generated
	 */
	EClass getNode();
	
	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getHasVals <em>Has Vals</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Has Vals</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node#getHasVals()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_HasVals();
	
	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getParentNode <em>Parent Node</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent Node</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node#getParentNode()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_ParentNode();
	
	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getChildNodes <em>Child Nodes</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Child Nodes</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node#getChildNodes()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_ChildNodes();
	
	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.tests.testmodel.Node#getNodename <em>Nodename</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Nodename</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Node#getNodename()
	 * @see #getNode()
	 * @generated
	 */
	EAttribute getNode_Nodename();
	
	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.emf.henshin.tests.testmodel.Val <em>Val</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Val</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Val
	 * @generated
	 */
	EClass getVal();
	
	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.tests.testmodel.Val#getIntvl <em>Intvl</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Intvl</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Val#getIntvl()
	 * @see #getVal()
	 * @generated
	 */
	EAttribute getVal_Intvl();
	
	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.tests.testmodel.Val#getValname <em>Valname</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valname</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Val#getValname()
	 * @see #getVal()
	 * @generated
	 */
	EAttribute getVal_Valname();
	
	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.henshin.tests.testmodel.Val#getIntlist <em>Intlist</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Intlist</em>'.
	 * @see org.eclipse.emf.henshin.tests.testmodel.Val#getIntlist()
	 * @see #getVal()
	 * @generated
	 */
	EAttribute getVal_Intlist();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestmodelFactory getTestmodelFactory();
	
	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that
	 * represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.tests.testmodel.impl.contImpl <em>cont</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.tests.testmodel.impl.contImpl
		 * @see org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl#getcont()
		 * @generated
		 */
		EClass CONT = eINSTANCE.getcont();
		
		/**
		 * The meta object literal for the '<em><b>Contains Node</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference CONT__CONTAINS_NODE = eINSTANCE.getcont_ContainsNode();
		
		/**
		 * The meta object literal for the '<em><b>Contains Val</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!--
		 * end-user-doc -->
		 * @generated
		 */
		EReference CONT__CONTAINS_VAL = eINSTANCE.getcont_ContainsVal();
		
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.tests.testmodel.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.tests.testmodel.impl.NodeImpl
		 * @see org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();
		
		/**
		 * The meta object literal for the '<em><b>Has Vals</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__HAS_VALS = eINSTANCE.getNode_HasVals();
		
		/**
		 * The meta object literal for the '<em><b>Parent Node</b></em>' reference feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__PARENT_NODE = eINSTANCE.getNode_ParentNode();
		
		/**
		 * The meta object literal for the '<em><b>Child Nodes</b></em>' reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__CHILD_NODES = eINSTANCE.getNode_ChildNodes();
		
		/**
		 * The meta object literal for the '<em><b>Nodename</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NODE__NODENAME = eINSTANCE.getNode_Nodename();
		
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.tests.testmodel.impl.ValImpl <em>Val</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.tests.testmodel.impl.ValImpl
		 * @see org.eclipse.emf.henshin.tests.testmodel.impl.TestmodelPackageImpl#getVal()
		 * @generated
		 */
		EClass VAL = eINSTANCE.getVal();
		
		/**
		 * The meta object literal for the '<em><b>Intvl</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAL__INTVL = eINSTANCE.getVal_Intvl();
		
		/**
		 * The meta object literal for the '<em><b>Valname</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAL__VALNAME = eINSTANCE.getVal_Valname();

		/**
		 * The meta object literal for the '<em><b>Intlist</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VAL__INTLIST = eINSTANCE.getVal_Intlist();
		
	}
	
} // TestmodelPackage
