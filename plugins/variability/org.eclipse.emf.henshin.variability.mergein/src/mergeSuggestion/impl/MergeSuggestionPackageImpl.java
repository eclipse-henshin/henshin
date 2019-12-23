/**
 */
package mergeSuggestion.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;

import mergeSuggestion.CloneDetectionResult;
import mergeSuggestion.CloneGroup;
import mergeSuggestion.CloneGroupElement;
import mergeSuggestion.MergeAC;
import mergeSuggestion.MergeNAC;
import mergeSuggestion.MergePAC;
import mergeSuggestion.MergeRule;
import mergeSuggestion.MergeRuleElement;
import mergeSuggestion.MergeSuggestion;
import mergeSuggestion.MergeSuggestionFactory;
import mergeSuggestion.MergeSuggestionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MergeSuggestionPackageImpl extends EPackageImpl implements MergeSuggestionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeSuggestionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeRuleElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeNACEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergePACEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mergeACEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cloneGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cloneGroupElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cloneDetectionResultEClass = null;

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
	 * @see mergeSuggestion.MergeSuggestionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MergeSuggestionPackageImpl() {
		super(eNS_URI, MergeSuggestionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link MergeSuggestionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MergeSuggestionPackage init() {
		if (isInited) return (MergeSuggestionPackage)EPackage.Registry.INSTANCE.getEPackage(MergeSuggestionPackage.eNS_URI);

		// Obtain or create and register package
		MergeSuggestionPackageImpl theMergeSuggestionPackage = (MergeSuggestionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MergeSuggestionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MergeSuggestionPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		HenshinPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMergeSuggestionPackage.createPackageContents();

		// Initialize created meta-data
		theMergeSuggestionPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMergeSuggestionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MergeSuggestionPackage.eNS_URI, theMergeSuggestionPackage);
		return theMergeSuggestionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeSuggestion() {
		return mergeSuggestionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeSuggestion_MergeClusters() {
		return (EReference)mergeSuggestionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeSuggestion__FindMergeRule__Rule() {
		return mergeSuggestionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeRule() {
		return mergeRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeRule_MasterRule() {
		return (EReference)mergeRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeRule_Rules() {
		return (EReference)mergeRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeRule_Elements() {
		return (EReference)mergeRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMergeRule_Name() {
		return (EAttribute)mergeRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeRule_MergeNacs() {
		return (EReference)mergeRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeRule_MergePacs() {
		return (EReference)mergeRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRule__FindMergeRuleElement__GraphElement() {
		return mergeRuleEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRule__AddMergeRuleElement__MergeRuleElement() {
		return mergeRuleEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRule__AddMergeNAC__MergeNAC() {
		return mergeRuleEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRule__AddMergePAC__MergePAC() {
		return mergeRuleEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRule__FindMergeNAC__Graph() {
		return mergeRuleEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRule__FindMergePAC__Graph() {
		return mergeRuleEClass.getEOperations().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeRuleElement() {
		return mergeRuleElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeRuleElement_ReferenceElements() {
		return (EReference)mergeRuleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMergeRuleElement_Name() {
		return (EAttribute)mergeRuleElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRuleElement__IsNacElement() {
		return mergeRuleElementEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRuleElement__IsPacElement() {
		return mergeRuleElementEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRuleElement__IsLhsElement() {
		return mergeRuleElementEClass.getEOperations().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRuleElement__IsRhsElement() {
		return mergeRuleElementEClass.getEOperations().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getMergeRuleElement__IsMultiRuleElement() {
		return mergeRuleElementEClass.getEOperations().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeNAC() {
		return mergeNACEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMergeNAC_Name() {
		return (EAttribute)mergeNACEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergeNAC_ReferenceNACs() {
		return (EReference)mergeNACEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergePAC() {
		return mergePACEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMergePAC_Name() {
		return (EAttribute)mergePACEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMergePAC_ReferencePACs() {
		return (EReference)mergePACEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMergeAC() {
		return mergeACEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCloneGroup() {
		return cloneGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloneGroup_Rules() {
		return (EReference)cloneGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloneGroup_Elements() {
		return (EReference)cloneGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCloneGroupElement() {
		return cloneGroupElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloneGroupElement_Elements() {
		return (EReference)cloneGroupElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCloneDetectionResult() {
		return cloneDetectionResultEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloneDetectionResult_CloneGroups() {
		return (EReference)cloneDetectionResultEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MergeSuggestionFactory getMergeSuggestionFactory() {
		return (MergeSuggestionFactory)getEFactoryInstance();
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
		mergeSuggestionEClass = createEClass(MERGE_SUGGESTION);
		createEReference(mergeSuggestionEClass, MERGE_SUGGESTION__MERGE_CLUSTERS);
		createEOperation(mergeSuggestionEClass, MERGE_SUGGESTION___FIND_MERGE_RULE__RULE);

		mergeRuleEClass = createEClass(MERGE_RULE);
		createEReference(mergeRuleEClass, MERGE_RULE__MASTER_RULE);
		createEReference(mergeRuleEClass, MERGE_RULE__RULES);
		createEReference(mergeRuleEClass, MERGE_RULE__ELEMENTS);
		createEAttribute(mergeRuleEClass, MERGE_RULE__NAME);
		createEReference(mergeRuleEClass, MERGE_RULE__MERGE_NACS);
		createEReference(mergeRuleEClass, MERGE_RULE__MERGE_PACS);
		createEOperation(mergeRuleEClass, MERGE_RULE___FIND_MERGE_RULE_ELEMENT__GRAPHELEMENT);
		createEOperation(mergeRuleEClass, MERGE_RULE___ADD_MERGE_RULE_ELEMENT__MERGERULEELEMENT);
		createEOperation(mergeRuleEClass, MERGE_RULE___ADD_MERGE_NAC__MERGENAC);
		createEOperation(mergeRuleEClass, MERGE_RULE___ADD_MERGE_PAC__MERGEPAC);
		createEOperation(mergeRuleEClass, MERGE_RULE___FIND_MERGE_NAC__GRAPH);
		createEOperation(mergeRuleEClass, MERGE_RULE___FIND_MERGE_PAC__GRAPH);

		mergeRuleElementEClass = createEClass(MERGE_RULE_ELEMENT);
		createEReference(mergeRuleElementEClass, MERGE_RULE_ELEMENT__REFERENCE_ELEMENTS);
		createEAttribute(mergeRuleElementEClass, MERGE_RULE_ELEMENT__NAME);
		createEOperation(mergeRuleElementEClass, MERGE_RULE_ELEMENT___IS_NAC_ELEMENT);
		createEOperation(mergeRuleElementEClass, MERGE_RULE_ELEMENT___IS_PAC_ELEMENT);
		createEOperation(mergeRuleElementEClass, MERGE_RULE_ELEMENT___IS_LHS_ELEMENT);
		createEOperation(mergeRuleElementEClass, MERGE_RULE_ELEMENT___IS_RHS_ELEMENT);
		createEOperation(mergeRuleElementEClass, MERGE_RULE_ELEMENT___IS_MULTI_RULE_ELEMENT);

		mergeNACEClass = createEClass(MERGE_NAC);
		createEAttribute(mergeNACEClass, MERGE_NAC__NAME);
		createEReference(mergeNACEClass, MERGE_NAC__REFERENCE_NA_CS);

		mergePACEClass = createEClass(MERGE_PAC);
		createEAttribute(mergePACEClass, MERGE_PAC__NAME);
		createEReference(mergePACEClass, MERGE_PAC__REFERENCE_PA_CS);

		mergeACEClass = createEClass(MERGE_AC);

		cloneGroupEClass = createEClass(CLONE_GROUP);
		createEReference(cloneGroupEClass, CLONE_GROUP__RULES);
		createEReference(cloneGroupEClass, CLONE_GROUP__ELEMENTS);

		cloneGroupElementEClass = createEClass(CLONE_GROUP_ELEMENT);
		createEReference(cloneGroupElementEClass, CLONE_GROUP_ELEMENT__ELEMENTS);

		cloneDetectionResultEClass = createEClass(CLONE_DETECTION_RESULT);
		createEReference(cloneDetectionResultEClass, CLONE_DETECTION_RESULT__CLONE_GROUPS);
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
		mergeNACEClass.getESuperTypes().add(this.getMergeAC());
		mergePACEClass.getESuperTypes().add(this.getMergeAC());

		// Initialize classes, features, and operations; add parameters
		initEClass(mergeSuggestionEClass, MergeSuggestion.class, "MergeSuggestion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMergeSuggestion_MergeClusters(), this.getMergeRule(), null, "mergeClusters", null, 0, -1, MergeSuggestion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getMergeSuggestion__FindMergeRule__Rule(), this.getMergeRule(), "findMergeRule", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theHenshinPackage.getRule(), "rule", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(mergeRuleEClass, MergeRule.class, "MergeRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMergeRule_MasterRule(), theHenshinPackage.getRule(), null, "masterRule", null, 0, 1, MergeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMergeRule_Rules(), theHenshinPackage.getRule(), null, "rules", null, 0, -1, MergeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMergeRule_Elements(), this.getMergeRuleElement(), null, "elements", null, 0, -1, MergeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMergeRule_Name(), ecorePackage.getEString(), "name", null, 0, 1, MergeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMergeRule_MergeNacs(), this.getMergeNAC(), null, "mergeNacs", null, 0, -1, MergeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getMergeRule_MergePacs(), this.getMergePAC(), null, "mergePacs", null, 0, -1, MergeRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getMergeRule__FindMergeRuleElement__GraphElement(), this.getMergeRuleElement(), "findMergeRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theHenshinPackage.getGraphElement(), "graphElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMergeRule__AddMergeRuleElement__MergeRuleElement(), null, "addMergeRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMergeRuleElement(), "mergeRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMergeRule__AddMergeNAC__MergeNAC(), null, "addMergeNAC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMergeNAC(), "mergeRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMergeRule__AddMergePAC__MergePAC(), null, "addMergePAC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getMergePAC(), "mergeRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMergeRule__FindMergeNAC__Graph(), this.getMergeNAC(), "findMergeNAC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theHenshinPackage.getGraph(), "graph", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getMergeRule__FindMergePAC__Graph(), this.getMergePAC(), "findMergePAC", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theHenshinPackage.getGraph(), "graph", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(mergeRuleElementEClass, MergeRuleElement.class, "MergeRuleElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMergeRuleElement_ReferenceElements(), theHenshinPackage.getGraphElement(), null, "referenceElements", null, 0, -1, MergeRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMergeRuleElement_Name(), ecorePackage.getEString(), "name", null, 0, 1, MergeRuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEOperation(getMergeRuleElement__IsNacElement(), ecorePackage.getEBoolean(), "isNacElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getMergeRuleElement__IsPacElement(), ecorePackage.getEBoolean(), "isPacElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getMergeRuleElement__IsLhsElement(), ecorePackage.getEBoolean(), "isLhsElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getMergeRuleElement__IsRhsElement(), ecorePackage.getEBoolean(), "isRhsElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEOperation(getMergeRuleElement__IsMultiRuleElement(), ecorePackage.getEBoolean(), "isMultiRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(mergeNACEClass, MergeNAC.class, "MergeNAC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMergeNAC_Name(), ecorePackage.getEString(), "name", null, 0, 1, MergeNAC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMergeNAC_ReferenceNACs(), theHenshinPackage.getGraph(), null, "referenceNACs", null, 0, -1, MergeNAC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mergePACEClass, MergePAC.class, "MergePAC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMergePAC_Name(), ecorePackage.getEString(), "name", null, 0, 1, MergePAC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getMergePAC_ReferencePACs(), theHenshinPackage.getGraph(), null, "referencePACs", null, 0, -1, MergePAC.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(mergeACEClass, MergeAC.class, "MergeAC", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(cloneGroupEClass, CloneGroup.class, "CloneGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCloneGroup_Rules(), theHenshinPackage.getRule(), null, "rules", null, 0, -1, CloneGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCloneGroup_Elements(), this.getCloneGroupElement(), null, "elements", null, 0, -1, CloneGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cloneGroupElementEClass, CloneGroupElement.class, "CloneGroupElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCloneGroupElement_Elements(), theHenshinPackage.getGraphElement(), null, "elements", null, 0, -1, CloneGroupElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cloneDetectionResultEClass, CloneDetectionResult.class, "CloneDetectionResult", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCloneDetectionResult_CloneGroups(), this.getCloneGroup(), null, "cloneGroups", null, 0, -1, CloneDetectionResult.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //MergeSuggestionPackageImpl
