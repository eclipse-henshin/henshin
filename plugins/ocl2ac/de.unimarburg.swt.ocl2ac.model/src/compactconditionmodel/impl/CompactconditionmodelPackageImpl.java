/**
 */
package compactconditionmodel.impl;

import compactconditionmodel.CompactConditionModel;
import compactconditionmodel.CompactconditionmodelFactory;
import compactconditionmodel.CompactconditionmodelPackage;

import laxcondition.LaxconditionPackage;

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
public class CompactconditionmodelPackageImpl extends EPackageImpl implements CompactconditionmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compactConditionModelEClass = null;

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
	 * @see compactconditionmodel.CompactconditionmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CompactconditionmodelPackageImpl() {
		super(eNS_URI, CompactconditionmodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link CompactconditionmodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CompactconditionmodelPackage init() {
		if (isInited) return (CompactconditionmodelPackage)EPackage.Registry.INSTANCE.getEPackage(CompactconditionmodelPackage.eNS_URI);

		// Obtain or create and register package
		CompactconditionmodelPackageImpl theCompactconditionmodelPackage = (CompactconditionmodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CompactconditionmodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new CompactconditionmodelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		LaxconditionPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCompactconditionmodelPackage.createPackageContents();

		// Initialize created meta-data
		theCompactconditionmodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCompactconditionmodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CompactconditionmodelPackage.eNS_URI, theCompactconditionmodelPackage);
		return theCompactconditionmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompactConditionModel() {
		return compactConditionModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompactConditionModel_Name() {
		return (EAttribute)compactConditionModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompactConditionModel_Compactconditions() {
		return (EReference)compactConditionModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompactConditionModel_TypeGraph() {
		return (EReference)compactConditionModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompactconditionmodelFactory getCompactconditionmodelFactory() {
		return (CompactconditionmodelFactory)getEFactoryInstance();
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
		compactConditionModelEClass = createEClass(COMPACT_CONDITION_MODEL);
		createEAttribute(compactConditionModelEClass, COMPACT_CONDITION_MODEL__NAME);
		createEReference(compactConditionModelEClass, COMPACT_CONDITION_MODEL__COMPACTCONDITIONS);
		createEReference(compactConditionModelEClass, COMPACT_CONDITION_MODEL__TYPE_GRAPH);
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
		LaxconditionPackage theLaxconditionPackage = (LaxconditionPackage)EPackage.Registry.INSTANCE.getEPackage(LaxconditionPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(compactConditionModelEClass, CompactConditionModel.class, "CompactConditionModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompactConditionModel_Name(), ecorePackage.getEString(), "name", null, 0, 1, CompactConditionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompactConditionModel_Compactconditions(), theLaxconditionPackage.getCondition(), null, "compactconditions", null, 0, -1, CompactConditionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompactConditionModel_TypeGraph(), ecorePackage.getEPackage(), null, "typeGraph", null, 1, 1, CompactConditionModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CompactconditionmodelPackageImpl
