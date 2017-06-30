/**
 */
package org.eclipse.emf.henshin.tests.EGraphTestmodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

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
 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelFactory
 * @model kind="package"
 * @generated
 */
public class EGraphTestmodelPackage extends EPackageImpl {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "EGraphTestmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "http://www.eclipse.org/emf/2010/Henshin/Tests/EGraphTestmodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "EGraphTestmodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final EGraphTestmodelPackage eINSTANCE = org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode <em>Recursive Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode
	 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage#getRecursiveNode()
	 * @generated
	 */
	public static final int RECURSIVE_NODE = 0;

	/**
	 * The feature id for the '<em><b>Contained Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RECURSIVE_NODE__CONTAINED_CHILDREN = 0;

	/**
	 * The number of structural features of the '<em>Recursive Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RECURSIVE_NODE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Recursive Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int RECURSIVE_NODE_OPERATION_COUNT = 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass recursiveNodeEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private EGraphTestmodelPackage() {
		super(eNS_URI, EGraphTestmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link EGraphTestmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static EGraphTestmodelPackage init() {
		if (isInited) return (EGraphTestmodelPackage)EPackage.Registry.INSTANCE.getEPackage(EGraphTestmodelPackage.eNS_URI);

		// Obtain or create and register package
		EGraphTestmodelPackage theEGraphTestmodelPackage = (EGraphTestmodelPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof EGraphTestmodelPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new EGraphTestmodelPackage());

		isInited = true;

		// Create package meta-data objects
		theEGraphTestmodelPackage.createPackageContents();

		// Initialize created meta-data
		theEGraphTestmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theEGraphTestmodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(EGraphTestmodelPackage.eNS_URI, theEGraphTestmodelPackage);
		return theEGraphTestmodelPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode <em>Recursive Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Recursive Node</em>'.
	 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode
	 * @generated
	 */
	public EClass getRecursiveNode() {
		return recursiveNodeEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode#getContainedChildren <em>Contained Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contained Children</em>'.
	 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode#getContainedChildren()
	 * @see #getRecursiveNode()
	 * @generated
	 */
	public EReference getRecursiveNode_ContainedChildren() {
		return (EReference)recursiveNodeEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public EGraphTestmodelFactory getEGraphTestmodelFactory() {
		return (EGraphTestmodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		recursiveNodeEClass = createEClass(RECURSIVE_NODE);
		createEReference(recursiveNodeEClass, RECURSIVE_NODE__CONTAINED_CHILDREN);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
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

		// Initialize classes, features, and operations; add parameters
		initEClass(recursiveNodeEClass, RecursiveNode.class, "RecursiveNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRecursiveNode_ContainedChildren(), this.getRecursiveNode(), null, "containedChildren", null, 0, -1, RecursiveNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

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
	public interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode <em>Recursive Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.RecursiveNode
		 * @see org.eclipse.emf.henshin.tests.EGraphTestmodel.EGraphTestmodelPackage#getRecursiveNode()
		 * @generated
		 */
		public static final EClass RECURSIVE_NODE = eINSTANCE.getRecursiveNode();

		/**
		 * The meta object literal for the '<em><b>Contained Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference RECURSIVE_NODE__CONTAINED_CHILDREN = eINSTANCE.getRecursiveNode_ContainedChildren();

	}

} //EGraphTestmodelPackage
