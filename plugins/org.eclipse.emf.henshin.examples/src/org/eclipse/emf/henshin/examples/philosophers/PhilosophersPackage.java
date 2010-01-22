/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.examples.philosophers;

import org.eclipse.emf.ecore.EAttribute;
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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersFactory
 * @model kind="package"
 * @generated
 */
public class PhilosophersPackage extends EPackageImpl {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNAME = "philosophers";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_URI = "http://www.eclipse.org/emf/2010/Henshin/Examples/Philosophers";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final String eNS_PREFIX = "philosophers";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final PhilosophersPackage eINSTANCE = org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.philosophers.Table <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.philosophers.Table
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getTable()
	 * @generated
	 */
	public static final int TABLE = 0;

	/**
	 * The feature id for the '<em><b>Plates</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__PLATES = 0;

	/**
	 * The feature id for the '<em><b>Philosophers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__PHILOSOPHERS = 1;

	/**
	 * The feature id for the '<em><b>Forks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE__FORKS = 2;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int TABLE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.philosophers.Identifiable <em>Identifiable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.philosophers.Identifiable
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getIdentifiable()
	 * @generated
	 */
	public static final int IDENTIFIABLE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDENTIFIABLE__ID = 0;

	/**
	 * The number of structural features of the '<em>Identifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int IDENTIFIABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher <em>Philosopher</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.philosophers.Philosopher
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher()
	 * @generated
	 */
	public static final int PHILOSOPHER = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PHILOSOPHER__ID = IDENTIFIABLE__ID;

	/**
	 * The feature id for the '<em><b>Forks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PHILOSOPHER__FORKS = IDENTIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Plate</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PHILOSOPHER__PLATE = IDENTIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Philosopher</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PHILOSOPHER_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.philosophers.Plate <em>Plate</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.philosophers.Plate
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPlate()
	 * @generated
	 */
	public static final int PLATE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PLATE__ID = IDENTIFIABLE__ID;

	/**
	 * The feature id for the '<em><b>Forks</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PLATE__FORKS = IDENTIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Plate</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int PLATE_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.henshin.examples.philosophers.Fork <em>Fork</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.henshin.examples.philosophers.Fork
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getFork()
	 * @generated
	 */
	public static final int FORK = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FORK__ID = IDENTIFIABLE__ID;

