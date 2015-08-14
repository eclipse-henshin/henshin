/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samtrace.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.SamalgebraPackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.AlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.algebraUse.impl.AlgebraUsePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.impl.SamalgebraPackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.SignaturePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.impl.SignaturePackageImpl;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.TermAlgebraUsePackage;
import org.eclipse.emf.henshin.sam.model.samalgebra.signature.termAlgebraUse.impl.TermAlgebraUsePackageImpl;
import org.eclipse.emf.henshin.sam.model.samannotation.SamannotationPackage;
import org.eclipse.emf.henshin.sam.model.samannotation.impl.SamannotationPackageImpl;
import org.eclipse.emf.henshin.sam.model.samgraph.SamgraphPackage;
import org.eclipse.emf.henshin.sam.model.samgraph.impl.SamgraphPackageImpl;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl;
import org.eclipse.emf.henshin.sam.model.samtrace.ContinuousTraceEntry;
import org.eclipse.emf.henshin.sam.model.samtrace.DiscreteTraceEntry;
import org.eclipse.emf.henshin.sam.model.samtrace.Match;
import org.eclipse.emf.henshin.sam.model.samtrace.ProxyEdge;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtraceFactory;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtrace.Trace;
import org.eclipse.emf.henshin.sam.model.samtrace.TraceEntry;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamtracePackageImpl extends EPackageImpl implements SamtracePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass traceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass traceEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass continuousTraceEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass discreteTraceEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass matchEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proxyEdgeEClass = null;

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
	 * @see org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SamtracePackageImpl() {
		super(eNS_URI, SamtraceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SamtracePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SamtracePackage init() {
		if (isInited) return (SamtracePackage)EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI);

		// Obtain or create and register package
		SamtracePackageImpl theSamtracePackage = (SamtracePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SamtracePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SamtracePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SamtypegraphPackageImpl theSamtypegraphPackage = (SamtypegraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) instanceof SamtypegraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) : SamtypegraphPackage.eINSTANCE);
		SamalgebraPackageImpl theSamalgebraPackage = (SamalgebraPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) instanceof SamalgebraPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) : SamalgebraPackage.eINSTANCE);
		AlgebraUsePackageImpl theAlgebraUsePackage = (AlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) instanceof AlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) : AlgebraUsePackage.eINSTANCE);
		SignaturePackageImpl theSignaturePackage = (SignaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) instanceof SignaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) : SignaturePackage.eINSTANCE);
		TermAlgebraUsePackageImpl theTermAlgebraUsePackage = (TermAlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) instanceof TermAlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) : TermAlgebraUsePackage.eINSTANCE);
		SamgraphPackageImpl theSamgraphPackage = (SamgraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) instanceof SamgraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) : SamgraphPackage.eINSTANCE);
		SamrulesPackageImpl theSamrulesPackage = (SamrulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) instanceof SamrulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) : SamrulesPackage.eINSTANCE);
		SamgraphconditionPackageImpl theSamgraphconditionPackage = (SamgraphconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) instanceof SamgraphconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) : SamgraphconditionPackage.eINSTANCE);
		SamannotationPackageImpl theSamannotationPackage = (SamannotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) instanceof SamannotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) : SamannotationPackage.eINSTANCE);

		// Create package meta-data objects
		theSamtracePackage.createPackageContents();
		theSamtypegraphPackage.createPackageContents();
		theSamalgebraPackage.createPackageContents();
		theAlgebraUsePackage.createPackageContents();
		theSignaturePackage.createPackageContents();
		theTermAlgebraUsePackage.createPackageContents();
		theSamgraphPackage.createPackageContents();
		theSamrulesPackage.createPackageContents();
		theSamgraphconditionPackage.createPackageContents();
		theSamannotationPackage.createPackageContents();

		// Initialize created meta-data
		theSamtracePackage.initializePackageContents();
		theSamtypegraphPackage.initializePackageContents();
		theSamalgebraPackage.initializePackageContents();
		theAlgebraUsePackage.initializePackageContents();
		theSignaturePackage.initializePackageContents();
		theTermAlgebraUsePackage.initializePackageContents();
		theSamgraphPackage.initializePackageContents();
		theSamrulesPackage.initializePackageContents();
		theSamgraphconditionPackage.initializePackageContents();
		theSamannotationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSamtracePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SamtracePackage.eNS_URI, theSamtracePackage);
		return theSamtracePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTrace() {
		return traceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTrace_Entries() {
		return (EReference)traceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTraceEntry() {
		return traceEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTraceEntry_State() {
		return (EReference)traceEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getContinuousTraceEntry() {
		return continuousTraceEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getContinuousTraceEntry_Duration() {
		return (EAttribute)continuousTraceEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDiscreteTraceEntry() {
		return discreteTraceEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDiscreteTraceEntry_CurrentRule() {
		return (EReference)discreteTraceEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDiscreteTraceEntry_CurrentMatch() {
		return (EReference)discreteTraceEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMatch() {
		return matchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatch_NodeMatching() {
		return (EReference)matchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatch_EdgeMatching() {
		return (EReference)matchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMatch_Size() {
		return (EAttribute)matchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMatchEntry() {
		return matchEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatchEntry_Key() {
		return (EReference)matchEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMatchEntry_Value() {
		return (EReference)matchEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProxyEdge() {
		return proxyEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProxyEdge_ReferenceEdge() {
		return (EReference)proxyEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamtraceFactory getSamtraceFactory() {
		return (SamtraceFactory)getEFactoryInstance();
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
		traceEClass = createEClass(TRACE);
		createEReference(traceEClass, TRACE__ENTRIES);

		traceEntryEClass = createEClass(TRACE_ENTRY);
		createEReference(traceEntryEClass, TRACE_ENTRY__STATE);

		continuousTraceEntryEClass = createEClass(CONTINUOUS_TRACE_ENTRY);
		createEAttribute(continuousTraceEntryEClass, CONTINUOUS_TRACE_ENTRY__DURATION);

		discreteTraceEntryEClass = createEClass(DISCRETE_TRACE_ENTRY);
		createEReference(discreteTraceEntryEClass, DISCRETE_TRACE_ENTRY__CURRENT_RULE);
		createEReference(discreteTraceEntryEClass, DISCRETE_TRACE_ENTRY__CURRENT_MATCH);

		matchEClass = createEClass(MATCH);
		createEReference(matchEClass, MATCH__NODE_MATCHING);
		createEReference(matchEClass, MATCH__EDGE_MATCHING);
		createEAttribute(matchEClass, MATCH__SIZE);

		matchEntryEClass = createEClass(MATCH_ENTRY);
		createEReference(matchEntryEClass, MATCH_ENTRY__KEY);
		createEReference(matchEntryEClass, MATCH_ENTRY__VALUE);

		proxyEdgeEClass = createEClass(PROXY_EDGE);
		createEReference(proxyEdgeEClass, PROXY_EDGE__REFERENCE_EDGE);
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
		SamrulesPackage theSamrulesPackage = (SamrulesPackage)EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI);

		// Create type parameters
		ETypeParameter matchEntryEClass_T = addETypeParameter(matchEntryEClass, "T");
		ETypeParameter matchEntryEClass_S = addETypeParameter(matchEntryEClass, "S");

		// Set bounds for type parameters

		// Add supertypes to classes
		traceEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		traceEntryEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		continuousTraceEntryEClass.getESuperTypes().add(this.getTraceEntry());
		discreteTraceEntryEClass.getESuperTypes().add(this.getTraceEntry());
		matchEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		proxyEdgeEClass.getESuperTypes().add(theSamgraphPackage.getEdge());
		proxyEdgeEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());

		// Initialize classes and features; add operations and parameters
		initEClass(traceEClass, Trace.class, "Trace", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTrace_Entries(), this.getTraceEntry(), null, "entries", null, 0, -1, Trace.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(traceEntryEClass, TraceEntry.class, "TraceEntry", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTraceEntry_State(), theSamgraphPackage.getGraph(), null, "state", null, 0, 1, TraceEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(continuousTraceEntryEClass, ContinuousTraceEntry.class, "ContinuousTraceEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getContinuousTraceEntry_Duration(), ecorePackage.getEDouble(), "duration", null, 1, 1, ContinuousTraceEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(discreteTraceEntryEClass, DiscreteTraceEntry.class, "DiscreteTraceEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDiscreteTraceEntry_CurrentRule(), theSamrulesPackage.getGraphRule(), null, "currentRule", null, 0, 1, DiscreteTraceEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDiscreteTraceEntry_CurrentMatch(), this.getMatch(), null, "currentMatch", null, 0, 1, DiscreteTraceEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(matchEClass, Match.class, "Match", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		EGenericType g1 = createEGenericType(this.getMatchEntry());
		EGenericType g2 = createEGenericType(theSamgraphPackage.getNode());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theSamgraphPackage.getNode());
		g1.getETypeArguments().add(g2);
		initEReference(getMatch_NodeMatching(), g1, null, "nodeMatching", null, 0, -1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getMatchEntry());
		g2 = createEGenericType(theSamgraphPackage.getEdge());
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(theSamgraphPackage.getEdge());
		g1.getETypeArguments().add(g2);
		initEReference(getMatch_EdgeMatching(), g1, null, "edgeMatching", null, 0, -1, Match.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMatch_Size(), ecorePackage.getEInt(), "size", null, 0, 1, Match.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		addEOperation(matchEClass, null, "clear", 0, 1, IS_UNIQUE, IS_ORDERED);

		addEOperation(matchEClass, this.getMatch(), "copy", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(matchEntryEClass, Map.Entry.class, "MatchEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(matchEntryEClass_T);
		initEReference(getMatchEntry_Key(), g1, null, "key", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(matchEntryEClass_S);
		initEReference(getMatchEntry_Value(), g1, null, "value", null, 1, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(proxyEdgeEClass, ProxyEdge.class, "ProxyEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProxyEdge_ReferenceEdge(), theSamgraphPackage.getEdge(), null, "referenceEdge", null, 1, 1, ProxyEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SamtracePackageImpl
