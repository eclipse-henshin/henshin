/**
 */
package metrics.impl;

import metrics.MetricsFactory;
import metrics.MetricsPackage;
import metrics.RuleMetrics;
import metrics.RuleSetMetrics;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MetricsPackageImpl extends EPackageImpl implements MetricsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleSetMetricsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleMetricsEClass = null;

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
	 * @see metrics.MetricsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MetricsPackageImpl() {
		super(eNS_URI, MetricsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MetricsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MetricsPackage init() {
		if (isInited) return (MetricsPackage)EPackage.Registry.INSTANCE.getEPackage(MetricsPackage.eNS_URI);

		// Obtain or create and register package
		MetricsPackageImpl theMetricsPackage = (MetricsPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MetricsPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MetricsPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMetricsPackage.createPackageContents();

		// Initialize created meta-data
		theMetricsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMetricsPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MetricsPackage.eNS_URI, theMetricsPackage);
		return theMetricsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleSetMetrics() {
		return ruleSetMetricsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleSetMetrics_RuleMetrics() {
		return (EReference)ruleSetMetricsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_NumberOfRules() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleSetMetrics_RuleSet() {
		return (EReference)ruleSetMetricsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfNodes() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfEdges() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfAttributes() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfLhsNodes() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfLhsEdges() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfLhsAttributes() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfAnnotatedNodes() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfAnnotatedEdges() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfAnnotatedAttributes() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfNACs() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleSetMetrics_TotalNumberOfPACs() {
		return (EAttribute)ruleSetMetricsEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRuleSetMetrics__FindRuleMetrics__Rule() {
		return ruleSetMetricsEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRuleSetMetrics__CreatePresentationString() {
		return ruleSetMetricsEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleMetrics() {
		return ruleMetricsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfNodes() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfEdges() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfAttributes() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfLhsNodes() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfLhsEdges() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfLhsAttributes() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleMetrics_Rule() {
		return (EReference)ruleMetricsEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfNACs() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfPACs() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfAnnotatedNodes() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfAnnotatedEdges() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleMetrics_NumberOfAnnotatedAttributes() {
		return (EAttribute)ruleMetricsEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricsFactory getMetricsFactory() {
		return (MetricsFactory)getEFactoryInstance();
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
		ruleSetMetricsEClass = createEClass(RULE_SET_METRICS);
		createEReference(ruleSetMetricsEClass, RULE_SET_METRICS__RULE_METRICS);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__NUMBER_OF_RULES);
		createEReference(ruleSetMetricsEClass, RULE_SET_METRICS__RULE_SET);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_NODES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_EDGES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_ATTRIBUTES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_NODES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_EDGES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_LHS_ATTRIBUTES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_NODES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_EDGES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_ANNOTATED_ATTRIBUTES);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_NA_CS);
		createEAttribute(ruleSetMetricsEClass, RULE_SET_METRICS__TOTAL_NUMBER_OF_PA_CS);
		createEOperation(ruleSetMetricsEClass, RULE_SET_METRICS___FIND_RULE_METRICS__RULE);
		createEOperation(ruleSetMetricsEClass, RULE_SET_METRICS___CREATE_PRESENTATION_STRING);

		ruleMetricsEClass = createEClass(RULE_METRICS);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_NODES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_EDGES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_ATTRIBUTES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_LHS_NODES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_LHS_EDGES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_LHS_ATTRIBUTES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_ANNOTATED_NODES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_ANNOTATED_EDGES);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_ANNOTATED_ATTRIBUTES);
		createEReference(ruleMetricsEClass, RULE_METRICS__RULE);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_NA_CS);
		createEAttribute(ruleMetricsEClass, RULE_METRICS__NUMBER_OF_PA_CS);
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
		HenshinPackage theHenshinPackage = (HenshinPackage)EPackage.Registry.INSTANCE.getEPackage(HenshinPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(ruleSetMetricsEClass, RuleSetMetrics.class, "RuleSetMetrics", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleSetMetrics_RuleMetrics(), this.getRuleMetrics(), null, "ruleMetrics", null, 0, -1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_NumberOfRules(), ecorePackage.getEInt(), "numberOfRules", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleSetMetrics_RuleSet(), theHenshinPackage.getRule(), null, "ruleSet", null, 0, -1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfNodes(), ecorePackage.getEInt(), "totalNumberOfNodes", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfEdges(), ecorePackage.getEInt(), "totalNumberOfEdges", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfAttributes(), ecorePackage.getEInt(), "totalNumberOfAttributes", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfLhsNodes(), ecorePackage.getEInt(), "totalNumberOfLhsNodes", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfLhsEdges(), ecorePackage.getEInt(), "totalNumberOfLhsEdges", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfLhsAttributes(), ecorePackage.getEInt(), "totalNumberOfLhsAttributes", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfAnnotatedNodes(), ecorePackage.getEInt(), "totalNumberOfAnnotatedNodes", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfAnnotatedEdges(), ecorePackage.getEInt(), "totalNumberOfAnnotatedEdges", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfAnnotatedAttributes(), ecorePackage.getEInt(), "totalNumberOfAnnotatedAttributes", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfNACs(), ecorePackage.getEInt(), "totalNumberOfNACs", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleSetMetrics_TotalNumberOfPACs(), ecorePackage.getEInt(), "totalNumberOfPACs", null, 0, 1, RuleSetMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getRuleSetMetrics__FindRuleMetrics__Rule(), this.getRuleMetrics(), "findRuleMetrics", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theHenshinPackage.getRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getRuleSetMetrics__CreatePresentationString(), ecorePackage.getEString(), "createPresentationString", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(ruleMetricsEClass, RuleMetrics.class, "RuleMetrics", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRuleMetrics_NumberOfNodes(), ecorePackage.getEInt(), "numberOfNodes", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfEdges(), ecorePackage.getEInt(), "numberOfEdges", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfAttributes(), ecorePackage.getEInt(), "numberOfAttributes", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfLhsNodes(), ecorePackage.getEInt(), "numberOfLhsNodes", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfLhsEdges(), ecorePackage.getEInt(), "numberOfLhsEdges", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfLhsAttributes(), ecorePackage.getEInt(), "numberOfLhsAttributes", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfAnnotatedNodes(), ecorePackage.getEInt(), "numberOfAnnotatedNodes", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfAnnotatedEdges(), ecorePackage.getEInt(), "numberOfAnnotatedEdges", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfAnnotatedAttributes(), ecorePackage.getEInt(), "numberOfAnnotatedAttributes", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleMetrics_Rule(), theHenshinPackage.getRule(), null, "rule", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfNACs(), ecorePackage.getEInt(), "numberOfNACs", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleMetrics_NumberOfPACs(), ecorePackage.getEInt(), "numberOfPACs", null, 0, 1, RuleMetrics.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //MetricsPackageImpl
