/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.henshin.sam.model.samrules.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
import org.eclipse.emf.henshin.sam.model.samgraphcondition.SamgraphconditionPackage;
import org.eclipse.emf.henshin.sam.model.samgraphcondition.impl.SamgraphconditionPackageImpl;
import org.eclipse.emf.henshin.sam.model.samrules.CreatedEdge;
import org.eclipse.emf.henshin.sam.model.samrules.CreatedNode;
import org.eclipse.emf.henshin.sam.model.samrules.DeletedEdge;
import org.eclipse.emf.henshin.sam.model.samrules.DeletedNode;
import org.eclipse.emf.henshin.sam.model.samrules.GraphRule;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedEdge;
import org.eclipse.emf.henshin.sam.model.samrules.PreservedNode;
import org.eclipse.emf.henshin.sam.model.samrules.RuleGraph;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesFactory;
import org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage;
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
public class SamrulesPackageImpl extends EPackageImpl implements SamrulesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass graphRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preservedNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preservedEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleGraphEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gtsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createdNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deletedNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass createdEdgeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass deletedEdgeEClass = null;

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
	 * @see org.eclipse.emf.henshin.sam.model.samrules.SamrulesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SamrulesPackageImpl() {
		super(eNS_URI, SamrulesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SamrulesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SamrulesPackage init() {
		if (isInited) return (SamrulesPackage)EPackage.Registry.INSTANCE.getEPackage(SamrulesPackage.eNS_URI);

		// Obtain or create and register package
		SamrulesPackageImpl theSamrulesPackage = (SamrulesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SamrulesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SamrulesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		SamtypegraphPackageImpl theSamtypegraphPackage = (SamtypegraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) instanceof SamtypegraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI) : SamtypegraphPackage.eINSTANCE);
		SamalgebraPackageImpl theSamalgebraPackage = (SamalgebraPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) instanceof SamalgebraPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamalgebraPackage.eNS_URI) : SamalgebraPackage.eINSTANCE);
		AlgebraUsePackageImpl theAlgebraUsePackage = (AlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) instanceof AlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI) : AlgebraUsePackage.eINSTANCE);
		SignaturePackageImpl theSignaturePackage = (SignaturePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) instanceof SignaturePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SignaturePackage.eNS_URI) : SignaturePackage.eINSTANCE);
		TermAlgebraUsePackageImpl theTermAlgebraUsePackage = (TermAlgebraUsePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) instanceof TermAlgebraUsePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TermAlgebraUsePackage.eNS_URI) : TermAlgebraUsePackage.eINSTANCE);
		SamgraphPackageImpl theSamgraphPackage = (SamgraphPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) instanceof SamgraphPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI) : SamgraphPackage.eINSTANCE);
		SamgraphconditionPackageImpl theSamgraphconditionPackage = (SamgraphconditionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) instanceof SamgraphconditionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI) : SamgraphconditionPackage.eINSTANCE);
		SamtracePackageImpl theSamtracePackage = (SamtracePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) instanceof SamtracePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamtracePackage.eNS_URI) : SamtracePackage.eINSTANCE);
		SamannotationPackageImpl theSamannotationPackage = (SamannotationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) instanceof SamannotationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SamannotationPackage.eNS_URI) : SamannotationPackage.eINSTANCE);

		// Create package meta-data objects
		theSamrulesPackage.createPackageContents();
		theSamtypegraphPackage.createPackageContents();
		theSamalgebraPackage.createPackageContents();
		theAlgebraUsePackage.createPackageContents();
		theSignaturePackage.createPackageContents();
		theTermAlgebraUsePackage.createPackageContents();
		theSamgraphPackage.createPackageContents();
		theSamgraphconditionPackage.createPackageContents();
		theSamtracePackage.createPackageContents();
		theSamannotationPackage.createPackageContents();

		// Initialize created meta-data
		theSamrulesPackage.initializePackageContents();
		theSamtypegraphPackage.initializePackageContents();
		theSamalgebraPackage.initializePackageContents();
		theAlgebraUsePackage.initializePackageContents();
		theSignaturePackage.initializePackageContents();
		theTermAlgebraUsePackage.initializePackageContents();
		theSamgraphPackage.initializePackageContents();
		theSamgraphconditionPackage.initializePackageContents();
		theSamtracePackage.initializePackageContents();
		theSamannotationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSamrulesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SamrulesPackage.eNS_URI, theSamrulesPackage);
		return theSamrulesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGraphRule() {
		return graphRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraphRule_Left() {
		return (EReference)graphRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraphRule_Right() {
		return (EReference)graphRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGraphRule_AttributeCondition() {
		return (EReference)graphRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphRule_Priority() {
		return (EAttribute)graphRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphRule_Urgent() {
		return (EAttribute)graphRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGraphRule_Name() {
		return (EAttribute)graphRuleEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPreservedNode() {
		return preservedNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPreservedNode_RefInRule() {
		return (EReference)preservedNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPreservedEdge() {
		return preservedEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPreservedEdge_RefInRule() {
		return (EReference)preservedEdgeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleGraph() {
		return ruleGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleGraph_Condition() {
		return (EReference)ruleGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGTS() {
		return gtsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTS_Rules() {
		return (EReference)gtsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGTS_Types() {
		return (EReference)gtsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreatedNode() {
		return createdNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeletedNode() {
		return deletedNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCreatedEdge() {
		return createdEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDeletedEdge() {
		return deletedEdgeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SamrulesFactory getSamrulesFactory() {
		return (SamrulesFactory)getEFactoryInstance();
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
		graphRuleEClass = createEClass(GRAPH_RULE);
		createEReference(graphRuleEClass, GRAPH_RULE__LEFT);
		createEReference(graphRuleEClass, GRAPH_RULE__RIGHT);
		createEReference(graphRuleEClass, GRAPH_RULE__ATTRIBUTE_CONDITION);
		createEAttribute(graphRuleEClass, GRAPH_RULE__PRIORITY);
		createEAttribute(graphRuleEClass, GRAPH_RULE__URGENT);
		createEAttribute(graphRuleEClass, GRAPH_RULE__NAME);

		preservedNodeEClass = createEClass(PRESERVED_NODE);
		createEReference(preservedNodeEClass, PRESERVED_NODE__REF_IN_RULE);

		preservedEdgeEClass = createEClass(PRESERVED_EDGE);
		createEReference(preservedEdgeEClass, PRESERVED_EDGE__REF_IN_RULE);

		ruleGraphEClass = createEClass(RULE_GRAPH);
		createEReference(ruleGraphEClass, RULE_GRAPH__CONDITION);

		gtsEClass = createEClass(GTS);
		createEReference(gtsEClass, GTS__RULES);
		createEReference(gtsEClass, GTS__TYPES);

		createdNodeEClass = createEClass(CREATED_NODE);

		deletedNodeEClass = createEClass(DELETED_NODE);

		createdEdgeEClass = createEClass(CREATED_EDGE);

		deletedEdgeEClass = createEClass(DELETED_EDGE);
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
		AlgebraUsePackage theAlgebraUsePackage = (AlgebraUsePackage)EPackage.Registry.INSTANCE.getEPackage(AlgebraUsePackage.eNS_URI);
		SamgraphPackage theSamgraphPackage = (SamgraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamgraphPackage.eNS_URI);
		SamgraphconditionPackage theSamgraphconditionPackage = (SamgraphconditionPackage)EPackage.Registry.INSTANCE.getEPackage(SamgraphconditionPackage.eNS_URI);
		SamtypegraphPackage theSamtypegraphPackage = (SamtypegraphPackage)EPackage.Registry.INSTANCE.getEPackage(SamtypegraphPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		graphRuleEClass.getESuperTypes().add(theSamannotationPackage.getAnnotatedElem());
		preservedNodeEClass.getESuperTypes().add(theSamgraphPackage.getNode());
		preservedEdgeEClass.getESuperTypes().add(theSamgraphPackage.getEdge());
		ruleGraphEClass.getESuperTypes().add(theSamgraphPackage.getGraph());
		createdNodeEClass.getESuperTypes().add(theSamgraphPackage.getNode());
		deletedNodeEClass.getESuperTypes().add(theSamgraphPackage.getNode());
		createdEdgeEClass.getESuperTypes().add(theSamgraphPackage.getEdge());
		deletedEdgeEClass.getESuperTypes().add(theSamgraphPackage.getEdge());

		// Initialize classes and features; add operations and parameters
		initEClass(graphRuleEClass, GraphRule.class, "GraphRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGraphRule_Left(), this.getRuleGraph(), null, "left", null, 0, 1, GraphRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGraphRule_Right(), this.getRuleGraph(), null, "right", null, 0, 1, GraphRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGraphRule_AttributeCondition(), theAlgebraUsePackage.getOperationUse(), null, "attributeCondition", null, 0, 1, GraphRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphRule_Priority(), ecorePackage.getEInt(), "priority", null, 0, 1, GraphRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphRule_Urgent(), ecorePackage.getEBoolean(), "urgent", null, 0, 1, GraphRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGraphRule_Name(), ecorePackage.getEString(), "name", null, 0, 1, GraphRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(preservedNodeEClass, PreservedNode.class, "PreservedNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPreservedNode_RefInRule(), this.getPreservedNode(), this.getPreservedNode_RefInRule(), "refInRule", null, 1, 1, PreservedNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(preservedEdgeEClass, PreservedEdge.class, "PreservedEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPreservedEdge_RefInRule(), this.getPreservedEdge(), this.getPreservedEdge_RefInRule(), "refInRule", null, 1, 1, PreservedEdge.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleGraphEClass, RuleGraph.class, "RuleGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleGraph_Condition(), theSamgraphconditionPackage.getGraphCondition(), null, "condition", null, 0, 1, RuleGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gtsEClass, org.eclipse.emf.henshin.sam.model.samrules.GTS.class, "GTS", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGTS_Rules(), this.getGraphRule(), null, "rules", null, 0, -1, org.eclipse.emf.henshin.sam.model.samrules.GTS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGTS_Types(), theSamtypegraphPackage.getTypeGraph(), null, "types", null, 0, -1, org.eclipse.emf.henshin.sam.model.samrules.GTS.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(createdNodeEClass, CreatedNode.class, "CreatedNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deletedNodeEClass, DeletedNode.class, "DeletedNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(createdEdgeEClass, CreatedEdge.class, "CreatedEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(deletedEdgeEClass, DeletedEdge.class, "DeletedEdge", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //SamrulesPackageImpl
