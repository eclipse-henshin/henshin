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
package org.eclipse.emf.henshin.tests.testmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.tests.testmodel.Node;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelFactory;
import org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage;
import org.eclipse.emf.henshin.tests.testmodel.Val;
import org.eclipse.emf.henshin.tests.testmodel.cont;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class TestmodelPackageImpl extends EPackageImpl implements TestmodelPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass contEClass = null;
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass nodeEClass = null;
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass valEClass = null;
	
	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory
	 * method {@link #init init()}, which also performs initialization of the
	 * package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.henshin.tests.testmodel.TestmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TestmodelPackageImpl() {
		super(eNS_URI, TestmodelFactory.eINSTANCE);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;
	
	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model,
	 * and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link TestmodelPackage#eINSTANCE} when
	 * that field is accessed. Clients should not invoke it directly. Instead,
	 * they should simply access that field to obtain the package. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TestmodelPackage init() {
		if (isInited)
			return (TestmodelPackage) EPackage.Registry.INSTANCE
					.getEPackage(TestmodelPackage.eNS_URI);
		
		// Obtain or create and register package
		TestmodelPackageImpl theTestmodelPackage = (TestmodelPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof TestmodelPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new TestmodelPackageImpl());
		
		isInited = true;
		
		// Create package meta-data objects
		theTestmodelPackage.createPackageContents();
		
		// Initialize created meta-data
		theTestmodelPackage.initializePackageContents();
		
		// Mark meta-data to indicate it can't be changed
		theTestmodelPackage.freeze();
		
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TestmodelPackage.eNS_URI, theTestmodelPackage);
		return theTestmodelPackage;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getcont() {
		return contEClass;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getcont_ContainsNode() {
		return (EReference) contEClass.getEStructuralFeatures().get(0);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getcont_ContainsVal() {
		return (EReference) contEClass.getEStructuralFeatures().get(1);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getNode() {
		return nodeEClass;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNode_HasVals() {
		return (EReference) nodeEClass.getEStructuralFeatures().get(0);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNode_ParentNode() {
		return (EReference) nodeEClass.getEStructuralFeatures().get(1);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getNode_ChildNodes() {
		return (EReference) nodeEClass.getEStructuralFeatures().get(2);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getNode_Nodename() {
		return (EAttribute) nodeEClass.getEStructuralFeatures().get(3);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVal() {
		return valEClass;
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVal_Intvl() {
		return (EAttribute) valEClass.getEStructuralFeatures().get(0);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVal_Valname() {
		return (EAttribute) valEClass.getEStructuralFeatures().get(1);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public TestmodelFactory getTestmodelFactory() {
		return (TestmodelFactory) getEFactoryInstance();
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;
	
	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;
		
		// Create classes and their features
		contEClass = createEClass(CONT);
		createEReference(contEClass, CONT__CONTAINS_NODE);
		createEReference(contEClass, CONT__CONTAINS_VAL);
		
		nodeEClass = createEClass(NODE);
		createEReference(nodeEClass, NODE__HAS_VALS);
		createEReference(nodeEClass, NODE__PARENT_NODE);
		createEReference(nodeEClass, NODE__CHILD_NODES);
		createEAttribute(nodeEClass, NODE__NODENAME);
		
		valEClass = createEClass(VAL);
		createEAttribute(valEClass, VAL__INTVL);
		createEAttribute(valEClass, VAL__VALNAME);
	}
	
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;
	
	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;
		
		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);
		
		// Create type parameters
		
		// Set bounds for type parameters
		
		// Add supertypes to classes
		
		// Initialize classes and features; add operations and parameters
		initEClass(contEClass, cont.class, "cont", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getcont_ContainsNode(), this.getNode(), null, "containsNode", null, 0, -1,
				cont.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getcont_ContainsVal(), this.getVal(), null, "containsVal", null, 0, -1,
				cont.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		
		initEClass(nodeEClass, Node.class, "Node", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNode_HasVals(), this.getVal(), null, "hasVals", null, 0, -1, Node.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNode_ParentNode(), this.getNode(), this.getNode_ChildNodes(),
				"parentNode", null, 0, 1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEReference(getNode_ChildNodes(), this.getNode(), this.getNode_ParentNode(),
				"childNodes", null, 0, -1, Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);
		initEAttribute(getNode_Nodename(), ecorePackage.getEString(), "nodename", null, 0, 1,
				Node.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		
		initEClass(valEClass, Val.class, "Val", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVal_Intvl(), ecorePackage.getEInt(), "intvl", null, 0, 1, Val.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getVal_Valname(), ecorePackage.getEString(), "valname", null, 0, 1,
				Val.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		
		// Create resource
		createResource(eNS_URI);
	}
	
} // TestmodelPackageImpl
