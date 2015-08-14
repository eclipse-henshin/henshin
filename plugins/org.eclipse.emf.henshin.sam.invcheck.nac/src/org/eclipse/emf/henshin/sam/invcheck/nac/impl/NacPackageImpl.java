/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.invcheck.nac.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.sam.invcheck.nac.GraphWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.MatchWithNacs;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacFactory;
import org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage;
import org.eclipse.emf.henshin.sam.invcheck.nac.NegativeApplicationCondition;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternEdge;
import org.eclipse.emf.henshin.sam.invcheck.nac.PatternNode;
import org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NacPackageImpl extends EPackageImpl implements NacPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass negativeApplicationConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchWithNacsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass graphWithNacsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patternNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass patternEdgeEClass = null;

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
	 * @see org.eclipse.emf.henshin.sam.invcheck.nac.NacPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private NacPackageImpl() {
		super(eNS_URI, NacFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link NacPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static NacPackage init() {
		if (isInited) return (NacPackage)EPackage.Registry.INSTANCE.getEPackage(NacPackage.eNS_URI);

		// Obtain or create and register package
		NacPackageImpl theNacPackage = (NacPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof NacPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new NacPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		SamtypegraphPackage.eINSTANCE.eClass();
		SamalgebraPackage.eINSTANCE.eClass();
		SamgraphPackage.eINSTANCE.eClass();
		SamrulesPackage.eINSTANCE.eClass();
		SamgraphconditionPackage.eINSTANCE.eClass();
		SamtracePackage.eINSTANCE.eClass();
		SamannotationPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theNacPackage.createPackageContents();

		// Initialize created meta-data
		theNacPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theNacPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(NacPackage.eNS_URI, theNacPackage);
		return theNacPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNegativeApplicationCondition() {
		return negativeApplicationConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNegativeApplicationCondition_Graph() {
		return (EReference)negativeApplicationConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNegativeApplicationCondition_Edges() {
		return (EReference)negativeApplicationConditionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNegativeApplicationCondition_Nodes() {
		return (EReference)negativeApplicationConditionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNegativeApplicationCondition_Name() {
		return (EAttribute)negativeApplicationConditionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMatchWithNacs() {
		return matchWithNacsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatchWithNacs_NacMatching() {
		return (EReference)matchWithNacsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGraphWithNacs() {
		return graphWithNacsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraphWithNacs_Nacs() {
		return (EReference)graphWithNacsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPatternNode() {
		return patternNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPatternNode_SameInRule() {
		return (EReference)patternNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPatternNode_SameInProp() {
		return (EReference)patternNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPatternEdge() {
		return patternEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPatternEdge_SameInRule() {
		return (EReference)patternEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPatternEdge_SameInProp() {
		return (EReference)patternEdgeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NacFactory getNacFactory() {
		return (NacFactory)getEFactoryInstance();
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
		negativeApplicationConditionEClass = createEClass(NEGATIVE_APPLICATION_CONDITION);
		createEReference(negativeApplicationConditionEClass, NEGATIVE_APPLICATION_CONDITION__GRAPH);
		createEReference(negativeApplicationConditionEClass, NEGATIVE_APPLICATION_CONDITION__EDGES);
		createEReference(negativeApplicationConditionEClass, NEGATIVE_APPLICATION_CONDITION__NODES);
		createEAttribute(negativeApplicationConditionEClass, NEGATIVE_APPLICATION_CONDITION__NAME);

		matchWithNacsEClass = createEClass(MATCH_WITH_NACS);
		createEReference(matchWithNacsEClass, MATCH_WITH_NACS__NAC_MATCHING);

		graphWithNacsEClass = createEClass(GRAPH_WITH_NACS);
		createEReference(graphWithNacsEClass, GRAPH_WITH_NACS__NACS);

		patternNodeEClass = createEClass(PATTERN_NODE);
		createEReference(patternNodeEClass, PATTERN_NODE__SAME_IN_RULE);
		createEReference(patternNodeEClass, PATTERN_NODE__SAME_IN_PROP);

		patternEdgeEClass = createEClass(PATTERN_EDGE);
		createEReference(patternEdgeEClass, PATTERN_EDGE__SAME_IN_RULE);
		createEReference(patternEdgeEClass, PATTERN_EDGE__SAME_IN_PROP);
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
		SamannotationPackage theSamannotationPackage = (SamannotationPackage)EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI);
		SamgraphPackage theSamgraphPackage = (SamgraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI);
		SamtracePackage theSamtracePackage = (SamtracePackage)EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		negativeApplicationConditionEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		matchWithNacsEClass.getESuperTypes().add(theSamtracePackage.getMatch());
		graphWithNacsEClass.getESuperTypes().add(theSamgraphPackage.getGraph());
		patternNodeEClass.getESuperTypes().add(theSamgraphPackage.getNode());
		patternEdgeEClass.getESuperTypes().add(theSamgraphPackage.getEdge());

		// Initialize classes and features; add operations and parameters
		initEClass(negativeApplicationConditionEClass, NegativeApplicationCondition.class, "NegativeApplicationCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNegativeApplicationCondition_Graph(), theSamgraphPackage.getGraph(), null, "graph", null, 0, 1, NegativeApplicationCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNegativeApplicationCondition_Edges(), theSamgraphPackage.getEdge(), null, "edges", null, 0, -1, NegativeApplicationCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getNegativeApplicationCondition_Nodes(), theSamgraphPackage.getNode(), null, "nodes", null, 0, -1, NegativeApplicationCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNegativeApplicationCondition_Name(), ecorePackage.getEString(), "name", null, 0, 1, NegativeApplicationCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(matchWithNacsEClass, MatchWithNacs.class, "MatchWithNacs", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		EGenericType g1 = createEGenericType(theSamtracePackage.getMatchEntry());
		EGenericType g2 = createEGenericType(this.getNegativeApplicationCondition());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(this.getNegativeApplicationCondition());
		g1.getETypeArguments().add(g2);
		initEReference(getMatchWithNacs_NacMatching(), g1, null, "nacMatching", null, 0, -1, MatchWithNacs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		addEOperation(matchWithNacsEClass, null, "clear", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(matchWithNacsEClass, this.getMatchWithNacs(), "copy", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(graphWithNacsEClass, GraphWithNacs.class, "GraphWithNacs", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGraphWithNacs_Nacs(), this.getNegativeApplicationCondition(), null, "nacs", null, 0, -1, GraphWithNacs.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternNodeEClass, PatternNode.class, "PatternNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatternNode_SameInRule(), theSamgraphPackage.getNode(), null, "sameInRule", null, 0, 1, PatternNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatternNode_SameInProp(), theSamgraphPackage.getNode(), null, "sameInProp", null, 0, 1, PatternNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(patternEdgeEClass, PatternEdge.class, "PatternEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPatternEdge_SameInRule(), theSamgraphPackage.getEdge(), null, "sameInRule", null, 0, 1, PatternEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPatternEdge_SameInProp(), theSamgraphPackage.getEdge(), null, "sameInProp", null, 0, 1, PatternEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //NacPackageImpl
