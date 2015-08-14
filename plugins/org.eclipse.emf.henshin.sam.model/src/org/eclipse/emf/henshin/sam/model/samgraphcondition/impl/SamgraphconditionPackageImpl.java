/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samgraphcondition.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
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
import org.eclipse.emf.henshin.sam.model.samgraphcondition.GraphCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalGCCoupling;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.LogicalOperator;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.NegatedCondition;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.ProxyNode;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantification;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.Quantor;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionFactory;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.TerminationCondition;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
import org.eclipse.emf.henshin.sam.model.samrules.impl.SamrulesPackageImpl;
import org.eclipse.emf.henshin.sam.model.samtrace.SamtracePackage;
import org.eclipse.emf.henshin.sam.model.samtrace.impl.SamtracePackageImpl;
import org.eclipse.emf.henshin.sam.model.samtypegraph.SamtypegraphPackage;
import org.eclipse.emf.henshin.sam.model.samtypegraph.impl.SamtypegraphPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SamgraphconditionPackageImpl extends EPackageImpl implements SamgraphconditionPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass proxyNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass graphConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass logicalGCCouplingEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass quantificationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass terminationConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass negatedConditionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum quantorEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum logicalOperatorEEnum = null;

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
	 * @see org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SamgraphconditionPackageImpl() {
		super(eNS_URI, SamgraphconditionFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SamgraphconditionPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SamgraphconditionPackage init() {
		if (isInited) return (SamgraphconditionPackage)EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI);

		// Obtain or create and register package
		SamgraphconditionPackageImpl theSamgraphconditionPackage = (SamgraphconditionPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SamgraphconditionPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SamgraphconditionPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SamtypegraphPackageImpl theSamtypegraphPackage = (SamtypegraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) instanceof SamtypegraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) : SamtypegraphPackage.eINSTANCE);
		SamalgebraPackageImpl theSamalgebraPackage = (SamalgebraPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) instanceof SamalgebraPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) : SamalgebraPackage.eINSTANCE);
		AlgebraUsePackageImpl theAlgebraUsePackage = (AlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) instanceof AlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) : AlgebraUsePackage.eINSTANCE);
		SignaturePackageImpl theSignaturePackage = (SignaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) instanceof SignaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) : SignaturePackage.eINSTANCE);
		TermAlgebraUsePackageImpl theTermAlgebraUsePackage = (TermAlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) instanceof TermAlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) : TermAlgebraUsePackage.eINSTANCE);
		SamgraphPackageImpl theSamgraphPackage = (SamgraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) instanceof SamgraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) : SamgraphPackage.eINSTANCE);
		SamrulesPackageImpl theSamrulesPackage = (SamrulesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) instanceof SamrulesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI) : SamrulesPackage.eINSTANCE);
		SamtracePackageImpl theSamtracePackage = (SamtracePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) instanceof SamtracePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) : SamtracePackage.eINSTANCE);
		SamannotationPackageImpl theSamannotationPackage = (SamannotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) instanceof SamannotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) : SamannotationPackage.eINSTANCE);

		// Create package meta-data objects
		theSamgraphconditionPackage.createPackageContents();
		theSamtypegraphPackage.createPackageContents();
		theSamalgebraPackage.createPackageContents();
		theAlgebraUsePackage.createPackageContents();
		theSignaturePackage.createPackageContents();
		theTermAlgebraUsePackage.createPackageContents();
		theSamgraphPackage.createPackageContents();
		theSamrulesPackage.createPackageContents();
		theSamtracePackage.createPackageContents();
		theSamannotationPackage.createPackageContents();

		// Initialize created meta-data
		theSamgraphconditionPackage.initializePackageContents();
		theSamtypegraphPackage.initializePackageContents();
		theSamalgebraPackage.initializePackageContents();
		theAlgebraUsePackage.initializePackageContents();
		theSignaturePackage.initializePackageContents();
		theTermAlgebraUsePackage.initializePackageContents();
		theSamgraphPackage.initializePackageContents();
		theSamrulesPackage.initializePackageContents();
		theSamtracePackage.initializePackageContents();
		theSamannotationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSamgraphconditionPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SamgraphconditionPackage.eNS_URI, theSamgraphconditionPackage);
		return theSamgraphconditionPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProxyNode() {
		return proxyNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProxyNode_ReferencedNode() {
		return (EReference)proxyNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGraphCondition() {
		return graphConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphCondition_Name() {
		return (EAttribute)graphConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogicalGCCoupling() {
		return logicalGCCouplingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogicalGCCoupling_Operator() {
		return (EAttribute)logicalGCCouplingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLogicalGCCoupling_Operands() {
		return (EReference)logicalGCCouplingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getQuantification() {
		return quantificationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getQuantification_Quantor() {
		return (EAttribute)quantificationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantification_Nesting() {
		return (EReference)quantificationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantification_Context() {
		return (EReference)quantificationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getQuantification_AttributeCondition() {
		return (EReference)quantificationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTerminationCondition() {
		return terminationConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTerminationCondition_IsTrue() {
		return (EAttribute)terminationConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNegatedCondition() {
		return negatedConditionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getNegatedCondition_Operand() {
		return (EReference)negatedConditionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getQuantor() {
		return quantorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getLogicalOperator() {
		return logicalOperatorEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamgraphconditionFactory getSamgraphconditionFactory() {
		return (SamgraphconditionFactory)getEFactoryInstance();
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
		proxyNodeEClass = createEClass(PROXY_NODE);
		createEReference(proxyNodeEClass, PROXY_NODE__REFERENCED_NODE);

		graphConditionEClass = createEClass(GRAPH_CONDITION);
		createEAttribute(graphConditionEClass, GRAPH_CONDITION__NAME);

		logicalGCCouplingEClass = createEClass(LOGICAL_GC_COUPLING);
		createEAttribute(logicalGCCouplingEClass, LOGICAL_GC_COUPLING__OPERATOR);
		createEReference(logicalGCCouplingEClass, LOGICAL_GC_COUPLING__OPERANDS);

		quantificationEClass = createEClass(QUANTIFICATION);
		createEAttribute(quantificationEClass, QUANTIFICATION__QUANTOR);
		createEReference(quantificationEClass, QUANTIFICATION__NESTING);
		createEReference(quantificationEClass, QUANTIFICATION__CONTEXT);
		createEReference(quantificationEClass, QUANTIFICATION__ATTRIBUTE_CONDITION);

		terminationConditionEClass = createEClass(TERMINATION_CONDITION);
		createEAttribute(terminationConditionEClass, TERMINATION_CONDITION__IS_TRUE);

		negatedConditionEClass = createEClass(NEGATED_CONDITION);
		createEReference(negatedConditionEClass, NEGATED_CONDITION__OPERAND);

		// Create enums
		quantorEEnum = createEEnum(QUANTOR);
		logicalOperatorEEnum = createEEnum(LOGICAL_OPERATOR);
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
		SamgraphPackage theSamgraphPackage = (SamgraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI);
		SamtypegraphPackage theSamtypegraphPackage = (SamtypegraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI);
		SamannotationPackage theSamannotationPackage = (SamannotationPackage)EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI);
		AlgebraUsePackage theAlgebraUsePackage = (AlgebraUsePackage)EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		proxyNodeEClass.getESuperTypes().add(theSamgraphPackage.getNode());
		graphConditionEClass.getESuperTypes().add(theSamtypegraphPackage.getTypeGraphCondition());
		graphConditionEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		logicalGCCouplingEClass.getESuperTypes().add(this.getGraphCondition());
		quantificationEClass.getESuperTypes().add(this.getGraphCondition());
		terminationConditionEClass.getESuperTypes().add(this.getGraphCondition());
		negatedConditionEClass.getESuperTypes().add(this.getGraphCondition());

		// Initialize classes and features; add operations and parameters
		initEClass(proxyNodeEClass, ProxyNode.class, "ProxyNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProxyNode_ReferencedNode(), theSamgraphPackage.getNode(), null, "referencedNode", null, 1, 1, ProxyNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(graphConditionEClass, GraphCondition.class, "GraphCondition", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGraphCondition_Name(), ecorePackage.getEString(), "name", null, 0, 1, GraphCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(logicalGCCouplingEClass, LogicalGCCoupling.class, "LogicalGCCoupling", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLogicalGCCoupling_Operator(), this.getLogicalOperator(), "operator", null, 0, 1, LogicalGCCoupling.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLogicalGCCoupling_Operands(), this.getGraphCondition(), null, "operands", null, 2, -1, LogicalGCCoupling.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(quantificationEClass, Quantification.class, "Quantification", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getQuantification_Quantor(), this.getQuantor(), "quantor", null, 0, 1, Quantification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantification_Nesting(), this.getGraphCondition(), null, "nesting", null, 1, 1, Quantification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantification_Context(), theSamgraphPackage.getGraph(), null, "context", null, 1, 1, Quantification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getQuantification_AttributeCondition(), theAlgebraUsePackage.getOperationUse(), null, "attributeCondition", null, 0, 1, Quantification.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(terminationConditionEClass, TerminationCondition.class, "TerminationCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTerminationCondition_IsTrue(), ecorePackage.getEBoolean(), "isTrue", null, 0, 1, TerminationCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(negatedConditionEClass, NegatedCondition.class, "NegatedCondition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNegatedCondition_Operand(), this.getGraphCondition(), null, "operand", null, 0, 1, NegatedCondition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(quantorEEnum, Quantor.class, "Quantor");
		addEEnumLiteral(quantorEEnum, Quantor.EXISTS);
		addEEnumLiteral(quantorEEnum, Quantor.FORALL);

		initEEnum(logicalOperatorEEnum, LogicalOperator.class, "LogicalOperator");
		addEEnumLiteral(logicalOperatorEEnum, LogicalOperator.DISJUNCTION);
		addEEnumLiteral(logicalOperatorEEnum, LogicalOperator.CONJUNCTION);

		// Create resource
		createResource(eNS_URI);
	}

} //SamgraphconditionPackageImpl