	/**
	 * The number of structural features of the '<em>Fork</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	public static final int FORK_FEATURE_COUNT = IDENTIFIABLE_FEATURE_COUNT + 0;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass philosopherEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass plateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass forkEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass identifiableEClass = null;

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
	 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PhilosophersPackage() {
		super(eNS_URI, PhilosophersFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link PhilosophersPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PhilosophersPackage init() {
		if (isInited) return (PhilosophersPackage)EPackage.Registry.INSTANCE.getEPackage(PhilosophersPackage.eNS_URI);

		// Obtain or create and register package
		PhilosophersPackage thePhilosophersPackage = (PhilosophersPackage)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PhilosophersPackage ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PhilosophersPackage());

		isInited = true;

		// Create package meta-data objects
		thePhilosophersPackage.createPackageContents();

		// Initialize created meta-data
		thePhilosophersPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePhilosophersPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(PhilosophersPackage.eNS_URI, thePhilosophersPackage);
		return thePhilosophersPackage;
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.philosophers.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Table
	 * @generated
	 */
	public EClass getTable() {
		return tableEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.philosophers.Table#getPlates <em>Plates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plates</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Table#getPlates()
	 * @see #getTable()
	 * @generated
	 */
	public EReference getTable_Plates() {
		return (EReference)tableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.philosophers.Table#getPhilosophers <em>Philosophers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Philosophers</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Table#getPhilosophers()
	 * @see #getTable()
	 * @generated
	 */
	public EReference getTable_Philosophers() {
		return (EReference)tableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.philosophers.Table#getForks <em>Forks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Forks</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Table#getForks()
	 * @see #getTable()
	 * @generated
	 */
	public EReference getTable_Forks() {
		return (EReference)tableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher <em>Philosopher</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Philosopher</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Philosopher
	 * @generated
	 */
	public EClass getPhilosopher() {
		return philosopherEClass;
	}

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getForks <em>Forks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Forks</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Philosopher#getForks()
	 * @see #getPhilosopher()
	 * @generated
	 */
	public EReference getPhilosopher_Forks() {
		return (EReference)philosopherEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher#getPlate <em>Plate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Plate</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Philosopher#getPlate()
	 * @see #getPhilosopher()
	 * @generated
	 */
	public EReference getPhilosopher_Plate() {
		return (EReference)philosopherEClass.getEStructuralFeatures().get(1);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.philosophers.Plate <em>Plate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plate</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Plate
	 * @generated
	 */
	public EClass getPlate() {
		return plateEClass;
	}

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.henshin.examples.philosophers.Plate#getForks <em>Forks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Forks</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Plate#getForks()
	 * @see #getPlate()
	 * @generated
	 */
	public EReference getPlate_Forks() {
		return (EReference)plateEClass.getEStructuralFeatures().get(0);
	}


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.philosophers.Fork <em>Fork</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Fork</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Fork
	 * @generated
	 */
	public EClass getFork() {
		return forkEClass;
	}

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.henshin.examples.philosophers.Identifiable <em>Identifiable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Identifiable</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Identifiable
	 * @generated
	 */
	public EClass getIdentifiable() {
		return identifiableEClass;
	}

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.henshin.examples.philosophers.Identifiable#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.henshin.examples.philosophers.Identifiable#getId()
	 * @see #getIdentifiable()
	 * @generated
	 */
	public EAttribute getIdentifiable_Id() {
		return (EAttribute)identifiableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	public PhilosophersFactory getPhilosophersFactory() {
		return (PhilosophersFactory)getEFactoryInstance();
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
		tableEClass = createEClass(TABLE);
		createEReference(tableEClass, TABLE__PLATES);
		createEReference(tableEClass, TABLE__PHILOSOPHERS);
		createEReference(tableEClass, TABLE__FORKS);

		philosopherEClass = createEClass(PHILOSOPHER);
		createEReference(philosopherEClass, PHILOSOPHER__FORKS);
		createEReference(philosopherEClass, PHILOSOPHER__PLATE);

		plateEClass = createEClass(PLATE);
		createEReference(plateEClass, PLATE__FORKS);

		forkEClass = createEClass(FORK);

		identifiableEClass = createEClass(IDENTIFIABLE);
		createEAttribute(identifiableEClass, IDENTIFIABLE__ID);
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
		philosopherEClass.getESuperTypes().add(this.getIdentifiable());
		plateEClass.getESuperTypes().add(this.getIdentifiable());
		forkEClass.getESuperTypes().add(this.getIdentifiable());

		// Initialize classes and features; add operations and parameters
		initEClass(tableEClass, Table.class, "Table", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTable_Plates(), this.getPlate(), null, "plates", null, 0, -1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTable_Philosophers(), this.getPhilosopher(), null, "philosophers", null, 0, -1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTable_Forks(), this.getFork(), null, "forks", null, 0, -1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(philosopherEClass, Philosopher.class, "Philosopher", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPhilosopher_Forks(), this.getFork(), null, "forks", null, 0, -1, Philosopher.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPhilosopher_Plate(), this.getPlate(), null, "plate", null, 0, 1, Philosopher.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(plateEClass, Plate.class, "Plate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPlate_Forks(), this.getFork(), null, "forks", null, 0, -1, Plate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(forkEClass, Fork.class, "Fork", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(identifiableEClass, Identifiable.class, "Identifiable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIdentifiable_Id(), ecorePackage.getEInt(), "id", null, 0, 1, Identifiable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.philosophers.Table <em>Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.philosophers.Table
		 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getTable()
		 * @generated
		 */
		public static final EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '<em><b>Plates</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TABLE__PLATES = eINSTANCE.getTable_Plates();

		/**
		 * The meta object literal for the '<em><b>Philosophers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TABLE__PHILOSOPHERS = eINSTANCE.getTable_Philosophers();

		/**
		 * The meta object literal for the '<em><b>Forks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference TABLE__FORKS = eINSTANCE.getTable_Forks();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.philosophers.Philosopher <em>Philosopher</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.philosophers.Philosopher
		 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPhilosopher()
		 * @generated
		 */
		public static final EClass PHILOSOPHER = eINSTANCE.getPhilosopher();

		/**
		 * The meta object literal for the '<em><b>Forks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PHILOSOPHER__FORKS = eINSTANCE.getPhilosopher_Forks();

		/**
		 * The meta object literal for the '<em><b>Plate</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PHILOSOPHER__PLATE = eINSTANCE.getPhilosopher_Plate();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.philosophers.Plate <em>Plate</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.philosophers.Plate
		 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getPlate()
		 * @generated
		 */
		public static final EClass PLATE = eINSTANCE.getPlate();

		/**
		 * The meta object literal for the '<em><b>Forks</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EReference PLATE__FORKS = eINSTANCE.getPlate_Forks();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.philosophers.Fork <em>Fork</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.philosophers.Fork
		 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getFork()
		 * @generated
		 */
		public static final EClass FORK = eINSTANCE.getFork();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.henshin.examples.philosophers.Identifiable <em>Identifiable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.henshin.examples.philosophers.Identifiable
		 * @see org.eclipse.emf.henshin.examples.philosophers.PhilosophersPackage#getIdentifiable()
		 * @generated
		 */
		public static final EClass IDENTIFIABLE = eINSTANCE.getIdentifiable();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		public static final EAttribute IDENTIFIABLE__ID = eINSTANCE.getIdentifiable_Id();

	}

} //PhilosophersPackage
