/**
 */
package nestedconstraintmodel.impl;

import graph.GraphPackage;

import graph.impl.GraphPackageImpl;

import laxcondition.LaxconditionPackage;

import laxcondition.impl.LaxconditionPackageImpl;

import nestedcondition.NestedconditionPackage;

import nestedcondition.impl.NestedconditionPackageImpl;

import nestedconstraintmodel.NestedConstraintModel;
import nestedconstraintmodel.NestedconstraintmodelFactory;
import nestedconstraintmodel.NestedconstraintmodelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NestedconstraintmodelPackageImpl extends EPackageImpl implements NestedconstraintmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass nestedConstraintModelEClass = null;

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
	 * @see nestedconstraintmodel.NestedconstraintmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NestedconstraintmodelPackageImpl() {
		super(eNS_URI, NestedconstraintmodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NestedconstraintmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NestedconstraintmodelPackage init() {
		if (isInited) return (NestedconstraintmodelPackage)EPackage.Registry.INSTANCE.getEPackage(NestedconstraintmodelPackage.eNS_URI);

		// Obtain or create and register package
		NestedconstraintmodelPackageImpl theNestedconstraintmodelPackage = (NestedconstraintmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NestedconstraintmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NestedconstraintmodelPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		NestedconditionPackageImpl theNestedconditionPackage = (NestedconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(NestedconditionPackage.eNS_URI) instanceof NestedconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(NestedconditionPackage.eNS_URI) : NestedconditionPackage.eINSTANCE);
		GraphPackageImpl theGraphPackage = (GraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI) instanceof GraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(GraphPackage.eNS_URI) : GraphPackage.eINSTANCE);
		LaxconditionPackageImpl theLaxconditionPackage = (LaxconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI) instanceof LaxconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI) : LaxconditionPackage.eINSTANCE);

		// Create package meta-data objects
		theNestedconstraintmodelPackage.createPackageContents();
		theNestedconditionPackage.createPackageContents();
		theGraphPackage.createPackageContents();
		theLaxconditionPackage.createPackageContents();

		// Initialize created meta-data
		theNestedconstraintmodelPackage.initializePackageContents();
		theNestedconditionPackage.initializePackageContents();
		theGraphPackage.initializePackageContents();
		theLaxconditionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNestedconstraintmodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NestedconstraintmodelPackage.eNS_URI, theNestedconstraintmodelPackage);
		return theNestedconstraintmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNestedConstraintModel() {
		return nestedConstraintModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNestedConstraintModel_Name() {
		return (EAttribute)nestedConstraintModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNestedConstraintModel_Nestedconstrainmodels() {
		return (EReference)nestedConstraintModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NestedconstraintmodelFactory getNestedconstraintmodelFactory() {
		return (NestedconstraintmodelFactory)getEFactoryInstance();
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
		nestedConstraintModelEClass = createEClass(NESTED_CONSTRAINT_MODEL);
		createEAttribute(nestedConstraintModelEClass, NESTED_CONSTRAINT_MODEL__NAME);
		createEReference(nestedConstraintModelEClass, NESTED_CONSTRAINT_MODEL__NESTEDCONSTRAINMODELS);
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

		// Obtain other dependent packages
		NestedconditionPackage theNestedconditionPackage = (NestedconditionPackage)EPackage.Registry.INSTANCE.getEPackage(NestedconditionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(nestedConstraintModelEClass, NestedConstraintModel.class, "NestedConstraintModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNestedConstraintModel_Name(), ecorePackage.getEString(), "name", null, 0, 1, NestedConstraintModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNestedConstraintModel_Nestedconstrainmodels(), theNestedconditionPackage.getNestedConstraint(), null, "nestedconstrainmodels", null, 0, -1, NestedConstraintModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //NestedconstraintmodelPackageImpl
